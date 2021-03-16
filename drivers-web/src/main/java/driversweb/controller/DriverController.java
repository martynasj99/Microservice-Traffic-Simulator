package driversweb.controller;

import driversweb.model.Action;
import driversweb.model.Driver;
import driversweb.model.EnvironmentState;
import driversweb.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
public class DriverController {

    Logger logger = Logger.getLogger(DriverController.class.getName());

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private DriverService driverService;

    @PostMapping("/{id}/notifications")
    public void sendAction(@PathVariable Long id, @RequestBody EnvironmentState state){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Driver driver = driverService.getDrivers().get(id);
        Action action = driver.generateAction(state);

        if(!state.getPossibleActions().contains(action.getType())){
            logger.warning("ACTION: " + action.getType() +" NOT POSSIBLE");
        }

        HttpEntity<Action> body = new HttpEntity<>(action, headers);

        logger.info("Sending... " + state.getId() + " " + action.getType());
        restTemplate.exchange("http://localhost:8081/vehicles/"+id+"/action", HttpMethod.PUT, body, Void.class);
        logger.info("Exiting... " + state.getId());
    }
}
