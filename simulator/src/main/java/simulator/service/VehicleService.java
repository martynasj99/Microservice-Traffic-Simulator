package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import simulator.exception.NoAgentAttachedException;
import simulator.model.*;
import simulator.model.network.Intersection;
import simulator.utils.GlobalClock;

import java.util.*;
import java.util.logging.Logger;

@Service
public class VehicleService {

    private final Logger logger = Logger.getLogger(VehicleService.class.getName());

    private GlobalClock clock = GlobalClock.getInstance();

    @Autowired
    private ServiceContext serviceContext;

    @Autowired
    private MapService mapService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private InformationService informationService;

    private Map<Long, Vehicle> vehicles = new Hashtable<>();

    public void addVehicle(Vehicle vehicle){
        vehicles.put(vehicle.getId(), vehicle);
    }

    public Vehicle getVehicle(Long id){
        return vehicles.get(id);
    }

    public Iterable<Vehicle> getVehicles(){
        return vehicles.values();
    }

    public int getNumberOfVehicles(){
        return vehicles.size();
    }

    public Set<String> generatePossibleActions(Vehicle vehicle){
        Set<String> validActions = new HashSet<>();
        validActions.add("wait");
        if (vehicle.getCurrentNode() != null) {
            validActions.add("leave");
        }
        else if (vehicle.getCurrentStreet() != null) {
            validActions.add("accelerate");
            if (vehicle.getSpeed() > 0) {
                validActions.add("decelerate");
                validActions.add("move");
            }
            if (vehicle.getStreetProgress() == locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells() - 1) {
                validActions.add("enter");
            }
        }
        return validActions;
    }

    public EnvironmentState generateEnvironment(Vehicle vehicle){
        EnvironmentState environmentState = new EnvironmentState();
        environmentState.setPossibleActions(generatePossibleActions(vehicle));
        environmentState.setId(vehicle.getId().intValue());
        environmentState.setTime(informationService.getTime());
        environmentState.setVehicleSpeed(vehicle.getSpeed());
        environmentState.setVehicleStreetProgress(vehicle.getStreetProgress());
        environmentState.setHasEndNode(vehicle.getEndNode() != null);
        environmentState.setHasArrived(vehicle.hasArrived());

        if(vehicle.getCurrentStreet() != null){
            Traffic traffic = locationService.getTraffic().get(vehicle.getCurrentStreet());
            environmentState.setStreetSpeed(mapService.getStreetById(vehicle.getCurrentStreet()).getMaxSpeed());
            environmentState.setStreetLength(locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells());

            if (vehicle.getStreetProgress() == locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells() - 1) {
                Intersection next = mapService.getIntersectionByName(vehicle.getNextNode());
                environmentState.setAtLastCell(true);
                environmentState.setIntersectionCurrentCapacity(locationService.getLocations().getNumberOfVehiclesAtNodes().getOrDefault(next.getId(), 0));
                environmentState.setIntersectionMaxCapacity(next.getCapacity());
                environmentState.setTrafficLightStatus(checkTrafficLightStatus(vehicle.getCurrentStreet()));
            }else{
                environmentState.setTrafficAhead(traffic.getTraffic()[vehicle.getStreetProgress()+1] != null);
                environmentState.setTrafficInVision(observeAhead(vehicle, traffic));
            }
        }
        else if(vehicle.getCurrentNode() != null) {
            environmentState.setAtIntersection(true);
            if(vehicle.getNextNode() != null && vehicle.getEndNode() != null && !vehicle.hasArrived()){
                Long currentStreetId = mapService.getStreetBetweenTwoIntersections(vehicle.getCurrentNode(), vehicle.getNextNode()).getRelationshipId();
                Traffic traffic = locationService.getTraffic().get(currentStreetId);

                Queue<Vehicle> waiting = locationService.getLocations().getWaitingToLeave().getOrDefault(vehicle.getCurrentNode(), new LinkedList<>());

                if(!waiting.contains(vehicle)) waiting.add(vehicle);
                else environmentState.setCanLeave(waiting.peek().equals(vehicle) && traffic.getTraffic()[0] == null);

                locationService.getLocations().getWaitingToLeave().put(vehicle.getCurrentNode(), waiting);
            }
        }

        System.out.println(environmentState.toString());
        return environmentState;
    }

    public boolean observeAhead(Vehicle vehicle, Traffic traffic){
        for (int i = vehicle.getStreetProgress()+1; i < traffic.getCells() && i  <= vehicle.getStreetProgress() + vehicle.getVision(); i++) {
            if( (traffic.getTraffic()[i] != null || i == traffic.getCells()-1) && vehicle.getSpeed() > 1)
                return true;
        }
        return false;
    }

    public boolean checkTrafficLightStatus(Long fromId){
        return mapService.getTrafficLightStatus().getOrDefault(fromId, true);
    }

    public void sendNotification(Vehicle vehicle, EnvironmentState state){
        if(vehicle.getNotificationUri() == null) throw new NoAgentAttachedException("No Agent is attached to vehicle: " + vehicle.getId());

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EnvironmentState> request = new HttpEntity<>(state, headers);

        logger.info("POSTING Notification: " + state.getId() + " Vehicle: " + vehicle.getId());
        String uri = vehicle.getNotificationUri();
        template.postForObject(uri, request, Void.class);
        logger.info(" Notification Sent : "+state.getId()+" sent from : " + vehicle.getId());
    }
}