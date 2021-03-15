package simulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvironmentState {

    private int id;

    private int vehicleStreetProgress;
    private int streetLength;
    private int vehicleSpeed;
    private int streetSpeed;
    private boolean trafficLightStatus;
    private boolean hasVehiclesInNode;
    private String intersectionName;
    private int numberAtIntersection;

    private boolean atIntersection;
    private boolean atLastCell;
    private boolean hasArrived;

    private boolean trafficInVision;
    private boolean trafficAhead;

    private boolean canLeave;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleStreetProgress() {
        return vehicleStreetProgress;
    }

    public void setVehicleStreetProgress(int vehicleStreetProgress) {
        this.vehicleStreetProgress = vehicleStreetProgress;
    }

    public int getStreetLength() {
        return streetLength;
    }

    public void setStreetLength(int streetLength) {
        this.streetLength = streetLength;
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

    public boolean isHasVehiclesInNode() {
        return hasVehiclesInNode;
    }

    public void setHasVehiclesInNode(boolean hasVehiclesInNode) {
        this.hasVehiclesInNode = hasVehiclesInNode;
    }

    public String getIntersectionName() {
        return intersectionName;
    }

    public void setIntersectionName(String intersectionName) {
        this.intersectionName = intersectionName;
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

    public int getNumberAtIntersection() {
        return numberAtIntersection;
    }

    public void setNumberAtIntersection(int numberAtIntersection) {
        this.numberAtIntersection = numberAtIntersection;
    }

    public boolean isTrafficInVision() {
        return trafficInVision;
    }

    public void setTrafficInVision(boolean trafficInVision) {
        this.trafficInVision = trafficInVision;
    }

    public boolean isTrafficAhead() {
        return trafficAhead;
    }

    public void setTrafficAhead(boolean trafficAhead) {
        this.trafficAhead = trafficAhead;
    }

    public boolean isCanLeave() {
        return canLeave;
    }

    public void setCanLeave(boolean canLeave) {
        this.canLeave = canLeave;
    }

    @Override
    public String toString() {
        return "EnvironmentState{" +
                "id=" + id +
                ", vehicleStreetProgress=" + vehicleStreetProgress +
                ", streetLength=" + streetLength +
                ", vehicleSpeed=" + vehicleSpeed +
                ", streetSpeed=" + streetSpeed +
                ", trafficLightStatus=" + trafficLightStatus +
                ", hasVehiclesInNode=" + hasVehiclesInNode +
                ", intersectionName='" + intersectionName + '\'' +
                ", numberAtIntersection=" + numberAtIntersection +
                ", atIntersection=" + atIntersection +
                ", atLastCell=" + atLastCell +
                ", hasArrived=" + hasArrived +
                ", trafficInVision=" + trafficInVision +
                ", trafficAhead=" + trafficAhead +
                ", canLeave=" + canLeave +
                '}';
    }
}
