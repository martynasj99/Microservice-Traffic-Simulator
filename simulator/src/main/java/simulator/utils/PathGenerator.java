package simulator.utils;

import org.neo4j.driver.internal.InternalPath;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import simulator.repository.IntersectionRepository;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PathGenerator {

    public PathGenerator() {
    }

    /**
     * Generate a path from startNode to toNode.
     * @param startNode
     * @param toNode
     * @return A list consisting of the names of the nodes.
     */
    public List<String> generatePath(String startNode, String toNode, IntersectionRepository intersectionRepository){
        List<String> path = new ArrayList<>();
        Iterable<Map<String, Object>> results = intersectionRepository.findShortestPath(startNode, toNode);
        for(Map<String, Object> paths : results){
            InternalPath.SelfContainedSegment[] connections = (InternalPath.SelfContainedSegment[]) paths.get("p");
            path = new ArrayList<>();
            path.add(startNode);
            for(InternalPath.SelfContainedSegment connection : connections){
                path.add(connection.end().get("name").toString().replaceAll("^\"+|\"+$", ""));
            }
        }
        return path;

    }
}
