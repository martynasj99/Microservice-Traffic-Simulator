package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.Vehicle;

import java.util.List;

public class DecelerateExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getSpeed() < 1) throw new InvalidActionException("Vehicle " + vehicle.getId() + " can't decelerate");
        vehicle.decelerate();
        return true;
    }

    /**
     * Decrease the speed of the vehicle by 1. It sends a notification to its agent to move after it does this.
     * @param vehicle
     * @param serviceContext
     * @param parameters
     * @return
     */
/*    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getSpeed() > 0) vehicle.setSpeed(vehicle.getSpeed()-1);
        vehicle.setNotification("move");
        return true;
    }*/
}
