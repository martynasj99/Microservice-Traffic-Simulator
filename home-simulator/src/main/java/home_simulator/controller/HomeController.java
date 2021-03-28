package home_simulator.controller;

import home_simulator.model.Action;
import home_simulator.model.Home;
import home_simulator.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/homes")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("")
    public Map<Long, Home> getHomes(){
        return homeService.getHomes();
    }

    @PutMapping("/{id}/action")
    public void addAction(@PathVariable Long id, @RequestBody Action action){
        homeService.getHome(id).setNextAction(action);
    }

    @PutMapping("/{id}")
    public void notificationPath(@PathVariable Long id, @RequestBody Home home){
        homeService.getHome(id).setNotificationUri(home.getNotificationUri());
    }
}
