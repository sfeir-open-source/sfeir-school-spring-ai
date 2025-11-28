package com.example.application;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

@Configuration
public class ObservabilityConfig {
  @PostConstruct
  public void init() {
    // C'est LA clé magique pour WebFlux + Zipkin
    Hooks.enableAutomaticContextPropagation();
  }
}
