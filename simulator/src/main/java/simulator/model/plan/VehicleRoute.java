package simulator.model.plan;

import java.util.List;

public class VehicleRoute {

    private String startNode;
    private String endNode;
    private List<String> path;

    public VehicleRoute() {
    }

    public VehicleRoute(String startNode, String endNode, List<String> path) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.path = path;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }
}
