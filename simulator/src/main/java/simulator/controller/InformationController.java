package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
