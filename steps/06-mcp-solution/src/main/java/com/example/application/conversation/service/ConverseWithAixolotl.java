package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.tool.EmailSenderTool;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.util.*;

import static java.util.Map.*;

import org.springframework.ai.tool.ToolCallbackProvider;

@Slf4j
@Service
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private static final int MAX_RESULTS = 3;

  private final ChatClient chatClient;

  public ConverseWithAixolotl(ChatModel chatModel,
                              ChatMemory chatMemory,
                              VectorStore vectorStore,
                              EmailSenderTool emailService,
                              ToolCallbackProvider tools,
                              @Value("${sensitiveWords}")  List<String> sensitiveWords) {

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

    this.chatClient = ChatClient.builder(chatModel)
      .defaultSystem(buildSystemPrompt())
      .defaultTools(emailService)
      .defaultToolCallbacks(tools)
      .defaultAdvisors(
        MessageChatMemoryAdvisor.builder(chatMemory)
          .conversationId(UUID.randomUUID().toString())
        .build(),
        new QuestionAnswerAdvisor(vectorStore),
        new SafeGuardAdvisor(sensitiveWords)
      )
      .build();

  }

  @Override
  public Flux<String> converse(final String prompt) {

    String content = this.chatClient
      .prompt()
      .user(prompt)
      .call().content();

    return Flux.just(content);
  }


  private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate("""
                  You are a blue smart Axolotl chatbot. Your name is {name}.
                  The company you're representing is called {company}. It's a French company composed of developers.
                  You assist employees in managing agendas with tools, answering questions, and providing information.
                  
                  You also have memory of your conversations.
                  Be concise in your responses but give meaningful information.
                  When you're greeting, be very concise by asking how you can help.

                  For tools requiring parameters, ensure to ask the user for the necessary information before
                  any call to the tool. Read the tool description carefully.

                  Be autonomous about time calculations, you can use the current date and time then calculate
                  from it to find the new month date, next week. For example, if the user talk about tomorrow,
                  you can use the current date and time to find the date of tomorrow.
            """);
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
}
