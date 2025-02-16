package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConverseWithRawAssistant implements ConverseWithAssistant {

  private final ChatModel chatModel;

  public ConverseWithRawAssistant(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public Flux<String> converse(final String prompt) {
    return chatModel.stream(new UserMessage(prompt));
  }
}
