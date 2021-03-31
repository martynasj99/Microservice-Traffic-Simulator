package driversweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
