package home_simulator.service;

import home_simulator.model.EnvironmentState;
import home_simulator.model.Home;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public void sendNotification(Home home, EnvironmentState state){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EnvironmentState> request = new HttpEntity<>(state, headers);

        String uri = home.getNotificationUri();
        template.exchange(uri+"/home", HttpMethod.PUT, request, Void.class);
    }

}
