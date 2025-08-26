package com.example.application.conversation;


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
public class McpApplication implements CommandLineRunner {

  private final DataLoaderService dataLoaderService;

  Logger logger = LoggerFactory.getLogger(McpApplication.class);


  public McpApplication(DataLoaderService dataLoaderService) {
    this.dataLoaderService = dataLoaderService;
  }

  public static void main(String[] args) {
    SpringApplication.run(McpApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Loading data...");
    dataLoaderService.load();

  }
}
