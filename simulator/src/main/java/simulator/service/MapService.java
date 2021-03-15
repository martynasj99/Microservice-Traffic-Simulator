package simulator.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import simulator.Configuration;
import simulator.exception.IntersectionNotFoundException;
import simulator.exception.StreetNotFoundException;
import simulator.model.*;
import simulator.model.network.Intersection;
import simulator.model.network.Street;
import simulator.model.view.MapView;
import simulator.repository.IntersectionRepository;
import simulator.repository.StreetRepository;
import simulator.utils.PathGenerator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapService {

    private PathGenerator pathGenerator = new PathGenerator();

    @Autowired
    private IntersectionRepository intersectionRepository;
    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private LocationService locationService;

    private Map<Long, Boolean> trafficLightStatus = new HashMap<>();

    private boolean isSafeMode;

    public void configure(Configuration configuration){
        intersectionRepository.deleteAll();

        final int NO_INTERSECTIONS = 11;

        List<Intersection> intersections = new ArrayList<>();

        for(int i = 1; i <= NO_INTERSECTIONS; i++){
            intersections.add(new Intersection("J"+ i));
        }

        intersections.add(new Intersection("H1"));
        intersections.add(new Intersection("W1"));

        intersections.get(4).setType("TRAFFIC LIGHT");
        intersections.get(7).setType("TRAFFIC LIGHT");
        intersections.get(3).setType("TRAFFIC LIGHT");
        intersections.get(6).setType("TRAFFIC LIGHT");

        intersections.get(11).setType("HOME");
        intersections.get(12).setType("WORK");

        List<Street> streets = Arrays.asList(
                    new Street("S1",5,1, intersections.get(1), intersections.get(0)),
                    new Street("S2", 10,2, intersections.get(2), intersections.get(1)),
                    new Street("S3", 10,2, intersections.get(3), intersections.get(2)),
                    new Street("S4", 7,2, intersections.get(1), intersections.get(4)),
                    new Street("S5", 5,1, intersections.get(0), intersections.get(5)),
                    new Street("S6", 5,1, intersections.get(5), intersections.get(4)),
                    new Street("S7", 5,1, intersections.get(4), intersections.get(3)),
                    new Street("S8", 5,1, intersections.get(8), intersections.get(3)),
                    new Street("S9", 5,1, intersections.get(4), intersections.get(7)),
                    new Street("S10", 5,1, intersections.get(5), intersections.get(6)),

                    new Street("S11", 10,2, intersections.get(9), intersections.get(8)),
                    new Street("S12", 7,2, intersections.get(8), intersections.get(9)),
                    new Street("S13", 5,1, intersections.get(8), intersections.get(7)),
                    new Street("S14", 5,1, intersections.get(7), intersections.get(8)),
                    new Street("S15", 5,1, intersections.get(7), intersections.get(6)),
                    new Street("S16", 5,1, intersections.get(6), intersections.get(7)),
                    new Street("S17", 5,1, intersections.get(6), intersections.get(10)),
                    new Street("S18", 5,1, intersections.get(10), intersections.get(6)),

                    new Street("S19", 2,1, intersections.get(11), intersections.get(10)),
                    new Street("S20", 2,1, intersections.get(10), intersections.get(11)),
                    new Street("S20", 2,1, intersections.get(2), intersections.get(12)),
                    new Street("S20", 2,1, intersections.get(12), intersections.get(2))
        );

        intersectionRepository.saveAll(intersections);
        generateVehiclePaths();
        locationService.updateLocations();
        locationService.initTraffic();
    }

    public MapView getMap(){
        MapView mapView = new MapView();

        Iterable<Intersection> intersections = getIntersections();
        mapView.setNodes(intersections);

        List<Street> links = new ArrayList<>();
        for(Intersection intersection : intersections){
            if(intersection.getOutStreets() != null) links.addAll(intersection.getOutStreets());
        }
        mapView.setLinks(links);
        return mapView;
    }

    public Iterable<Intersection> getIntersections(){
        return intersectionRepository.findAll();
    }

    public Intersection getIntersectionById(Long id){
        return intersectionRepository.findById(id).orElse(null);
    }

    public Intersection getIntersectionByName(String name){
        return intersectionRepository.findByName(name).orElse(null);
    }

    public Street getStreetById(Long id){
        return streetRepository.findById(id).orElse(null);
    }

    public Iterable<Street> getOutStreetsAtIntersection(String name){
        return intersectionRepository.findByName(name).<Iterable<Street>>map(Intersection::getOutStreets).orElse(null);
    }

    public Iterable<Street> getInStreetsAtIntersection(String name){
        return intersectionRepository.findByName(name).<Iterable<Street>>map(Intersection::getInStreets).orElse(null);
    }

    public void addIntersectionStreet(Intersection intersection, Street street, Long toNode) throws Exception{
        addIntersection(intersection);
        addStreet(intersection.getId(), toNode, street);
    }

    public void addIntersection(Intersection intersection){
        intersectionRepository.save(intersection);
    }
    public void removeIntersection(Intersection intersection){intersectionRepository.delete(intersection);}

    public Street getStreetBetweenTwoIntersections(String start, String end){
        Iterable<Street> streets = getOutStreetsAtIntersection(start);
        for(Street street : streets){
            if(street.getTarget().equals(getIntersectionByName(end).getId())){
                return street;
            }
        }
        throw new StreetNotFoundException("Street between node: " + start +" and node: " + end + " not found.");
    }

    public void addStreet(Long fromId, Long toId, Street street) throws Exception{
        Intersection intersection1 = intersectionRepository.findById(fromId).orElse(null);
        Intersection intersection2 = intersectionRepository.findById(toId).orElse(null);

        if(intersection1 == null || intersection2 == null){
            throw new IntersectionNotFoundException("Intersection with id: "+fromId+" or id: "+toId+" Not Found");
        }

        street.setSource(intersection1);
        street.setTarget(intersection2);
        intersection1.addOutLink(street);
        intersectionRepository.save(intersection1);
        intersectionRepository.save(intersection2);

        locationService.addNewTraffic(street);
    }

    public void generateTrafficLights(){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for(Intersection intersection : intersectionRepository.findByType("TRAFFIC LIGHT")){
            JSONObject body = new JSONObject();
            if(intersection.getOutStreets() != null && intersection.getInStreets() != null ){
                body.put("inLinks", intersection.getInStreets().stream().map(Street::getRelationshipId).collect(Collectors.toList()));
                body.put("outLinks", intersection.getOutStreets().stream().map(Street::getRelationshipId).collect(Collectors.toList()));
                HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
                template.postForObject("http://localhost:8082/traffic-lights/add/"+intersection.getId(), request, Void.class);
            }
        }
    }

    private void generateVehiclePaths() {
        for (Vehicle vehicle : vehicleService.getVehicles()) {
            generateVehiclePath(vehicle);
        }
    }

    public void generateVehiclePath(Vehicle vehicle){
        if (vehicle.getPath() == null && vehicle.getEndNode() != null) {
            List<String> path = pathGenerator.generatePath(vehicle.getStartNode(), vehicle.getEndNode(), intersectionRepository);
            vehicle.setPath(path);
            vehicle.setCurrentNode(path.get(0));
            vehicle.setNextNode(path.get(1));
        }
    }

    public boolean isSafeMode() {
        return isSafeMode;
    }

    public Map<Long, Boolean> getTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(Map<Long, Boolean> trafficLightStatus){
        this.trafficLightStatus = trafficLightStatus;
    }
}