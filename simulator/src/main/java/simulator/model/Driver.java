package simulator.model;

public class Driver {

    private String vehicleUri;
    private EnvironmentState state;

    public Driver() {
    }
/*
    public Driver(String vehicleUri, EnvironmentState state) {
        this.vehicleUri = vehicleUri;
      //  this.state = state;
    }*/

    public String getVehicleUri() {
        return vehicleUri;
    }

    public void setVehicleUri(String vehicleUri) {
        this.vehicleUri = vehicleUri;
    }

    public EnvironmentState getState() {
        return state;
    }

    public void setState(EnvironmentState state) {
        this.state = state;
    }
}
