package work_simulator.controller;

import common.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work_simulator.model.Work;
import work_simulator.service.WorkService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/homes")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("")
    public Map<Long, Work> getWorkPlaces(){
        return workService.getWorkPlaces();
    }

    @PutMapping("/{id}/action")
    public void addAction(@PathVariable Long id, @RequestBody Action action){
        Work w = workService.getWork(id);
        if(w.getNextAction() == null) w.setNextAction(new HashMap<>());
        workService.getWork(id).addNextAction(action.getAgentId(), action);
    }

    @PutMapping("/{id}")
    public void notificationPath(@PathVariable Long id, @RequestBody Work work){
        Work w = workService.getWork(id);
        if(w.getNotificationUri() == null)
            w.setNotificationUri(new HashMap<>());

        for(Map.Entry<Long, String> uri : work.getNotificationUri().entrySet()){
            workService.getWork(id).addNotificationUri(uri.getKey(), uri.getValue());
        }
    }
}
