package common;

public class Action {
    private Long agentId;
    private String type;
    private String newDestination;

    public Action() {
    }

    public Action(Long id, String type) {
        this.type = type;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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
