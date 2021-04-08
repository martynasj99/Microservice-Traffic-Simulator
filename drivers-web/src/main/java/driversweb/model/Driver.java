package driversweb.model;

import common.Action;
import common.EnvironmentState;
import driversweb.model.plan.DayPlan;

public class Driver {

    private Long id;
    private String time;
    private DayPlan plan;

    public Driver() {
    }

    public Action generateAction(EnvironmentState state, String type){
        Action action = new Action();
        action.setId(id);

        if(type.equals("traffic")){
            if(getPlan() != null && (state.isHasArrived() || !state.isHasEndNode() ) && getPlan().getSchedule().containsKey(time) ){
                action.setType("plan");
                action.setNewDestination(getPlan().getSchedule().get(time));
            }
            else if(state.isHasArrived() ) action.setType("wait");
            else if(state.isAtIntersection() && state.isCanLeave()) action.setType("leave");
            else if(state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() == 1) action.setType("enter");
            else if( state.getVehicleSpeed() >= 1 && shouldSlowDown(state)) action.setType("decelerate");
            else if( canAccelerate(state) || state.getVehicleSpeed() == 0 ) action.setType("accelerate");
            else if(!state.isAtIntersection() && state.getVehicleSpeed() > 0 ) action.setType("move");
            else action.setType("wait");
        }else if(type.equals("home")){
            if(getPlan() != null && getPlan().getSchedule().containsKey(time) ){
                action.setType("plan");
                action.setNewDestination(getPlan().getSchedule().get(time));
            }else
                action.setType("watching tv");
        }
        return action;
    }

    private boolean canEnterIntersection(EnvironmentState state){
        return state.isTrafficLightStatus()
                && (state.getIntersectionCurrentCapacity() < state.getIntersectionMaxCapacity());
    }
    private boolean shouldSlowDown(EnvironmentState state){
        return state.isVehicleAhead() || (state.isObstacleAhead() && state.getVehicleSpeed() > 1) || (state.isAtLastCell() && (!canEnterIntersection(state) || state.getVehicleSpeed() > 1 ));
    }

    private boolean canAccelerate(EnvironmentState state){
        return ((state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() == 0)
                || (!state.isAtLastCell() && !state.isObstacleAhead()) || (state.getVehicleSpeed() == 0 && !state.isAtLastCell() && !state.isVehicleAhead()))
                && state.getVehicleSpeed() < state.getStreetSpeed() && !state.isObstacleInVision() ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayPlan getPlan() {
        return plan;
    }

    public void setPlan(DayPlan plan) {
        this.plan = plan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
