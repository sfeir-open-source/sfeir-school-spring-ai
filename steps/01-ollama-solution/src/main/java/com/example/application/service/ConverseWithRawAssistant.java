package com.example.application.service;

import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import reactor.core.publisher.Flux;


public class ConverseWithRawAssistant implements ConverseWithAssistant {

  private final ChatModel chatModel;

  public ConverseWithRawAssistant(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public Flux<String> converse(final String prompt) {

    String ollamaUrl = "http://127.0.0.1:11434";

    final var ollamaApi = new OllamaApi(ollamaUrl);

    // Construction d'un chatModel Ollama
    OllamaChatModel ollamaChatModel = OllamaChatModel.builder()
      .ollamaApi(ollamaApi)
      .defaultOptions(OllamaOptions.builder()
        .model("llama3.2")
        .temperature(0.3)
        .topK(10)
        .topP(0.2)
        .build())
      .build();

    // Sync request
    return chatModel.stream(new UserMessage(prompt));
  }
}
