package driversweb.controller;

import driversweb.model.Action;
import driversweb.model.Driver;
import driversweb.model.EnvironmentState;
import driversweb.Logging;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
public class DriverController {

    Logger logger = Logging.getInstance().getLogger();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @PostMapping("/{id}/notifications")
    public void sendAction(@PathVariable Long id, @RequestBody Driver driver){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Action action = driver.generateAction();
        HttpEntity<Action> body = new HttpEntity<>(action, headers);

        logger.info("Sending... " + driver.getState().getId() + " " + action.getType());
        restTemplate.exchange(driver.getVehicleUri(), HttpMethod.PUT, body, Void.class);
        logger.info("Exiting... " + driver.getState().getId());
    }
}
