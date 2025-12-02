package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.rag.ModularRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;

import static java.util.Map.*;

@RequiredArgsConstructor
@Service
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private final UUID conversationId = UUID.randomUUID();
  private final ChatModel chatModel;
  private final ChatMemory aixolotlMemory;
  private final ModularRagService modularRagService;
  private static final int MAX_RESULTS = 3;
  private final ChatClient.Builder builder;
  @Value("${sensitiveWords}")
  private List<String> sensitiveWords;


  @Override
  public Flux<String> converse(final String prompt) {

    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = modularRagService.retrievalAugmentationAdvisor(ChatClient.builder(chatModel));

    return builder
      .build()
      .prompt()
      .system(buildSystemPrompt())
      .user(prompt)
      .advisors(
        MessageChatMemoryAdvisor.builder(aixolotlMemory).conversationId(conversationId.toString()).build(),
        //QuestionAnswerAdvisor.builder(vectorStore).searchRequest(searchRequest).build(),// RAG
        retrievalAugmentationAdvisor, // MODULAR RAG
        new SafeGuardAdvisor(sensitiveWords)
      )
      .stream()
      .content()
      ;
  }


  FilterExpressionBuilder b = new FilterExpressionBuilder();

  SearchRequest searchRequest = SearchRequest
    .builder()
    .filterExpression(
      // préciser la recherche de document dans la catégorie adminrh
      b.eq("category", "adminrh")
        .build()
    )
    .similarityThreshold(0.9)
    .topK(MAX_RESULTS)
    .build();

  private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate("""
                  You are a blue smart Axolotl chatbot. Your name is {name}.
                  You also have memory of your conversations.
                  The company you're representing is called {company}.
                  It's a French company composed of developers.
                  Be concise in your responses but give meaningful information.
                  When you're greeting, be very concise by asking how you can help.
            """);
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
}
