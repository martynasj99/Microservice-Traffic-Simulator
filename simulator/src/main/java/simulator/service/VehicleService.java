package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import simulator.Logging;
import simulator.exception.NoAgentAttachedException;
import simulator.model.*;
import simulator.utils.GlobalClock;

import java.util.*;
import java.util.logging.Logger;

@Service
public class VehicleService {

    Logger logger = Logging.getInstance().getLogger();

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

    public void generateValidActions(){
        for(Vehicle vehicle : vehicles.values()) {
            List<String> validActions = new ArrayList<>();
            if (vehicle.getCurrentNode() != null) {
                validActions.add("leave");
            }
            else if (vehicle.getCurrentStreet() != null) {
                if (mapService.getStreetById(vehicle.getCurrentStreet()).getMaxSpeed() > vehicle.getSpeed() || vehicle.getSpeed() == 0) {
                    validActions.add("accelerate");
                }
                if (vehicle.getSpeed() > 0) {
                    validActions.add("decelerate");
                }
                if (vehicle.getStreetProgress() == locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells() - 1) {
                    validActions.add("enter");
                }else{
                    validActions.add("move");
                }
            }
            vehicle.setValidActions(validActions);
        }
    }

    public EnvironmentState generateEnvironment(Vehicle vehicle){
        EnvironmentState environmentState = new EnvironmentState();
        environmentState.setId(vehicle.getId().intValue());
        environmentState.setVehicleSpeed(vehicle.getSpeed());
        environmentState.setVehicleStreetProgress(vehicle.getStreetProgress());

        if(vehicle.getCurrentStreet() != null){
            Traffic traffic = serviceContext.locationService.getTraffic().get(vehicle.getCurrentStreet());
            environmentState.setStreetSpeed(mapService.getStreetById(vehicle.getCurrentStreet()).getMaxSpeed());
            environmentState.setStreetLength(locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells());

            if (vehicle.getStreetProgress() == locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells() - 1) {
                environmentState.setAtLastCell(true);
                environmentState.setHasVehiclesInNode(locationService.getNumberOfVehiclesAtNode().containsKey(mapService.getIntersectionByName(vehicle.getNextNode()).getId()));
                environmentState.setIntersectionName(vehicle.getNextNode());
                environmentState.setTrafficLightStatus(checkTrafficLightStatus(vehicle.getCurrentStreet()));
            }else{
                environmentState.setTrafficAhead(traffic.getTraffic()[vehicle.getStreetProgress()+1] != null);
                environmentState.setTrafficInVision(observeAhead(vehicle, traffic));
            }
        }
        else if(vehicle.getCurrentNode() != null) {
            environmentState.setAtIntersection(true);
            if(vehicle.getNextNode() != null && vehicle.getEndNode() != null && !vehicle.hasArrived()){
                Long currentStreetId = serviceContext.mapService.getStreetBetweenTwoIntersections(vehicle.getCurrentNode(), vehicle.getNextNode()).getRelationshipId();
                Traffic traffic = serviceContext.locationService.getTraffic().get(currentStreetId);

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
        for (int i = vehicle.getStreetProgress()+1; i < traffic.getCells() && i  <= vehicle.getStreetProgress() + 2; i++) {
            if( (traffic.getTraffic()[i] != null || i == traffic.getCells()-1) && vehicle.getSpeed() > 1)
                return true;
        }
        return false;
    }

    public boolean checkTrafficLightStatus(Long fromId){
        return mapService.getTrafficLightStatus().getOrDefault(fromId, true);
    }

    public void updatePlans(String time){
        for(Vehicle vehicle : vehicles.values()){
            if( vehicle.getPlan() != null
                    && (vehicle.hasArrived() || vehicle.getEndNode() == null)
                    && vehicle.getPlan().getSchedule().containsKey(time) ){
                vehicle.newPlan();
                vehicle.setEndNode(vehicle.getPlan().getSchedule().get(time));
                mapService.generateVehiclePath(vehicle);
            }
        }
    }

    private void sendNotification(Vehicle vehicle, Driver driver){
        if(vehicle.getNotificationUri() == null) throw new NoAgentAttachedException("No Agent is attached to vehicle: " + vehicle.getId());

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Driver> request = new HttpEntity<>(driver, headers);

        logger.info("POSTING Notification: " + driver.getState().getId() + " Vehicle: " + vehicle.getId());
        String uri = vehicle.getNotificationUri();
        template.postForObject(uri, request, Void.class);
        logger.info(" Notification Sent : "+driver.getState().getId()+" sent from : " + vehicle.getId());
    }

    public synchronized void step(State state){
        logger.info("Starting... [" + state.getStep() + "]");
        informationService.setTime(state.getStep());;

        for(Vehicle vehicle : getVehicles()){
            if(!vehicle.hasArrived() && vehicle.getNotificationUri() != null){
                if(vehicle.getNextAction() != null) {
                    Action action = vehicle.getNextAction();
                    vehicle.setNextAction(null);
                    vehicle.execute(serviceContext, action);
                    logger.info("Vehicle " + vehicle.getId() + " executed " +action.getType());
                }
            }
        }

        for(Vehicle vehicle : getVehicles()){
            if(!vehicle.hasArrived() && vehicle.getNotificationUri() != null){
                EnvironmentState environmentState = generateEnvironment(vehicle);
                Driver driver = new Driver();
                driver.setState(environmentState);
                driver.setVehicleUri("http://localhost:8081/vehicles/"+environmentState.getId()+"/action");
                sendNotification(vehicle, driver);
            }
        }

        updatePlans(clock.toString());
    }
}
