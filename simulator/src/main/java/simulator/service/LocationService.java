package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simulator.model.view.LocationView;
import simulator.model.network.Street;
import simulator.model.Vehicle;
import simulator.model.Traffic;

import java.util.*;

@Service
public class LocationService {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private MapService mapService;

    private LocationView locationView = new LocationView();

    public synchronized void updateOnAction(Vehicle vehicle, Traffic traffic, Long trafficId){
        getTraffic().put(trafficId, traffic);
        vehicleService.addVehicle(vehicle);
        updateLocations();
    }

    /**
     * Updates the vehicleNodes and vehicleCountNodes fields in the locationView object
     * Depends on the vehicles being up to date in the vehicle service
     */
    public void updateLocations(){
        Map<Long, List<Vehicle>> vehicleNodeLocations = new Hashtable<>(); //INTERSECTION ID -> LIST[DRIVERS]
        Map<Long, Integer> numberOfVehiclesAtNode = new Hashtable<>(); //INTERSECTION ID -> NUMBER OF DRIVERS AT THAT INTERSECTION
        for(Vehicle vehicle : vehicleService.getVehicles()) {
            if (vehicle.getCurrentNode() != null) {
                Long intersection_id = mapService.getIntersectionByName(vehicle.getCurrentNode()).getId();
                List<Vehicle> vehicles = vehicleNodeLocations.getOrDefault(intersection_id, new ArrayList<>());
                vehicles.add(vehicle);
                vehicleNodeLocations.put(intersection_id, vehicles);
            }
        }
        for(Long id : vehicleNodeLocations.keySet()) numberOfVehiclesAtNode.put(id, vehicleNodeLocations.get(id).size());

        locationView.setVehiclesAtNodes(vehicleNodeLocations);
        locationView.setNumberOfVehiclesAtNodes(numberOfVehiclesAtNode);
    }

    public LocationView getLocations(){
        return locationView;
    }

    public Map<Long, Integer> getNumberOfVehiclesAtNode(){
        return locationView.getNumberOfVehiclesAtNodes();
    }

    public Map<Long, List<Vehicle>> getVehiclesAtNodes(){
        return locationView.getVehiclesAtNodes();
    }

    public void initTraffic(){
        Map<Long, Traffic> vehicleStreetLocations = new HashMap<>(); //STREET ID -> TRAFFIC AT THE STREET
        for(Street street : mapService.getMap().getLinks()){
            Traffic traffic = new Traffic(street.getLength());
            vehicleStreetLocations.put(street.getRelationshipId(), traffic);
        }
        locationView.setTrafficAtStreet(vehicleStreetLocations);
    }

    public void addNewTraffic(Street street){
        Map<Long, Traffic> trafficAtStreet = locationView.getTrafficAtStreet();
        Traffic traffic = new Traffic(street.getLength());
        trafficAtStreet.put(street.getRelationshipId(), traffic);
        locationView.setTrafficAtStreet(trafficAtStreet);
    }

    public Map<Long, Traffic> getTraffic(){
        return locationView.getTrafficAtStreet();
    }


}
