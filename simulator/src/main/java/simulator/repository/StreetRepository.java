package simulator.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import simulator.model.network.Street;

@EnableNeo4jRepositories
public interface StreetRepository extends Neo4jRepository<Street, Long> {
    @Query("MATCH (a:Intersection), (b:Intersection)" +
            "WHERE a.name = $0 AND b.name = $1 " +
            "CREATE (a)-[:STREET { length : $2, maxSpeed : $3 } ]->(b)")
    void createStreet(String source, String target, int length, int maxSpeed);
}
