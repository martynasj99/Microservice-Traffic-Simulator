package home_simulator.controller;

import common.Action;
import common.EnvironmentState;
import common.State;
import home_simulator.model.Home;
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
                    Iterable<Action> actions = home.getNextAction().values();
                    home.setNextAction(null);
                    for(Action action : actions){
                        home.execute(action);
                    }
                }
            }
        }

        for(Home home : homeService.getHomes().values()){
            if(home.getNotificationUri() != null){
                EnvironmentState environmentState = new EnvironmentState();
                environmentState.setAtHome(true);
                environmentState.setType("home");
                environmentState.setId(home.getId().intValue());
                home.sendNotification(environmentState);
            }
        }
    }
}
