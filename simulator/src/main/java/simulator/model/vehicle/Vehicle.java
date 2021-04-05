package simulator.model.vehicle;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import simulator.exception.NoAgentAttachedException;
import simulator.model.Action;
import simulator.model.EnvironmentState;
import simulator.model.action.*;
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
    private RoundTrip roundTrip;
    private VehicleRoute route;
    private VehicleLocation location;
    private VehicleProgress vehicleProgress;
    private int speed;
    private int vision;
    private String notificationUri;
    private Action nextAction;

    public Vehicle() {
        this.roundTrip = new RoundTrip();
        roundTrip.setVehicleId(id);
        roundTrip.start();
        this.vehicleProgress = new VehicleProgress();
        this.speed = 1;
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
        template.exchange(notificationUri, HttpMethod.PUT, request, Void.class);
        logger.info(" Notification Sent : "+state.getId()+" sent from : " + id);
    }

    public void transferSimulator(String simulatorUrl){
        roundTrip.end();

        RestTemplate restTemplate = new RestTemplate();
        JSONObject object = new JSONObject();

        Map<Long, String> notificationUri = new HashMap<>();
        notificationUri.put(id, this.notificationUri);
        object.put("notificationUri", notificationUri);
        setNotificationUri(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
        restTemplate.exchange(simulatorUrl, HttpMethod.PUT, body, Void.class);
        logger.info("Vehicle: " + id + " is transferring to simulator: " + simulatorUrl);
    }

    public void nextStage(){
        setCurrentNode(getNextNode());
        setNextNode(getProgress() < route.pathSize() ? route.getNodeAt(getProgress()) : getNextNode());
        vehicleProgress.nextTotalProgress();
        setCurrentStreet(null);
        setStreetProgress(0);
    }

    public synchronized boolean execute(ServiceContext serviceContext, Action action){
        ActionExecutor executer = executors.get(action.getType()).create();
        return executer.execute(this, serviceContext, null);
    }

    public boolean hasArrived(){
        return getCurrentNode() != null && getCurrentNode().equals(route.getEndNode());
    }

    public void newPlan(){
        setNextAction(null);
        setPath(null);
        setStartNode(getCurrentNode());
        setProgress(2);
        setCurrentNode(getStartNode());
        setNextNode(null);
        getRoundTrip().start();
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
        return getLocation().getCurrentStreet();
    }

    public void setCurrentStreet(Long currentStreet) {
        getLocation().setCurrentStreet(currentStreet);
    }

    public String getCurrentNode() {
        return getLocation().getCurrentNode();
    }

    public void setCurrentNode(String currentNode) {
        getLocation().setCurrentNode(currentNode);
    }

    public String getNextNode() {
        return getLocation().getNextNode();
    }

    public void setNextNode(String nextNode) {
        getLocation().setNextNode(nextNode);
    }

    public VehicleLocation getLocation() {
        return location;
    }

    public void setLocation(VehicleLocation location) {
        this.location = location;
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
        return getVehicleProgress().getTotalProgress();
    }

    public void setProgress(int progress) {
        getVehicleProgress().setTotalProgress(progress);
    }

    public int getStreetProgress() {
        return getVehicleProgress().getCurrentStreetProgress();
    }

    public void setStreetProgress(int streetProgress) {
        getVehicleProgress().setCurrentStreetProgress(streetProgress);
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

    public VehicleProgress getVehicleProgress() {
        return vehicleProgress;
    }

    public void setVehicleProgress(VehicleProgress vehicleProgress) {
        this.vehicleProgress = vehicleProgress;
    }

    public RoundTrip getRoundTrip() {
        return roundTrip;
    }
}
