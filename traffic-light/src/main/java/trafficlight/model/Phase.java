package trafficlight.model;

import java.util.List;

public class Phase  {

    private int duration;
    private List<TrafficLightSignals> state;

    public Phase(int duration, List<TrafficLightSignals> state) {
        this.duration = duration;
        this.state = state;
    }

    public Phase() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<TrafficLightSignals> getState() {
        return state;
    }

    public void setState(List<TrafficLightSignals> state) {
        this.state = state;
    }
}
