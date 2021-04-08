package work_simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work_simulator.Configuration;
import work_simulator.model.Work;
import work_simulator.service.WorkService;

@RestController
public class ConfigurationController {

    @Autowired
    private WorkService workService;

    @PostMapping("/setup")
    public void setup(@RequestBody Configuration configuration){
        for(Work work : configuration.getWorkPlaces()){
            workService.addWork(work);
        }
    }
}
