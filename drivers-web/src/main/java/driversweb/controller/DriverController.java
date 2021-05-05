package driversweb.controller;

import common.Action;
import common.EnvironmentState;
import driversweb.model.Driver;
import driversweb.service.DriverService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
public class DriverController {

    Logger logger = Logger.getLogger(DriverController.class.getName());

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private DriverService driverService;

    @PutMapping("/{id}/notifications")
    public void sendAction(@PathVariable Long id, @RequestBody EnvironmentState state){
        String type = state.getType();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Driver driver = driverService.getDrivers().get(id);
        driver.setTime(driverService.getTime());
        Action action = driver.generateAction(state, type);
        if(!state.getPossibleActions().contains(action.getType())){
            logger.warning("ACTION: " + action.getType() +" NOT POSSIBLE");
        }

        HttpEntity<Action> body = new HttpEntity<>(action, headers);
        logger.info("Sending... " + state.getId() + " " + action.getType() + "Time: " + driverService.getTime());

        switch (type){
            case "traffic":
                restTemplate.exchange("http://localhost:8081/vehicles/"+state.getId()+"/action", HttpMethod.PUT, body, Void.class);
                break;
            case "home":
                restTemplate.exchange("http://localhost:8084/home/"+state.getId()+"/action", HttpMethod.PUT, body, Void.class);
                break;
            case "work":
                restTemplate.exchange("http://localhost:8085/work/"+state.getId()+"/action", HttpMethod.PUT, body, Void.class);
                break;
            default:
                logger.warning("Invalid type");
                break;
        }

        logger.info("Exiting... " + state.getId());
    }

    @PutMapping("/main/time")
    public void setTime(@RequestBody JSONObject time){
        driverService.setTime((String) time.get("time"));
    }
}
