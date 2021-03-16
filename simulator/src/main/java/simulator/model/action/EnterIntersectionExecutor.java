package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.Vehicle;

import java.util.List;

public class EnterIntersectionExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getCurrentNode() != null) throw new InvalidActionException("This vehicle cannot perform this action: " + vehicle.getNextAction());

        Long curr = vehicle.getCurrentStreet();
        Traffic traffic = serviceContext.locationService.getTraffic().get(vehicle.getCurrentStreet());

        traffic.getTraffic()[traffic.getCells()-1] = null;
        vehicle.nextStage();

        serviceContext.locationService.updateOnAction(vehicle,traffic, curr);
        return true;
    }

}
