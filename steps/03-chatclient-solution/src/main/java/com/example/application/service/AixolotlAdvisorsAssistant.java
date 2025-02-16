package com.example.application.service;

import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class AixolotlAdvisorsAssistant implements ConverseWithAssistant {

  private final ChatClient chatClient;

  private final ChatMemory chatMemory;

  @Value("${sensitiveWords}")
  private List<String> sensitiveWords;

  public AixolotlAdvisorsAssistant(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    this.chatClient = chatClientBuilder.build();
    this.chatMemory = chatMemory;
  }

  @Override
    public Flux<String> converse(String prompt) {
        return chatClient.prompt()
          .advisors(
            new MessageChatMemoryAdvisor(chatMemory),
            new SafeGuardAdvisor(sensitiveWords))
          .stream().content();
    }
}
