package com.example.application.prompttemplating;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:assistant.properties")
public record MyAssistantProperties(@Value("${company}") String company, @Value("${name}") String name) {
}
