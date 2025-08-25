package com.example.application;

import com.example.application.conversation.service.DataLoaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
public class RagSolutionApplication implements CommandLineRunner {

  private final DataLoaderService dataLoaderService;

  public RagSolutionApplication(DataLoaderService dataLoaderService) {
    this.dataLoaderService = dataLoaderService;
  }

  public static void main(String[] args) {
    SpringApplication.run(RagSolutionApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    dataLoaderService.load();
  }
}
