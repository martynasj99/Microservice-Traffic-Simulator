package common;

import java.util.Set;

public class EnvironmentState {

    private int id;
    private String type;

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

    private boolean atHome;

    private Set<String> possibleActions;

    private Iterable<EnvironmentStreet> streets;
    private VehicleLocation vehicleLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isAtHome() {
        return atHome;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }


    public Iterable<EnvironmentStreet> getStreets() {
        return streets;
    }

    public void setStreets(Iterable<EnvironmentStreet> streets) {
        this.streets = streets;
    }


    public VehicleLocation getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(VehicleLocation vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }

    @Override
    public String toString() {
        return "EnvironmentState{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", vehicleSpeed=" + vehicleSpeed +
                ", streetSpeed=" + streetSpeed +
                ", trafficLightStatus=" + trafficLightStatus +
                ", intersectionCurrentCapacity=" + intersectionCurrentCapacity +
                ", intersectionMaxCapacity=" + intersectionMaxCapacity +
                ", atIntersection=" + atIntersection +
                ", atLastCell=" + atLastCell +
                ", hasArrived=" + hasArrived +
                ", hasEndNode=" + hasEndNode +
                ", vehicleAhead=" + vehicleAhead +
                ", obstacleInVision=" + obstacleInVision +
                ", obstacleAhead=" + obstacleAhead +
                ", canLeave=" + canLeave +
                ", atHome=" + atHome +
                ", possibleActions=" + possibleActions +
                ", streets=" + streets +
                ", vehicleLocation=" + vehicleLocation +
                '}';
    }
}
