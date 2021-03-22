package driversweb.model;

public class Driver {

    private DayPlan plan;

    public Driver() {
    }

    public Action generateAction(EnvironmentState state){
        Action action = new Action();

        if(getPlan() != null && (state.isHasArrived() || !state.isHasEndNode() ) && getPlan().getSchedule().containsKey(state.getTime()) ){
            action.setType("plan");
            action.setNewDestination(getPlan().getSchedule().get(state.getTime()));
        }
        else if(state.isHasArrived() ) action.setType("wait");
        else if(state.isAtIntersection() && state.isCanLeave()) action.setType("leave");
        else if(state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() == 1) action.setType("enter");
        else if( state.getVehicleSpeed() >= 1 && shouldSlowDown(state)) action.setType("decelerate");
        else if( canAccelerate(state) && !state.isTrafficInVision()) action.setType("accelerate");
        else if(!state.isAtIntersection() && state.getVehicleSpeed() > 0) action.setType("move");
        else action.setType("wait");

        return action;
    }

    private boolean canEnterIntersection(EnvironmentState state){
        return state.isTrafficLightStatus()
                && (state.getIntersectionCurrentCapacity() < state.getIntersectionMaxCapacity());
    }
    private boolean shouldSlowDown(EnvironmentState state){
        return (state.isTrafficAhead() && state.getVehicleSpeed() > 1) || (state.isAtLastCell() && (!canEnterIntersection(state) || state.getVehicleSpeed() > 1 ));
    }

    private boolean canAccelerate(EnvironmentState state){
        return ((state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() == 0)
                || (!state.isAtLastCell() && !state.isTrafficAhead()))
                && state.getVehicleSpeed() < state.getStreetSpeed();
    }


    public DayPlan getPlan() {
        return plan;
    }

    public void setPlan(DayPlan plan) {
        this.plan = plan;
    }
}
