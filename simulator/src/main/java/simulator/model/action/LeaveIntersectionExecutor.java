package simulator.model.action;

import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.Vehicle;

import java.util.List;

public class LeaveIntersectionExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        Long currentStreetId = serviceContext.mapService.getStreetBetweenTwoIntersections(vehicle.getCurrentNode(), vehicle.getNextNode()).getRelationshipId();
        Traffic traffic = serviceContext.locationService.getTraffic().get(currentStreetId);

        traffic.getTraffic()[0] = vehicle;
        serviceContext.locationService.getLocations().getWaitingToLeave().get(vehicle.getCurrentNode()).poll();

        vehicle.setCurrentNode(null);
        vehicle.setCurrentStreet(currentStreetId);
        serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());

        return true;
    }
/*
    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {

        if(!vehicle.hasArrived()){
            Long currentStreetId = serviceContext.mapService.getStreetBetweenTwoIntersections(vehicle.getCurrentNode(), vehicle.getNextNode()).getRelationshipId();
            Traffic traffic = serviceContext.locationService.getTraffic().get(currentStreetId);

            if(traffic.getTraffic()[0] == null){ //Check if you can move out onto the road
                traffic.getTraffic()[0] = vehicle;
            }else {
                vehicle.setNotification("leave");
                return false;
            }
            vehicle.setCurrentNode(null);
            vehicle.setCurrentStreet(currentStreetId);
            serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());
        }
        vehicle.setNotification("move");
        return true;
    }*/
}