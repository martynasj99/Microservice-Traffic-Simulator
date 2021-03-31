package home_simulator.model;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Home {

    private Long id;
    private Action nextAction;
    private Set<String> notificationUri;
    private String link = "http://localhost:8081/vehicles/4";

    public Home() {
    }

    public void execute(Action action){
        if(action.getType().equals("plan")){
            RestTemplate restTemplate = new RestTemplate();
            JSONObject object = new JSONObject();

            object.put("notificationUri", "http://localhost:9001/4/notifications");
            notificationUri.remove("http://localhost:9001/4/notifications");
            setNotificationUri(null);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
            restTemplate.exchange(link, HttpMethod.PUT, body, Void.class);

            HttpEntity<Action> actionBody = new HttpEntity<>(action, headers);
            restTemplate.exchange(link+"/action", HttpMethod.PUT, actionBody, Void.class);
        }else {
            System.out.println(id + " is Watching TV!");
        }

    }

    public void sendNotification(EnvironmentState state){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EnvironmentState> request = new HttpEntity<>(state, headers);

        Set<String> notifications = notificationUri;
        for(String uri : notifications)
            template.exchange(uri+"/home", HttpMethod.PUT, request, Void.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public void addNotificationUri(String notificationUri){
        getNotificationUri().add(notificationUri);
    }

    public void removeNotificationUri(String notificationUri){
        getNotificationUri().remove(notificationUri);
    }

    public void setNotificationUri(Set<String> notificationUri) {
        this.notificationUri = notificationUri;
    }

    public Set<String> getNotificationUri() {
        return notificationUri;
    }
}
