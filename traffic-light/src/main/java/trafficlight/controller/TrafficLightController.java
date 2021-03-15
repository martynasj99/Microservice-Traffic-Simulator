package trafficlight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import trafficlight.model.TrafficLightSystem;
import trafficlight.service.TrafficLightService;

import java.util.Map;

@RestController
@RequestMapping("/traffic-lights")
public class TrafficLightController {

    @Autowired
    private TrafficLightService trafficLightService;

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/traffic-lights")
    @GetMapping("")
    public Map<Long, TrafficLightSystem> getTrafficLights(){
        return trafficLightService.getTrafficLights();
    }

    @PostMapping("/add/{nodeId}")
    public void addTrafficLight(@PathVariable Long nodeId, @RequestBody TrafficLightSystem trafficLightSystem){
        trafficLightService.addTrafficLight(nodeId, trafficLightSystem.getInLinks(), trafficLightSystem.getOutLinks());
    }
}
