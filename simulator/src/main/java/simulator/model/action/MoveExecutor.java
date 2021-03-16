package simulator.model.action;

import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.Vehicle;

import java.util.List;

public class MoveExecutor implements ActionExecutor {

    /**
     * Move vehicle by an increment of its speed along the traffic.
     * @param vehicle
     * @param serviceContext
     * @param parameters
     * @return
     */
    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        Traffic traffic = serviceContext.locationService.getTraffic().get(vehicle.getCurrentStreet());

        for (int i = 0; i < vehicle.getSpeed(); i++) {
            traffic.getTraffic()[vehicle.getStreetProgress() + 1] = traffic.getTraffic()[vehicle.getStreetProgress()];
            traffic.getTraffic()[vehicle.getStreetProgress()] = null;
            vehicle.setStreetProgress(vehicle.getStreetProgress() + 1);
            serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());
        }
        return true;
    }
}