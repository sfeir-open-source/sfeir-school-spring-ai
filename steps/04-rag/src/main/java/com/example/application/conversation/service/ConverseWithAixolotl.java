package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.configuration.Aixolotl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private final ChatModel chatModel;

  public Flux<String> converse(final String prompt) {
    return chatModel.stream(
      new SystemMessage(buildSystemPrompt()),
      new UserMessage(prompt));
  }

  private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate(Aixolotl.getSystemPromptTemplate());
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
}

