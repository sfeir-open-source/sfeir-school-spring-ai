package com.example.application.conversation.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.tool.EmailSenderTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

@Slf4j
@Service
public class ConverseWithAixolotl implements ConverseWithAssistant {

  private final ChatClient chatClient;

  public ConverseWithAixolotl(ChatModel chatModel,
                              ChatMemory chatMemory,
                              VectorStore vectorStore,
                              EmailSenderTool emailService,
                              ToolCallbackProvider tools,
                              @Value("${sensitiveWords}")  List<String> sensitiveWords) {

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
