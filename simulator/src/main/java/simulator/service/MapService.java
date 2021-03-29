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
import simulator.exception.InvalidException;
import simulator.exception.StreetNotFoundException;
import simulator.model.*;
import simulator.model.network.Intersection;
import simulator.model.network.Street;
import simulator.model.view.MapView;
import simulator.repository.IntersectionRepository;
import simulator.repository.StreetRepository;
import simulator.utils.PathGenerator;

import javax.validation.ConstraintViolationException;
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

    private boolean safeMode;

    public void configure(Configuration configuration) throws InvalidException{
        intersectionRepository.deleteAll();

        this.safeMode = configuration.isSafeMode();

        for(Intersection intersection : configuration.getIntersections()) {
            if(intersectionRepository.findByName(intersection.getName()).orElse(null) == null) {
                intersectionRepository.createNode(intersection.getName(), intersection.getType(), intersection.getCapacity());
                System.out.println(intersection.getSimulators());
                Intersection i = intersectionRepository.findByName(intersection.getName()).orElse(null);
                if(i != null) {
                    i.setSimulators(intersection.getSimulators());
                    intersectionRepository.save(i);
                }

            }
            else
                throw new InvalidException("Duplicate name: " + intersection.getName());
        }

        for(Street street : configuration.getStreets())
            streetRepository.createStreet(street.getSource(), street.getTarget(), street.getName(), street.getLength(), street.getMaxSpeed());

        for (Vehicle vehicle : vehicleService.getVehicles())
            generateVehiclePath(vehicle);


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

    public Intersection getIntersectionByName(String name) {
        try{
            return intersectionRepository.findByName(name).orElseThrow(() -> new IntersectionNotFoundException("Intersection: " + name + " not found"));
        }catch (IntersectionNotFoundException e){
            e.printStackTrace();
        }
        return null;
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

    public void addIntersection(Intersection intersection){
        intersectionRepository.createNode(intersection.getName(), intersection.getType(), intersection.getCapacity());
    }

    public void removeIntersection(Intersection intersection){intersectionRepository.delete(intersection);}

    public Street getStreetBetweenTwoIntersections(String start, String end){
        Iterable<Street> streets = getOutStreetsAtIntersection(start);
        for(Street street : streets){
            if(street.getTarget().equals(end)){
                return street;
            }
        }
        throw new StreetNotFoundException("Street between node: " + start +" and node: " + end + " not found.");
    }

    public void addStreet(Street street) throws InvalidException {
        streetRepository.createStreet(street.getSource(), street.getTarget(), street.getName(), street.getLength(), street.getMaxSpeed());
        Street s = streetRepository.findByName(street.getName()).orElseThrow(() -> new InvalidException("Street: "+street.getName()+ " not found"));
        locationService.addNewTraffic(s);
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

    public void generateVehiclePath(Vehicle vehicle){
        if (vehicle.getPath() == null && vehicle.getEndNode() != null) {
            List<String> path = pathGenerator.generatePath(vehicle.getStartNode(), vehicle.getEndNode(), intersectionRepository);
            vehicle.setPath(path);
            vehicle.setCurrentNode(path.get(0));
            vehicle.setNextNode(path.get(1));
        }
    }

    public Map<Long, Boolean> getTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(Map<Long, Boolean> trafficLightStatus){
        this.trafficLightStatus = trafficLightStatus;
    }

    public boolean isSafeMode() {
        return safeMode;
    }
}