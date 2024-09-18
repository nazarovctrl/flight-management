package uz.ccrew.flightmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication
public class FlightManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightManagementApplication.class, args);
    }

}
