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

}
