package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.model.vehicle.RoundTrip;
import simulator.model.view.InformationView;
import simulator.service.InformationService;

import java.util.List;

@RestController
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping("/crash-log")
    public List<String> getCrashLog(){
        return informationService.getCrashLog();
    }

    @GetMapping("/round-trips")
    public List<RoundTrip> getRoundTrips(){
        return informationService.getRoundTrips();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/information")
    @GetMapping("/information")
    public InformationView getInformation(){
        return informationService.getInformationView();
    }
}
