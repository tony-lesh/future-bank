package com.example.futurebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class FutureBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureBankApplication.class, args);
    }

}
