package simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceContext {
    @Autowired public LocationService locationService;
    @Autowired public MapService mapService;
    @Autowired public VehicleService vehicleService;
    @Autowired public InformationService informationService;
}
