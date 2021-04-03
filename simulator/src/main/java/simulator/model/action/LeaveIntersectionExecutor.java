package simulator.model.action;

import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.vehicle.Vehicle;

import java.util.List;

public class LeaveIntersectionExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        Long currentStreetId = serviceContext.mapService.getStreetBetweenTwoIntersections(vehicle.getCurrentNode(), vehicle.getNextNode()).getRelationshipId();
        Traffic traffic = serviceContext.locationService.getTraffic().get(currentStreetId);

        if (!isActionSafe(vehicle, serviceContext, traffic)) return false;

        traffic.getTraffic()[0] = vehicle;
        serviceContext.locationService.getLocations().getWaitingToLeave().get(vehicle.getCurrentNode()).poll();

        vehicle.setCurrentNode(null);
        vehicle.setCurrentStreet(currentStreetId);
        serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());
        return true;
    }

    private boolean isActionSafe(Vehicle vehicle, ServiceContext serviceContext, Traffic traffic) {
        if(traffic.getTraffic()[0] != null) {
            if(serviceContext.mapService.isSafeMode()){
                return false;
            }else{
                serviceContext.informationService.addCrash("Crash leaving intersection: "+vehicle.getCurrentNode()+ " by vehicle: "+vehicle.getId());
            }
        }
        return true;
    }
}