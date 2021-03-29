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
    CommandLineRunner initial() {
        return args -> {
            System.out.println("Running...");
        };
    }
}
