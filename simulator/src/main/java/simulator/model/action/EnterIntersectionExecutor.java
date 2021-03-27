package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.Vehicle;

import java.util.List;

public class EnterIntersectionExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        if(vehicle.getCurrentNode() != null || vehicle.getNextNode() == null) throw new InvalidActionException("This vehicle cannot perform this action: " + vehicle.getNextAction());
        if(!isActionSafe(vehicle, serviceContext)) return false;

        Long curr = vehicle.getCurrentStreet();
        Traffic traffic = serviceContext.locationService.getTraffic().get(vehicle.getCurrentStreet());
        traffic.getTraffic()[traffic.getCells()-1] = null;
        vehicle.nextStage();

        serviceContext.locationService.updateOnAction(vehicle,traffic, curr);
        return true;
    }

    private boolean isActionSafe(Vehicle vehicle, ServiceContext serviceContext){
        if(serviceContext.locationService.getLocations().getNumberOfVehiclesAtNodes().getOrDefault(vehicle.getNextNode(), 0) >= serviceContext.mapService.getIntersectionByName(vehicle.getNextNode()).getCapacity()){
            if(serviceContext.mapService.isSafeMode()){
                return false;
            }else{
                serviceContext.informationService.getInformationView().setMessage("CRASH: " + vehicle.getId());
            }
        }
        return true;
    }

}
