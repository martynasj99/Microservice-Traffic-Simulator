package driversweb.model;

import java.util.Map;

public class DayPlan implements Plan {
    private Map<String, String> schedule; //Time -> Destination

    public DayPlan() {
    }

    public DayPlan(Map<String, String> schedule) {
        this.schedule = schedule;
    }

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
    }
}
