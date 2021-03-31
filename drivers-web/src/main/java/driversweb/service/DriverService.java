package driversweb.service;

import driversweb.model.Driver;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DriverService {

    private Map<Long, Driver> drivers = new HashMap<>();
    private String time;

    public Map<Long, Driver> getDrivers(){
        return drivers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
