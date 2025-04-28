package tn.esprit.msbanque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "tn.esprit.msbanque.client")
public class MsBanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBanqueApplication.class, args);
    }

}
