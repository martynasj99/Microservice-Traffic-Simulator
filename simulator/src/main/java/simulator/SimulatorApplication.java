package simulator;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import simulator.repository.IntersectionRepository;
import simulator.service.VehicleService;
import simulator.service.MapService;

import java.net.URI;

@EnableTransactionManagement
@SpringBootApplication
@EnableNeo4jRepositories
public class SimulatorApplication {

    private final static Logger log = LoggerFactory.getLogger(SimulatorApplication.class);

    @Autowired private MapService mapService;
    @Autowired private VehicleService vehicleService;

    public static void main(String[] args) {
            SpringApplication.run(SimulatorApplication.class, args);
    }

    @Bean
    CommandLineRunner initial(IntersectionRepository intersectionRepository) {
        return args -> {
            log.debug("---Starting---");
            intersectionRepository.deleteAll();

            JSONObject registration = new JSONObject();
            registration.put("name", "simulator");
            registration.put("uri", "http://localhost:8081/step");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate template = new RestTemplate();
            URI uri = new URI("http://localhost:9000/registry");
            HttpEntity<String> body = new HttpEntity<>(registration.toString(), headers);
            template.postForEntity(uri, body, String.class);

            log.debug("---End---");
        };
    }

}
