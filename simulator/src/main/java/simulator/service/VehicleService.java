package simulator.service;

import common.EnvironmentState;
import common.EnvironmentStreet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simulator.model.*;
import simulator.model.network.Intersection;
import simulator.model.network.Street;
import simulator.model.vehicle.Vehicle;


import java.util.*;
import java.util.logging.Logger;

@Service
public class VehicleService {

    private final Logger logger = Logger.getLogger(VehicleService.class.getName());

    @Autowired
    private MapService mapService;

    @Autowired
    private LocationService locationService;

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
        environmentState.setType("traffic");
        environmentState.setVehicleSpeed(vehicle.getSpeed());
        environmentState.setHasEndNode(vehicle.getEndNode() != null);
        environmentState.setHasArrived(vehicle.hasArrived());

        environmentState.setIntersectionCurrentCapacity(0);
        environmentState.setIntersectionMaxCapacity(0);

        if(vehicle.getCurrentStreet() != null){
            Traffic traffic = locationService.getTraffic().get(vehicle.getCurrentStreet());
            environmentState.setStreetSpeed(mapService.getStreetById(vehicle.getCurrentStreet()).getMaxSpeed());

            if (vehicle.getStreetProgress() == locationService.getTraffic().get(vehicle.getCurrentStreet()).getCells() - 1) {
                Intersection next = mapService.getIntersectionByName(vehicle.getNextNode());
                environmentState.setAtLastCell(true);
                environmentState.setIntersectionCurrentCapacity(locationService.getLocations().getNumberOfVehiclesAtNodes().getOrDefault(next.getName(), 0));
                environmentState.setIntersectionMaxCapacity(next.getCapacity());
                environmentState.setTrafficLightStatus(checkTrafficLightStatus(vehicle.getCurrentStreet()));
            }else{
                environmentState.setVehicleAhead(locationService.getTraffic().get(vehicle.getCurrentStreet()).getTraffic()[vehicle.getStreetProgress()+1] != null);
                environmentState.setObstacleInVision(observeAhead(vehicle, traffic, vehicle.getVision()));
                environmentState.setObstacleAhead(observeAhead(vehicle, traffic, vehicle.getSpeed()));
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

                List<EnvironmentStreet> streets = new ArrayList<>();
                for(Street s : mapService.getOutStreetsAtIntersection(vehicle.getCurrentNode())){
                    EnvironmentStreet street = new EnvironmentStreet();
                    street.setSource(s.getSource());
                    street.setTarget(s.getTarget());
                    street.setId(s.getRelationshipId());
                    streets.add(street);

                    Traffic t = locationService.getTraffic().get(s.getRelationshipId());
                    if(waiting.contains(vehicle)) {
                        street.setCanEnter(waiting.peek().equals(vehicle) && t.getTraffic()[0] == null);
                    }
                }
                environmentState.setStreets(streets);
                environmentState.setVehicleLocation(vehicle.getLocation());
            }

        }
        System.out.println(environmentState.toString());
        return environmentState;
    }

    private boolean observeAhead(Vehicle vehicle, Traffic traffic, int range){
        for (int i = vehicle.getStreetProgress()+1; i < traffic.getCells() && i  <= vehicle.getStreetProgress() + range; i++) {
            if( (traffic.getTraffic()[i] != null || (i == traffic.getCells()-1  )))
                return true;
        }
        return false;
    }

    public boolean checkTrafficLightStatus(Long fromId){
        return mapService.getTrafficLightStatus().getOrDefault(fromId, true);
    }
}