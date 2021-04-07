package work_simulator.model;

public class Action {
    private Long id;
    private String type;
    private String newDestination;

    public Action() {
    }

    public Action(Long id, String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
