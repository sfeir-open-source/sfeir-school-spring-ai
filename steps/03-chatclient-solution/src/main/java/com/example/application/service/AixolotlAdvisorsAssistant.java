package com.example.application.service;

import com.example.application.conversation.ConverseWithAssistant;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import reactor.core.publisher.Flux;


public class AixolotlAdvisorsAssistant implements ConverseWithAssistant {

  private final ChatClient chatClient;

  public AixolotlAdvisorsAssistant(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
    public Flux<String> converse(String prompt) {
        return chatClient.prompt().stream().content();
    }
}
