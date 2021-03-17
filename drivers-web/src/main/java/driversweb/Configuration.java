package driversweb;

import driversweb.model.DayPlan;

import java.util.Map;

public class Configuration {
    private Map<Long, DayPlan> plans;

    public Map<Long, DayPlan> getPlans() {
        return plans;
    }

    public void setPlans(Map<Long, DayPlan> plans) {
        this.plans = plans;
    }
}
