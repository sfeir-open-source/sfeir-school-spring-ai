package com.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
public class OllamaSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OllamaSolutionApplication.class, args);
    }
}
