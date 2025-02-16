package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "labs")
public class ChatClientSolutionApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(ChatClientSolutionApplication.class, args);
    }

  @Bean
  public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
  }

}
