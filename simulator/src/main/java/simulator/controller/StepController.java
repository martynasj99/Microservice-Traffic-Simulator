package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simulator.Logging;
import simulator.model.*;
import simulator.service.ServiceContext;
import simulator.service.VehicleService;
import simulator.utils.GlobalClock;

import java.util.logging.Logger;


@RestController
public class StepController {
    Logger logger = Logging.getInstance().getLogger();

    private GlobalClock clock = GlobalClock.getInstance();

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceContext serviceContext;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        double startStep = System.currentTimeMillis();
        vehicleService.step(state);
        double endStep = System.currentTimeMillis();
        logger.info("Step "+ state.getStep() + " at time: " + clock + " took " + (endStep - startStep)/100 + "s");
    }
}
