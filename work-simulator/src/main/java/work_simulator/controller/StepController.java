package work_simulator.controller;
import common.Action;
import common.EnvironmentState;
import common.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work_simulator.model.Work;
import work_simulator.service.WorkService;

import java.util.logging.Logger;

@RestController
public class StepController {

    private final Logger logger = Logger.getLogger(StepController.class.getName());

    @Autowired
    private WorkService workService;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        logger.info("Starting... [" + state.getStep() + "]");

        for(Work work : workService.getWorkPlaces().values()){
            if(work.getNotificationUri() != null){
                if(work.getNextAction() != null) {
                    Iterable<Action> actions = work.getNextAction().values();
                    work.setNextAction(null);
                    for(Action action : actions){
                        work.execute(action);
                    }
                }
            }
        }

        for(Work work : workService.getWorkPlaces().values()){
            if(work.getNotificationUri() != null){
                EnvironmentState environmentState = new EnvironmentState();
                environmentState.setAtHome(true);
                environmentState.setType("work");
                environmentState.setId(work.getId().intValue());
                work.sendNotification(environmentState);
            }
        }
    }
}
