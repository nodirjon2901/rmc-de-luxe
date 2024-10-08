package uz.result.rmcdeluxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RmcDeLuxeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmcDeLuxeApplication.class, args);
    }

}
