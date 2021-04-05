package management;

import management.service.ConfigurationService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
            SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner initial(@Autowired ConfigurationService configurationService) {
        return args -> {
            JSONObject registration = new JSONObject();
            registration.put("name", "management");
            registration.put("uri", "http://localhost:8083/step");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate template = new RestTemplate();
            URI uri = new URI("http://localhost:9000/registry");
            HttpEntity<String> body = new HttpEntity<>(registration.toString(), headers);
            template.postForEntity(uri, body, String.class);

            configurationService.sendConfiguration("/config.json", "http://localhost:8081/setup");
            configurationService.sendConfiguration("/home.json", "http://localhost:8084/setup");
            //configurationService.sendConfiguration("/drivers.json", "http://localhost:9001/setup");
        };
    }

}
