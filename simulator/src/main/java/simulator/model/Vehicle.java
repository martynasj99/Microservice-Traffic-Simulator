package simulator.model;

import simulator.model.action.*;
import simulator.model.plan.DayPlan;
import simulator.model.plan.VehicleRoute;
import simulator.service.ServiceContext;

import java.util.*;

public class Vehicle {
    private static Map<String, ActionFactory<?>> executors = new Hashtable<>();
    static {
        executors.put("accelerate", new AccelerateFactory());
        executors.put("decelerate", new DecelerateFactory());
        executors.put("wait", new WaitFactory());
        executors.put("move", new MoveFactory());
        executors.put("enter", new EnterIntersectionFactory());
        executors.put("leave", new LeaveIntersectionFactory());
    }

    private Long id;

    private Long currentStreet;
    private String currentNode;
    private String nextNode;
    private VehicleRoute route;
    private int speed;
    private int progress;
    private int streetProgress;
    private String notificationUri;
    private Action nextAction;
    private List<String> validActions;
    private String notification;

    private DayPlan plan;

    public Vehicle() {
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

    public List<String> getValidActions() {
        return validActions;
    }

    public void setValidActions(List<String> validActions) {
        this.validActions = validActions;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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

    public DayPlan getPlan() {
        return plan;
    }

    public void setPlan(DayPlan plan) {
        this.plan = plan;
    }
}
