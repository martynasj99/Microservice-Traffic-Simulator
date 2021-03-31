package simulator.model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import simulator.exception.NoAgentAttachedException;
import simulator.model.action.*;
import simulator.model.plan.VehicleRoute;
import simulator.service.ServiceContext;

import java.util.*;
import java.util.logging.Logger;

public class Vehicle {
    Logger logger = Logger.getLogger(Vehicle.class.getName());

    private final static Map<String, ActionFactory<?>> executors = new Hashtable<>();
    static {
        executors.put("accelerate", new AccelerateFactory());
        executors.put("decelerate", new DecelerateFactory());
        executors.put("wait", new WaitFactory());
        executors.put("move", new MoveFactory());
        executors.put("enter", new EnterIntersectionFactory());
        executors.put("leave", new LeaveIntersectionFactory());
        executors.put("plan", new ChangePlanFactory());
    }

    private Long id;

    private Long currentStreet;
    private String currentNode;
    private String nextNode;
    private VehicleRoute route;
    private int speed;
    private int progress;
    private int vision;
    private int streetProgress;
    private String notificationUri;
    private Action nextAction;

    public Vehicle() {
        this.speed = 1;
        this.progress = 2;
        this.vision = 5;
    }

    public void sendNotification(EnvironmentState state){
        if(notificationUri == null) throw new NoAgentAttachedException("No Agent is attached to vehicle: " + id);

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EnvironmentState> request = new HttpEntity<>(state, headers);

        logger.info("POSTING Notification: " + state.getId() + " Vehicle: " + id);
        //template.postForObject(uri, request, Void.class);
        template.exchange(notificationUri+"/traffic", HttpMethod.PUT, request, Void.class);
        logger.info(" Notification Sent : "+state.getId()+" sent from : " + id);
    }

    public void nextStage(){
        currentNode = nextNode;
        nextNode = progress < route.getPath().size() ? route.getPath().get(progress++) : nextNode;
        currentStreet = null;
        streetProgress = 0;
    }

    public synchronized boolean execute(ServiceContext serviceContext, Action action){
        ActionExecutor executer = executors.get(action.getType()).create();
        return executer.execute(this, serviceContext, null);
    }


    public boolean hasArrived(){
        return currentNode != null && currentNode.equals(route.getEndNode());
    }

    public void newPlan(){
        setNextAction(null);
        setPath(null);
        setStartNode(getCurrentNode());
        setProgress(2);
        setCurrentNode(getStartNode());
        setNextNode(null);
    }

    public void accelerate(){
        this.speed++;
    }

    public void decelerate(){
        this.speed--;
    }

    public Long getId() {
        return id;
    }

    public List<String> getPath() {
        return getRoute().getPath();
    }

    public Long getCurrentStreet() {
        return currentStreet;
    }

    public void setCurrentStreet(Long currentStreet) {
        this.currentStreet = currentStreet;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public VehicleRoute getRoute() {
        return route;
    }

    public void setRoute(VehicleRoute route) {
        this.route = route;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStreetProgress() {
        return streetProgress;
    }

    public void setStreetProgress(int streetProgress) {
        this.streetProgress = streetProgress;
    }

    public String getNotificationUri() {
        return notificationUri;
    }

    public void setNotificationUri(String notificationUri) {
        this.notificationUri = notificationUri;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public String getStartNode(){
        return getRoute().getStartNode();
    }

    public void setStartNode(String node){
        getRoute().setStartNode(node);
    }
    public void setEndNode(String node){
        getRoute().setEndNode(node);
    }

    public String getEndNode(){
        return getRoute().getEndNode();
    }

    public void setPath(List<String> path){
        getRoute().setPath(path);
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }
}
