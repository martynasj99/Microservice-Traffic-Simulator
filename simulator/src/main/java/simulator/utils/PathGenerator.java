package simulator.utils;

import org.neo4j.driver.internal.InternalPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        System.out.println("START");
        List<String> path = new ArrayList<>();
        Iterable<Map<String, Object>> results = intersectionRepository.findShortestPath(startNode, toNode);
        for(Map<String, Object> paths : results){
            System.out.println("PATH");
            InternalPath.SelfContainedSegment[] connections = (InternalPath.SelfContainedSegment[]) paths.get("p");
            path = new ArrayList<>();
            path.add(startNode);
            for(InternalPath.SelfContainedSegment connection : connections){
                path.add(connection.end().get("name").toString().replaceAll("^\"+|\"+$", ""));
            }
            System.out.println(path);
        }
        return path;
    }
}
