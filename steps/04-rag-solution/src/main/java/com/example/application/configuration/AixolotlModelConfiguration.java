package com.example.application.configuration;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.service.AixolotlAdvisorsAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AixolotlModelConfiguration {

    @Bean
    public ConverseWithAssistant converseWithAixolotl(final ChatClient.Builder chatClientBuilder) {
        return new AixolotlAdvisorsAssistant(chatClientBuilder);
    }
}
