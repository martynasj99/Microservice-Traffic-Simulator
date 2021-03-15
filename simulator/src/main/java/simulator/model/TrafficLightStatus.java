package simulator.model;

import java.util.Map;

public class TrafficLightStatus {
    private Map<Long, Boolean> status;

    public TrafficLightStatus() {
    }

    public Map<Long, Boolean> getStatus() {
        return status;
    }

    public void setStatus(Map<Long, Boolean> status) {
        this.status = status;
    }
}
