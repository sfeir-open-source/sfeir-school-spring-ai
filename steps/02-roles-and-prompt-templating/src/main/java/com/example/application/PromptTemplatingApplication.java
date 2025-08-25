package com.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
public class PromptTemplatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptTemplatingApplication.class, args);
    }
}
