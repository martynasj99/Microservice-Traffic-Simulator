package driversweb.model;

public class Action {
    private String type;
    private String newDestination;

    public Action() {
    }

    public Action(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewDestination() {
        return newDestination;
    }

    public void setNewDestination(String newDestination) {
        this.newDestination = newDestination;
    }
}
