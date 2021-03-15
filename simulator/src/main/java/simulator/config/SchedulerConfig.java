package simulator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import simulator.service.InformationService;
import simulator.service.LocationService;
import simulator.service.MapService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private MapService mapService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private InformationService informationService;


    @Autowired
    private SimpMessagingTemplate template;

    @CrossOrigin(origins = "http://localhost:8080")
    @Scheduled(fixedDelay = 3000, initialDelay = 4000)
    public void getTrafficWebSocket(){
        template.convertAndSend("/topic/traffic", locationService.getTraffic());
        template.convertAndSend("/topic/nodes", locationService.getNumberOfVehiclesAtNode());
        template.convertAndSend("/topic/map", mapService.getMap());
        template.convertAndSend("/topic/information", informationService.getInformationView());
    }
}
