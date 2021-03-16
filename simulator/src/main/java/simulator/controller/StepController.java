package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simulator.model.*;
import simulator.service.InformationService;
import simulator.service.ServiceContext;
import simulator.service.VehicleService;
import simulator.utils.GlobalClock;

import java.util.logging.Logger;


@RestController
public class StepController {

    private final Logger logger = Logger.getLogger(StepController.class.getName());

    private GlobalClock clock = GlobalClock.getInstance();

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private ServiceContext serviceContext;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        double startStep = System.currentTimeMillis();
        logger.info("Starting... [" + state.getStep() + "]");
        informationService.setTime(state.getStep());;

        for(Vehicle vehicle : vehicleService.getVehicles()){
            if(!vehicle.hasArrived() && vehicle.getNotificationUri() != null){
                if(vehicle.getNextAction() != null) {
                    Action action = vehicle.getNextAction();
                    vehicle.setNextAction(null);
                    vehicle.execute(serviceContext, action);
                    logger.info("Vehicle " + vehicle.getId() + " executed " +action.getType());
                }
            }
        }

        for(Vehicle vehicle : vehicleService.getVehicles()){
            if(!vehicle.hasArrived() && vehicle.getNotificationUri() != null){
                EnvironmentState environmentState = vehicleService.generateEnvironment(vehicle);
                vehicleService.sendNotification(vehicle, environmentState);
            }
        }

        vehicleService.updatePlans(clock.toString());
        double endStep = System.currentTimeMillis();
        logger.info("Step "+ state.getStep() + " at time: " + clock + " took " + (endStep - startStep)/100 + "s");
    }
}
