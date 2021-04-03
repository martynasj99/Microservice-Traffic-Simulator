package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simulator.model.Action;
import simulator.service.ServiceContext;
import simulator.model.vehicle.Vehicle;
import simulator.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceContext serviceContext;

    @GetMapping("")
    public Iterable<Vehicle> getVehicles(){
        return vehicleService.getVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable Long id){
        return vehicleService.getVehicle(id);
    }

    @PostMapping("/add")
    public void addDriver(@RequestBody Vehicle vehicle){
        vehicleService.addVehicle(vehicle);
    }

    @GetMapping("/count")
    public int getNumberOfVehicles(){
        return vehicleService.getNumberOfVehicles();
    }

    @PutMapping("/{id}/action")
    public void addAction(@PathVariable Long id, @RequestBody Action action){
        vehicleService.getVehicle(id).setNextAction(action);
    }

    @PutMapping("/{id}")
    public void notificationPath(@PathVariable Long id, @RequestBody Vehicle vehicle){
        vehicleService.getVehicle(id).setNotificationUri(vehicle.getNotificationUri());
    }
}
