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

/*    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        Traffic traffic = serviceContext.locationService.getTraffic().get(vehicle.getCurrentStreet());

        if(canAccelerate(vehicle, serviceContext, traffic)){
            vehicle.setNotification("accelerate");
            return false;
        }

        for (int i = 0; i < vehicle.getSpeed(); i++) { //move along traffic by increment of speed (during a step)
            if (vehicle.getStreetProgress() == (traffic.getCells() - 1) ) { //in the last index
                vehicle.setNotification("enter");
                return false;
            }

            if (!shouldSlowDown(vehicle, traffic, 2)) {
                traffic.getTraffic()[vehicle.getStreetProgress() + 1] = traffic.getTraffic()[vehicle.getStreetProgress()];
                traffic.getTraffic()[vehicle.getStreetProgress()] = null;
                vehicle.setStreetProgress(vehicle.getStreetProgress() + 1);
                serviceContext.locationService.updateOnAction(vehicle, traffic, vehicle.getCurrentStreet());
            }else{
                vehicle.setNotification("decelerate");
                return false;
            }
        }
        vehicle.setNotification("move");
        return true;
    }*/

    private boolean shouldSlowDown(Vehicle vehicle, Traffic traffic, int vision){
        if(traffic.getTraffic()[vehicle.getStreetProgress()+1] != null) return true;

        for (int i = vehicle.getStreetProgress()+1; i < traffic.getCells() && i  <= vehicle.getStreetProgress() + vision; i++) {
            if( (traffic.getTraffic()[i] != null || i == traffic.getCells()-1) && vehicle.getSpeed() > 1) return true;
        }
        return false;
    }

    private boolean canAccelerate(Vehicle vehicle, ServiceContext serviceContext, Traffic traffic){
        return ((vehicle.getStreetProgress() == (traffic.getCells() -1)
                && canEnterIntersection(vehicle, serviceContext))
                || (vehicle.getStreetProgress() < (traffic.getCells() -1) && traffic.getTraffic()[vehicle.getStreetProgress()+1] == null) )
                && vehicle.getSpeed() == 0;
    }

    private boolean canEnterIntersection(Vehicle vehicle, ServiceContext serviceContext){
        return serviceContext.vehicleService.checkTrafficLightStatus(vehicle.getCurrentStreet())
                && (!serviceContext.locationService.getNumberOfVehiclesAtNode().containsKey(serviceContext.mapService.getIntersectionByName(vehicle.getNextNode()).getId())
                || !serviceContext.mapService.getIntersectionByName(vehicle.getNextNode()).getName().startsWith("J"));
    }
}