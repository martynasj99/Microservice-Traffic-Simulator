package home_simulator.controller;

import home_simulator.Configuration;
import home_simulator.model.Home;
import home_simulator.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

    @Autowired
    private HomeService homeService;

    @PostMapping("/setup")
    public void setup(@RequestBody Configuration configuration){
        for(Home home : configuration.getHomes()){
            homeService.addHome(home);
        }
    }
}
