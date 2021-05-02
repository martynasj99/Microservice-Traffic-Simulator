package simulator.controller;

import common.Action;
import common.EnvironmentState;
import common.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simulator.model.*;
import simulator.model.vehicle.Vehicle;
import simulator.service.ServiceContext;
import simulator.service.VehicleService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class StepController {
    private final Logger logger = Logger.getLogger(StepController.class.getName());

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceContext serviceContext;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        double startStep = System.currentTimeMillis();
        logger.info("Starting... [" + state.getStep() + "]");

        for(Vehicle vehicle : vehicleService.getVehicles()){
            if(vehicle.getNotificationUri() != null){
                if(vehicle.getNextAction() != null) {
                    List<String > params = new ArrayList<>();
                    Action action = vehicle.getNextAction();
                    vehicle.setNextAction(null);
                    if(action.getNewDestination() != null) vehicle.setEndNode(action.getNewDestination());
                    if(action.getStreet() != null) params.add(action.getStreet());
                    vehicle.execute(serviceContext, action, params);
                    logger.info("Vehicle " + vehicle.getId() + " executed " +action.getType());
                }
            }
        }

        for(Vehicle vehicle : vehicleService.getVehicles()){
            if(vehicle.getNotificationUri() != null){
                EnvironmentState environmentState = vehicleService.generateEnvironment(vehicle);
                vehicle.sendNotification(environmentState);
            }
        }

        double endStep = System.currentTimeMillis();
        logger.info("Step "+ state.getStep() + " at time: "  + " took " + (endStep - startStep)/100 + "s");
    }
}
