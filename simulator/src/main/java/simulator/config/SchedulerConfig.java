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

import java.util.logging.Logger;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final Logger logger = Logger.getLogger(SchedulerConfig.class.getName());

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
        if(locationService.getTraffic() != null)
            template.convertAndSend("/topic/traffic", locationService.getTraffic());
        else logger.warning("Could not send to /topic/traffic");

        if(locationService.getNumberOfVehiclesAtNode() != null)
            template.convertAndSend("/topic/nodes", locationService.getNumberOfVehiclesAtNode());
        else logger.warning("Could not send to /topic/nodes");

        if(mapService.getMap() != null)
            template.convertAndSend("/topic/map", mapService.getMap());
        else logger.warning("Could not send to /topic/map");

        if(informationService.getInformationView() != null)
            template.convertAndSend("/topic/information", informationService.getInformationView());
        else logger.warning("Could not send to /topic/information");
    }
}
