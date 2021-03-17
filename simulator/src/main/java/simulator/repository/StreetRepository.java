package simulator.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import simulator.model.network.Intersection;
import simulator.model.network.Street;

import java.util.Optional;

@EnableNeo4jRepositories
public interface StreetRepository extends Neo4jRepository<Street, Long> {
    Optional<Street> findByName(String name);

    @Query("MATCH (a:Intersection), (b:Intersection)" +
            "WHERE a.name = $0 AND b.name = $1 " +
            "CREATE (a)-[:STREET { name: $2, length : $3, maxSpeed : $4 } ]->(b)")
    void createStreet(String source, String name, String target, int length, int maxSpeed);
}
