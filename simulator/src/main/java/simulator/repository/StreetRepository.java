package simulator.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import simulator.model.network.Street;

@EnableNeo4jRepositories
public interface StreetRepository extends Neo4jRepository<Street, Long> {
}
