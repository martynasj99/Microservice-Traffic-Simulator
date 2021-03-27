package management;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Map;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
            SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner initial() {
        return args -> {

            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();
            InputStream in = getClass().getResourceAsStream("/config.json");
            Map<?, ?> object  = mapper.readValue(in, Map.class);

            HttpEntity<Map<?, ?>> body = new HttpEntity<>(object, headers);
            template.postForEntity("http://localhost:8081/setup", body, Void.class);
        };
    }

}
