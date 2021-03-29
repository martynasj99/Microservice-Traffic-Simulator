package management;

import management.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
            SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner initial(@Autowired ConfigurationService configurationService) {
        return args -> {
            configurationService.sendConfiguration("/config.json", "http://localhost:8081/setup");
            configurationService.sendConfiguration("/home.json", "http://localhost:8084/setup");
            configurationService.sendConfiguration("/drivers.json", "http://localhost:9001/setup");
        };
    }

}
