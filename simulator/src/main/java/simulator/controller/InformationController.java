package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.model.view.InformationView;
import simulator.service.InformationService;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/information")
    @GetMapping("")
    public InformationView getNumberAtNodes(){
        return informationService.getInformationView();
    }
}
