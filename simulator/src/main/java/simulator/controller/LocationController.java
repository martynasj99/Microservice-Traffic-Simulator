package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.model.view.LocationView;
import simulator.model.Vehicle;
import simulator.model.Traffic;
import simulator.service.LocationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("")
    public LocationView getNumberAtLocations(){
        return locationService.getLocations();
    }

    @GetMapping("/nodes")
    public Map<String, List<Vehicle>> getVehicleAtLocation(){
        return locationService.getVehiclesAtNodes();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/nodes")
    @GetMapping("/nodes/number")
    public Map<String, Integer> getNumberAtNodes(){
        return locationService.getNumberOfVehiclesAtNode();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/traffic")
    @GetMapping("/drivers/traffic")
    public Map<Long, Traffic> getTraffic(){
        return locationService.getTraffic();
    }
}
