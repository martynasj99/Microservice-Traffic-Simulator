package simulator.model.action;

import simulator.model.vehicle.Vehicle;
import simulator.service.ServiceContext;

import java.util.List;

public class ChangePlanExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        vehicle.newPlan();
        serviceContext.mapService.generateVehiclePath(vehicle);
        return true;
    }
}
