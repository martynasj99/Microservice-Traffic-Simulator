package home_simulator.controller;

import home_simulator.model.EnvironmentState;
import home_simulator.model.Home;
import home_simulator.model.State;
import home_simulator.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class StepController {

    private final Logger logger = Logger.getLogger(StepController.class.getName());

    @Autowired
    private HomeService homeService;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        logger.info("Starting... [" + state.getStep() + "]");

        for(Home home : homeService.getHomes().values()){
            if(home.getNotificationUri() != null){
                if(home.getNextAction() != null) {
                    home.setNextAction(null);
                    home.execute();
                }
            }
        }

        for(Home home : homeService.getHomes().values()){
            if(home.getNotificationUri() != null){
                EnvironmentState environmentState = new EnvironmentState();
                environmentState.setAtHome(true);
                environmentState.setId(home.getId().intValue());
                homeService.sendNotification(home, environmentState);
            }
        }
    }
}
