package common;

public class VehicleLocation {
    private Long currentStreet;
    private String currentNode;
    private String nextNode;

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
}
