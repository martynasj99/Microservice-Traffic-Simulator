package simulator;

import simulator.model.Vehicle;
import simulator.model.network.Intersection;
import simulator.model.network.Street;

import java.util.List;

public class Configuration {

    private boolean safeMode;
    private List<Vehicle> vehicles;


    public boolean isSafeMode() {
        return safeMode;
    }

    public void setSafeMode(boolean safeMode) {
        this.safeMode = safeMode;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

}
