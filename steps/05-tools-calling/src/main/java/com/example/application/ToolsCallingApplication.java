package com.example.application;

import com.example.application.conversation.service.DataLoaderService;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
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
@Theme(value = "labs")
public class ToolsCallingApplication implements AppShellConfigurator, CommandLineRunner {

  private final DataLoaderService dataLoaderService;

  public ToolsCallingApplication(DataLoaderService dataLoaderService) {
    this.dataLoaderService = dataLoaderService;
  }

  public static void main(String[] args) {
    SpringApplication.run(ToolsCallingApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    dataLoaderService.load();
  }
}
