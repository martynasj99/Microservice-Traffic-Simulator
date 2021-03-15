package simulator.model.view;

import simulator.model.Traffic;
import simulator.model.Vehicle;

import java.util.*;

public class LocationView {
    private Map<Long, List<Vehicle>> vehiclesAtNodes;
    private Map<Long, Integer> numberOfVehiclesAtNodes;
    private Map<Long, Traffic> trafficAtStreet;
    private Map<String, Queue<Vehicle>> waitingToLeave;

    public LocationView() {
        waitingToLeave = new HashMap<>();
    }

    public Map<Long, List<Vehicle>> getVehiclesAtNodes() {
        return vehiclesAtNodes;
    }

    public void setVehiclesAtNodes(Map<Long, List<Vehicle>> vehiclesAtNodes) {
        this.vehiclesAtNodes = vehiclesAtNodes;
    }

    public Map<Long, Integer> getNumberOfVehiclesAtNodes() {
        return numberOfVehiclesAtNodes;
    }

    public void setNumberOfVehiclesAtNodes(Map<Long, Integer> numberOfVehiclesAtNodes) {
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
