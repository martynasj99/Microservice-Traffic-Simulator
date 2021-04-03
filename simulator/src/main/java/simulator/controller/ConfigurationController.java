package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simulator.Configuration;
import simulator.exception.InvalidException;
import simulator.model.vehicle.Vehicle;
import simulator.service.MapService;
import simulator.service.VehicleService;

@RestController
public class ConfigurationController {

    @Autowired
    private MapService mapService;

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/setup")
    public void setup(@RequestBody Configuration configuration) throws InvalidException {

        for(Vehicle vehicle : configuration.getVehicles()){
            vehicleService.addVehicle(vehicle);
        }

        mapService.configure(configuration);
        mapService.generateTrafficLights();
    }

}
