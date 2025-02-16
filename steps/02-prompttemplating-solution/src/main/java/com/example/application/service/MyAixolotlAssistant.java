package com.example.application.service;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.model.Aixolotl;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class MyAixolotlAssistant implements ConverseWithAssistant {


    private final ChatModel chatModel;

    @Value("classpath:system-message.txt")
    private Resource systemResource;

    private final MyAssistantProperties assistantProperties;

    public MyAixolotlAssistant(ChatModel chatModel, MyAssistantProperties assistantProperties) {
        this.chatModel = chatModel;
        this.assistantProperties = assistantProperties;
    }

//    @Override
//    public Flux<String> converse(String prompt) {
//        Aixolotl aixolotl = Aixolotl.builder().build();
//        SystemMessage systemMessage = new SystemMessage(aixolotl.getIdentityContext());
//        AssistantMessage assistantMessage = new AssistantMessage("Now, any response to a user message should have a southern belle personality and tone.");
//        UserMessage userMessage = new UserMessage(prompt);
//
//        return chatModel.stream(systemMessage, assistantMessage, userMessage);
//    }


    @Override
    public Flux<String> converse(String prompt) {
        Aixolotl aixolotl = Aixolotl.builder().build();
        SystemPromptTemplate promptTemplate = new SystemPromptTemplate(aixolotl.getIdentityContext());
        Message systemMessage = promptTemplate.createMessage(Map.of("name", assistantProperties.name(), "company", assistantProperties.company()));

        AssistantMessage assistantMessage = new AssistantMessage("");
        UserMessage userMessage = new UserMessage(prompt);

        return chatModel.stream(systemMessage, assistantMessage, userMessage);
    }

//    @Override
//    public Flux<String> converse(String prompt) {
//        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
//        return chatModel.stream(new UserMessage(prompt), systemPromptTemplate.createMessage()));
//    }

}
