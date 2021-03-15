package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.Vehicle;

import java.util.List;

public class AccelerateExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getCurrentStreet() == null) throw new InvalidActionException("This vehicle cannot perform this action: " + vehicle.getNextAction());
        vehicle.accelerate();
        return true;
    }


    /**
     * Increase the speed of the vehicle once it does not exceed the max speed permitted for the current street.
     * @param vehicle
     * @param serviceContext
     * @param parameters
     * @return
     */
/*    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getCurrentStreet() == null) throw new InvalidActionException("This vehicle cannot perform this action: " + vehicle.getNextAction());
        Street street = serviceContext.mapService.getStreetById(vehicle.getCurrentStreet());
        if(street.getMaxSpeed() > vehicle.getSpeed())
            vehicle.setSpeed(vehicle.getSpeed()+1);

        vehicle.setNotification("move");
        return true;
    }*/

}
