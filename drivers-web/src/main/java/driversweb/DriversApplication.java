package driversweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import driversweb.model.DayPlan;
import driversweb.model.Driver;
import driversweb.service.DriverService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DriversApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriversApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(@Autowired DriverService driverService) {
        return args -> {
            System.out.println("Running...");
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8081/vehicles/count";
            String result = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            InputStream in = getClass().getResourceAsStream("/config.json");
            Configuration configuration = mapper.readValue(in, Configuration.class);


            Map<Long, DayPlan> plans = configuration.getPlans();
            System.out.println(plans);
            System.out.println(plans.values());
            if(result != null){
                long num = Long.parseLong(result);
                for (long i = 0; i < num; i++) {
                    JSONObject object = new JSONObject();
                    object.put("notificationUri", "http://localhost:9001/"+(i+1)+"/notifications");
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> body = new HttpEntity<>(object.toString(), headers);
                    Driver driver = new Driver();
                    driver.setPlan(plans.getOrDefault(i+1, null));
                    driverService.getDrivers().put(i+1, driver);
                    restTemplate.exchange("http://localhost:8081/vehicles/"+(i+1), HttpMethod.PUT, body, Void.class);
                }
            }
        };
    }
}
