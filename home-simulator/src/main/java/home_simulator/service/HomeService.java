package home_simulator.service;

import home_simulator.model.Home;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {

    private Map<Long, Home> homes = new HashMap<>();

    public void addHome(Home home){
        homes.put(home.getId(), home);
    }

    public Map<Long, Home> getHomes(){
        return homes;
    }

    public Home getHome(Long id){
        return getHomes().get(id);
    }

}
