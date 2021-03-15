package simulator.model.view;

import simulator.model.network.Intersection;
import simulator.model.network.Street;

import java.util.List;

public class MapView {
    private Iterable<Intersection> nodes;
    private List<Street> links;

    public MapView() {
    }

    public Iterable<Intersection> getNodes() {
        return nodes;
    }

    public void setNodes(Iterable<Intersection> nodes) {
        this.nodes = nodes;
    }

    public List<Street> getLinks() {
        return links;
    }

    public void setLinks(List<Street> links) {
        this.links = links;
    }
}
