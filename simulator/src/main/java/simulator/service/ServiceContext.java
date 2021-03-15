package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simulator.service.LocationService;
import simulator.service.MapService;
import simulator.service.VehicleService;

@Service
public class ServiceContext {
    @Autowired public LocationService locationService;
    @Autowired public MapService mapService;
    @Autowired public VehicleService vehicleService;
}
