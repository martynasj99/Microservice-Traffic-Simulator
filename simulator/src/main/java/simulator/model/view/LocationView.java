package simulator.model.view;

import simulator.model.Traffic;
import simulator.model.vehicle.Vehicle;

import java.util.*;

public class LocationView {
    private Map<String, List<Vehicle>> vehiclesAtNodes;
    private Map<String, Integer> numberOfVehiclesAtNodes;
    private Map<Long, Traffic> trafficAtStreet;
    private Map<String, Queue<Vehicle>> waitingToLeave;

    public LocationView() {
        waitingToLeave = new HashMap<>();
        trafficAtStreet = new HashMap<>();
    }

    public Map<String, List<Vehicle>> getVehiclesAtNodes() {
        return vehiclesAtNodes;
    }

    public void setVehiclesAtNodes(Map<String, List<Vehicle>> vehiclesAtNodes) {
        this.vehiclesAtNodes = vehiclesAtNodes;
    }

    public Map<String, Integer> getNumberOfVehiclesAtNodes() {
        return numberOfVehiclesAtNodes;
    }

    public void setNumberOfVehiclesAtNodes(Map<String, Integer> numberOfVehiclesAtNodes) {
        this.numberOfVehiclesAtNodes = numberOfVehiclesAtNodes;
    }

    public Map<Long, Traffic> getTrafficAtStreet() {
        return trafficAtStreet;
    }

    public void setTrafficAtStreet(Map<Long, Traffic> trafficAtStreet) {
        this.trafficAtStreet = trafficAtStreet;
    }

    public Map<String, Queue<Vehicle>> getWaitingToLeave() {
        return waitingToLeave;
    }

    public void setWaitingToLeave(Map<String, Queue<Vehicle>> waitingToLeave) {
        this.waitingToLeave = waitingToLeave;
    }
}
