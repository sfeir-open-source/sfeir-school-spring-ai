package com.example.application.configuration;

import com.example.application.service.ConverseWithRawAssistant;
import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AixolotlModelConfiguration {

    @Bean
    public ConverseWithAssistant converseWithAixolotl(final ChatModel model) {
        return new ConverseWithRawAssistant(model);
    }
}
