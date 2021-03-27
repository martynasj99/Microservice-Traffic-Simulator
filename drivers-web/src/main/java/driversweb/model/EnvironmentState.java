package driversweb.model;

import java.util.Set;

public class EnvironmentState {

    private int id;
    private String time;

    private int vehicleSpeed;
    private int streetSpeed;
    private boolean trafficLightStatus;

    private int intersectionCurrentCapacity;
    private int intersectionMaxCapacity;

    private boolean atIntersection;
    private boolean atLastCell;
    private boolean hasArrived;
    private boolean hasEndNode;

    private boolean vehicleAhead;
    private boolean obstacleInVision;
    private boolean obstacleAhead;

    private boolean canLeave;

    private Set<String> possibleActions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public int getStreetSpeed() {
        return streetSpeed;
    }

    public void setStreetSpeed(int streetSpeed) {
        this.streetSpeed = streetSpeed;
    }

    public boolean isTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(boolean trafficLightStatus) {
        this.trafficLightStatus = trafficLightStatus;
    }

    public int getIntersectionCurrentCapacity() {
        return intersectionCurrentCapacity;
    }

    public void setIntersectionCurrentCapacity(int intersectionCurrentCapacity) {
        this.intersectionCurrentCapacity = intersectionCurrentCapacity;
    }

    public int getIntersectionMaxCapacity() {
        return intersectionMaxCapacity;
    }

    public void setIntersectionMaxCapacity(int intersectionMaxCapacity) {
        this.intersectionMaxCapacity = intersectionMaxCapacity;
    }

    public boolean isAtIntersection() {
        return atIntersection;
    }

    public void setAtIntersection(boolean atIntersection) {
        this.atIntersection = atIntersection;
    }

    public boolean isAtLastCell() {
        return atLastCell;
    }

    public void setAtLastCell(boolean atLastCell) {
        this.atLastCell = atLastCell;
    }

    public boolean isHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    public boolean isVehicleAhead() {
        return vehicleAhead;
    }

    public void setVehicleAhead(boolean vehicleAhead) {
        this.vehicleAhead = vehicleAhead;
    }

    public boolean isObstacleInVision() {
        return obstacleInVision;
    }

    public void setObstacleInVision(boolean obstacleInVision) {
        this.obstacleInVision = obstacleInVision;
    }

    public boolean isObstacleAhead() {
        return obstacleAhead;
    }

    public void setObstacleAhead(boolean obstacleAhead) {
        this.obstacleAhead = obstacleAhead;
    }

    public boolean isCanLeave() {
        return canLeave;
    }

    public void setCanLeave(boolean canLeave) {
        this.canLeave = canLeave;
    }

    public Set<String> getPossibleActions() {
        return possibleActions;
    }

    public void setPossibleActions(Set<String> possibleActions) {
        this.possibleActions = possibleActions;
    }

    public boolean isHasEndNode() {
        return hasEndNode;
    }

    public void setHasEndNode(boolean hasEndNode) {
        this.hasEndNode = hasEndNode;
    }

}
