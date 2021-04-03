package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.vehicle.Vehicle;

import java.util.List;

public class AccelerateExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getCurrentStreet() == null) throw new InvalidActionException("This vehicle cannot perform this action: " + vehicle.getNextAction());
        vehicle.accelerate();
        //vehicle.execute(serviceContext, new Action("move"));
        return true;
    }

}
