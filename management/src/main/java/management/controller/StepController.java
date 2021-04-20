package management.controller;

import common.State;
import management.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;


@RestController
public class StepController {
    private final Logger logger = Logger.getLogger(StepController.class.getName());

    @Autowired
    private InformationService informationService;

    @PutMapping("/step")
    public void step(@RequestBody State state){
        logger.info("Starting... [" + state.getStep() + "]");
        informationService.setTime(state.getStep());

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body = new HttpEntity<>(headers);
        //template.exchange("http://localhost:9001/time/"+informationService.getTime(), HttpMethod.PUT, body, Void.class);
    }
}
