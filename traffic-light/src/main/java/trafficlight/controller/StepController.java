package trafficlight.controller;

import common.State;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import trafficlight.service.TrafficLightService;

@RestController
public class StepController {


    private RestTemplate template = new RestTemplate();

    @Autowired
    private TrafficLightService trafficLightService;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        System.out.println("STEP: " + state.getStep());
        trafficLightService.step(state);

        JSONObject streetStatus = new JSONObject();
        streetStatus.put("status", trafficLightService.getStreetStatuses());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> body = new HttpEntity<>(streetStatus.toString(), headers);
        template.exchange("http://localhost:8081/map/traffic-lights", HttpMethod.PUT, body, Void.class);
    }

}
