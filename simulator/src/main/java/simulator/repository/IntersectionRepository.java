package simulator.repository;

import org.neo4j.cypher.internal.expressions.In;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import simulator.model.network.Intersection;

import javax.sound.midi.Soundbank;
import java.util.Map;
import java.util.Optional;

@EnableNeo4jRepositories
public interface IntersectionRepository extends Neo4jRepository<Intersection, Long> {
    Optional<Intersection> findByName(String name);
    Iterable<Intersection> findByType(String type);

    @Query("MATCH (start:Intersection{name:$0}), (end:Intersection{name:$1}) ," +
            "p = shortestPath((start)-[*]->(end)) " +
            "RETURN p")
    Iterable<Map<String, Object>> findShortestPath(String n1, String n2);

    @Query("CREATE (:Intersection { name: $0, type: $1 })")
    void createNode(String name, String type);



}
