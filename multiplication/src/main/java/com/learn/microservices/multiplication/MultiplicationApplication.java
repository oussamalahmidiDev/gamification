package com.learn.microservices.multiplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiplicationApplication.class, args);
    }

}
