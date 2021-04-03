package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simulator.model.view.LocationView;
import simulator.model.network.Street;
import simulator.model.vehicle.Vehicle;
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
        Map<String, List<Vehicle>> vehicleNodeLocations = new Hashtable<>(); //INTERSECTION ID -> LIST[DRIVERS]
        Map<String, Integer> numberOfVehiclesAtNode = new Hashtable<>(); //INTERSECTION ID -> NUMBER OF DRIVERS AT THAT INTERSECTION
        for(Vehicle vehicle : vehicleService.getVehicles()) {
            if (vehicle.getCurrentNode() != null) {
                List<Vehicle> vehicles = vehicleNodeLocations.getOrDefault(vehicle.getCurrentNode(), new ArrayList<>());
                vehicles.add(vehicle);
                vehicleNodeLocations.put(vehicle.getCurrentNode(), vehicles);
            }
        }
        for(String name : vehicleNodeLocations.keySet()) numberOfVehiclesAtNode.put(name, vehicleNodeLocations.get(name).size());

        locationView.setVehiclesAtNodes(vehicleNodeLocations);
        locationView.setNumberOfVehiclesAtNodes(numberOfVehiclesAtNode);
    }

    public LocationView getLocations(){
        return locationView;
    }

    public Map<String, Integer> getNumberOfVehiclesAtNode(){
        return locationView.getNumberOfVehiclesAtNodes();
    }

    public Map<String, List<Vehicle>> getVehiclesAtNodes(){
        return locationView.getVehiclesAtNodes();
    }

    public void addNewTraffic(Street street){
        Map<Long, Traffic> trafficAtStreet = locationView.getTrafficAtStreet();
        trafficAtStreet.put(street.getRelationshipId(), new Traffic(street.getLength()));
        locationView.setTrafficAtStreet(trafficAtStreet);
    }

    public Map<Long, Traffic> getTraffic(){
        return locationView.getTrafficAtStreet();
    }
}
