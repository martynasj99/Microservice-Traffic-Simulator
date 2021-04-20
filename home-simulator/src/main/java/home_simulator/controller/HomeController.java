package home_simulator.controller;

import common.Action;
import home_simulator.model.Home;
import home_simulator.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/homes")
    @GetMapping("")
    public Map<Long, Home> getHomes(){
        return homeService.getHomes();
    }

    @PutMapping("/{id}/action")
    public void addAction(@PathVariable Long id, @RequestBody Action action){
        Home h = homeService.getHome(id);
        if(h.getNextAction() == null) h.setNextAction(new HashMap<>());
        homeService.getHome(id).addNextAction(action.getAgentId(), action);
    }

    @PutMapping("/{id}")
    public void notificationPath(@PathVariable Long id, @RequestBody Home home){
        Home h = homeService.getHome(id);
        if(h.getNotificationUri() == null)
            h.setNotificationUri(new HashMap<>());

        for(Map.Entry<Long, String> uri : home.getNotificationUri().entrySet()){
            homeService.getHome(id).addNotificationUri(uri.getKey(), uri.getValue());
        }
    }
}
