package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

@Service
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private final UUID conversationId = UUID.randomUUID();
  private final ChatModel chatModel;
  private final ChatMemory aixolotlMemory;
  private final VectorStore vectorStore;
  private static final int MAX_RESULTS = 3;

  @Value("${sensitiveWords}")
  private List<String> sensitiveWords;

  public ConverseWithAixolotl(ChatModel chatModel, ChatMemory aixolotlMemory, VectorStore vectorStore) {
    this.chatModel = chatModel;
    this.aixolotlMemory = aixolotlMemory;
    this.vectorStore = vectorStore;
  }

  @Override
  public Flux<String> converse(final String prompt) {
    return ChatClient.builder(chatModel)
      .build()
      .prompt()
      .system(buildSystemPrompt())
      .user(prompt)
      .advisors(
        new MessageChatMemoryAdvisor(aixolotlMemory, conversationId.toString(), 50),
        new QuestionAnswerAdvisor(this.vectorStore, searchRequest), // RAG
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
