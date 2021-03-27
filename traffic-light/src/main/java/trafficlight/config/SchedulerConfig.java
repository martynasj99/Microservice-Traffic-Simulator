package trafficlight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import trafficlight.service.TrafficLightService;

import java.util.logging.Logger;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final Logger logger = Logger.getLogger(SchedulerConfig.class.getName());

    @Autowired
    private TrafficLightService trafficLightService;

    @Autowired
    private SimpMessagingTemplate template;

    @CrossOrigin(origins = "http://localhost:8080")
    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void getTrafficWebSocket(){
        if(trafficLightService.getTrafficLights() != null){
            template.convertAndSend("/topic/traffic-lights", trafficLightService.getTrafficLights());
            logger.info("Send to /topic/traffic-lights");
        }else{
            logger.warning("Could not send to /topic/traffic-lights");
        }


    }
}
