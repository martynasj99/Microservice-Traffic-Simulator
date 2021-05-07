package simulator.model.vehicle;

public class RoundTrip {

    private Long vehicleId;
    private long startTime;
    private long endTime;
    private float totalTime;
    private VehicleRoute route;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void end() {
        endTime = System.currentTimeMillis();
        totalTime = (endTime - startTime) / 1000f;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public VehicleRoute getRoute() {
        return route;
    }

    public void setRoute(VehicleRoute route) {
        this.route = route;
    }
}

