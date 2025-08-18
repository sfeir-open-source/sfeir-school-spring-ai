package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.configuration.Aixolotl;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

@Service
@AllArgsConstructor
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private final UUID conversationId = UUID.randomUUID();
  private final ChatModel chatModel;
  private final ChatMemory aixolotlMemory;

  @Value("${sensitiveWords}")
  private final List<String> sensitiveWords;

  @Override
  public Flux<String> converse(final String prompt) {
    return ChatClient.builder(chatModel)
      .build()
      .prompt()
      .system(buildSystemPrompt())
      .user(prompt)
      .advisors(
        MessageChatMemoryAdvisor.builder(aixolotlMemory).conversationId(conversationId.toString()).build(),
        new SafeGuardAdvisor(sensitiveWords)
      )
      .stream()
      .content()
      ;
  }

  private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate(Aixolotl.getSystemPromptTemplate());
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
}
