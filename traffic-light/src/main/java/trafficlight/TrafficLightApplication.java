package trafficlight;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import trafficlight.service.TrafficLightService;

import java.io.InputStream;
import java.net.URI;

@SpringBootApplication
public class TrafficLightApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrafficLightApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(TrafficLightService service) {
        return args -> {
            JSONObject registration = new JSONObject();
            registration.put("name", "traffic-light");
            registration.put("uri", "http://localhost:8082/step");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate template = new RestTemplate();
            URI uri = new URI("http://localhost:9000/registry");
            HttpEntity<String> body = new HttpEntity<>(registration.toString(), headers);
            template.postForEntity(uri, body, String.class);
        };
    }
}
