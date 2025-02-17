package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class ConverseWithRawAssistant implements ConverseWithAssistant {

  private final ChatModel chatModel;

  public Flux<String> converse(final String prompt) {
    return chatModel.stream(prompt);
  }
}
