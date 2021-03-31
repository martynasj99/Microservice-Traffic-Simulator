package driversweb;

import driversweb.model.plan.DayPlan;

import java.util.Map;

public class Configuration {
    private int initial;
    private Map<Long, DayPlan> plans;

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public Map<Long, DayPlan> getPlans() {
        return plans;
    }

    public void setPlans(Map<Long, DayPlan> plans) {
        this.plans = plans;
    }
}
