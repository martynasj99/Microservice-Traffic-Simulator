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
            if (!isActionSafe(vehicle, serviceContext, traffic)) return false;
            traffic.getTraffic()[vehicle.getStreetProgress() + 1] = traffic.getTraffic()[vehicle.getStreetProgress()];
            traffic.getTraffic()[vehicle.getStreetProgress()] = null;
            vehicle.setStreetProgress(vehicle.getStreetProgress() + 1);
            serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());
        }
        return true;
    }

    private boolean isActionSafe(Vehicle vehicle, ServiceContext serviceContext, Traffic traffic) {
        if(traffic.getTraffic()[vehicle.getStreetProgress() + 1] != null) {
            if(serviceContext.mapService.isSafeMode()){
                return false;
            }else{
                //serviceContext.informationService.getInformationView().setMessage("CRASH: " + vehicle.getId());
            }
        }
        return true;
    }
}