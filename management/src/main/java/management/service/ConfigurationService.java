package management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class ConfigurationService {

    public void sendSimulatorConfiguration() throws IOException {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        InputStream in = getClass().getResourceAsStream("/config.json");
        Map<?, ?> object  = mapper.readValue(in, Map.class);

        HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
        template.postForEntity("http://localhost:8081/setup", body, Void.class);

    }
}
