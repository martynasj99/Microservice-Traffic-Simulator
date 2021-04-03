package simulator.model.action;

import simulator.exception.InvalidActionException;
import simulator.model.network.Intersection;
import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.vehicle.Vehicle;

import java.util.*;

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

        //TRANSFER TO A NEW SIMULATOR
        Intersection currentIntersection = serviceContext.mapService.getIntersectionByName(vehicle.getCurrentNode());
        if(currentIntersection.getSimulators() != null && currentIntersection.getSimulators().containsKey(vehicle.getId().toString())){
            vehicle.transferSimulator(serviceContext.mapService.getIntersectionByName(vehicle.getCurrentNode()).getSimulators().get(vehicle.getId().toString()));
        }

        return true;
    }

    private boolean isActionSafe(Vehicle vehicle, ServiceContext serviceContext){
        Intersection nextIntersection = serviceContext.mapService.getIntersectionByName(vehicle.getNextNode());
        if(serviceContext.locationService.getLocations().getNumberOfVehiclesAtNodes().getOrDefault(vehicle.getNextNode(), 0) >= nextIntersection.getCapacity()){
            if(serviceContext.mapService.isSafeMode()){
                return false;
            }else{
                serviceContext.informationService.addCrash("Crash at Intersection: "+nextIntersection.getId() + " by vehicle: "+vehicle.getId());
            }
        }
        return true;
    }

}
