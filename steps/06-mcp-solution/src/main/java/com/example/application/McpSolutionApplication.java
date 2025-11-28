package com.example.application;


import com.example.application.conversation.service.DataLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class McpSolutionApplication implements CommandLineRunner {

  private final DataLoaderService dataLoaderService;

  Logger logger = LoggerFactory.getLogger(McpSolutionApplication.class);


  public McpSolutionApplication(DataLoaderService dataLoaderService) {
    this.dataLoaderService = dataLoaderService;
  }

  public static void main(String[] args) {
    SpringApplication.run(McpSolutionApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");
    dataLoaderService.load();
    logger.info("Loading complete...");
  }
}
