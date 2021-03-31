package driversweb.controller;

import driversweb.Configuration;
import driversweb.model.plan.DayPlan;
import driversweb.model.Driver;
import driversweb.service.DriverService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class ConfigurationController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/setup")
    public void setup(@RequestBody Configuration configuration){
        RestTemplate restTemplate = new RestTemplate();
        Map<Long, DayPlan> plans = configuration.getPlans();
        for(long i = 1; i <= configuration.getInitial(); i++){
            JSONObject object = new JSONObject();
            object.put("notificationUri", "http://localhost:9001/"+i+"/notifications");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
            Driver driver = new Driver();
            driver.setId(i);
            driver.setPlan(plans.getOrDefault(driver.getId(), null));
            driverService.getDrivers().put(driver.getId(), driver);
            restTemplate.exchange("http://localhost:8081/vehicles/"+i, HttpMethod.PUT, body, Void.class);
        }
    }
}
