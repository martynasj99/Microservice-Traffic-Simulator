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

    public void sendConfiguration(String file, String url) throws IOException{
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        InputStream in = getClass().getResourceAsStream(file);
        Map<?, ?> object  = mapper.readValue(in, Map.class);

        HttpEntity<Map<?, ?>> body = new HttpEntity<>(object, headers);
        template.postForEntity(url, body, Void.class);
    }
}
