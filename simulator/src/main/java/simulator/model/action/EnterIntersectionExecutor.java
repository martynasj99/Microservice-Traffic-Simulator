package simulator.model.action;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import simulator.exception.InvalidActionException;
import simulator.service.ServiceContext;
import simulator.model.Traffic;
import simulator.model.Vehicle;

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
        if(vehicle.getCurrentNode() != null && serviceContext.mapService.getIntersectionByName(vehicle.getCurrentNode()).getSimulators() != null){
            if(serviceContext.mapService.getIntersectionByName(vehicle.getCurrentNode()).getSimulators().containsKey(vehicle.getId().toString())){
                RestTemplate restTemplate = new RestTemplate();
                JSONObject object = new JSONObject();

                Map<Long, String> notificationUri = new HashMap<>();
                notificationUri.put(vehicle.getId(), vehicle.getNotificationUri());
                object.put("notificationUri", notificationUri);
                vehicle.setNotificationUri(null);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
                restTemplate.exchange(serviceContext.mapService.getIntersectionByName(vehicle.getCurrentNode()).getSimulators().get(vehicle.getId().toString()), HttpMethod.PUT, body, Void.class);
            }
        }

        return true;
    }

    private boolean isActionSafe(Vehicle vehicle, ServiceContext serviceContext){
        if(serviceContext.locationService.getLocations().getNumberOfVehiclesAtNodes().getOrDefault(vehicle.getNextNode(), 0) >= serviceContext.mapService.getIntersectionByName(vehicle.getNextNode()).getCapacity()){
            if(serviceContext.mapService.isSafeMode()){
                return false;
            }else{
                //serviceContext.informationService.getInformationView().setMessage("CRASH: " + vehicle.getId());
            }
        }
        return true;
    }

}
