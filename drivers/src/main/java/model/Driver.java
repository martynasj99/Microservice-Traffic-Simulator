package model;

public class Driver {

    public String vehicleUri;
    public EnvironmentState state;

/*    public Action generateAction(){
        model.EnvironmentState state = getState();
        Action action = new Action();

        String type;

        if(state.isHasArrived() ) action.setType("wait");
        else if(state.isAtIntersection() && state.isCanLeave()) action.setType("leave");
        else if(state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() > 0) action.setType("enter");
        else if(state.getVehicleSpeed() == 0 && canAccelerate(state)) action.setType("accelerate");
        else if( state.getVehicleSpeed() >= 1 && shouldSlowDown(state)) action.setType("decelerate");
        else if(!state.isAtIntersection()) action.setType("move");
        else action.setType("wait");

        return action;
    }

    private boolean canEnterIntersection(model.EnvironmentState state){
        return state.isTrafficLightStatus()
                && (!state.isHasVehiclesInNode() || !state.getIntersectionName().startsWith("J"));
    }
    private boolean shouldSlowDown(model.EnvironmentState state){
        return state.isTrafficAhead() || (state.isTrafficInVision() && state.getVehicleSpeed() > 1) || (state.isAtLastCell() && !canEnterIntersection(state));
    }

    private boolean canAccelerate(model.EnvironmentState state){
        return (state.isAtLastCell() && canEnterIntersection(state) && state.getVehicleSpeed() == 0)
                || (!state.isAtLastCell() && !state.isTrafficAhead())
                && state.getVehicleSpeed() < state.getStreetSpeed();
    }

    public String getVehicleUri() {
        return vehicleUri;
    }

    public void setVehicleUri(String vehicleUri) {
        this.vehicleUri = vehicleUri;
    }

    public model.EnvironmentState getState() {
        return state;
    }

    public void setState(model.EnvironmentState state) {
        this.state = state;
    }*/
}
