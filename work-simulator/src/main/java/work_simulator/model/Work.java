package work_simulator.model;

import common.Action;
import common.EnvironmentState;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.logging.Logger;

public class Work {
    private Logger logger = Logger.getLogger(Work.class.getName());

    private Long id;
    private Map<Long, Action> nextAction;
    private Map<Long, String> notificationUri;
    private String link = "http://localhost:8081/vehicles/";

    public Work() {
    }

    public void execute(Action action){
        if(action.getType().equals("plan")){
            logger.info("Switching back to traffic: Work " +id + " Agent: " + action.getAgentId());

            RestTemplate restTemplate = new RestTemplate();
            JSONObject object = new JSONObject();

            object.put("notificationUri", notificationUri.get(action.getAgentId()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
            restTemplate.exchange(link+action.getAgentId(), HttpMethod.PUT, body, Void.class); //send notification uri to traffic

            HttpEntity<Action> actionBody = new HttpEntity<>(action, headers);
            restTemplate.exchange(link+action.getAgentId()+"/action", HttpMethod.PUT, actionBody, Void.class); //send the action to traffic
            notificationUri.remove(action.getAgentId());
        }else {
            logger.info(action.getAgentId() + " is Working! At home: " + id);
        }
    }

    public void sendNotification(EnvironmentState state){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EnvironmentState> request = new HttpEntity<>(state, headers);

        for(String uri : notificationUri.values())
            template.exchange(uri, HttpMethod.PUT, request, Void.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Action> getNextAction() {
        return nextAction;
    }

    public void setNextAction(Map<Long, Action> nextAction) {
        this.nextAction = nextAction;
    }

    public Map<Long, String> getNotificationUri() {
        return notificationUri;
    }

    public void setNotificationUri(Map<Long, String> notificationUri) {
        this.notificationUri = notificationUri;
    }

    public void addNextAction(Long id, Action action){
        nextAction.put(id, action);
    }

    public void addNotificationUri(Long id, String notificationUri){
        getNotificationUri().put(id, notificationUri);
    }

    public void removeNotificationUri(Long id){
        getNotificationUri().remove(id);
    }
}
