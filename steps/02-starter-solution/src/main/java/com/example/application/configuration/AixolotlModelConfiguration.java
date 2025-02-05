package com.example.application.configuration;

import com.example.application.conversation.ConverseWithAssistant;
import com.example.application.conversation.model.Aixolotl;
import com.example.application.prompttemplating.MyAixolotlAssistant;
import com.example.application.prompttemplating.MyAssistantProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AixolotlModelConfiguration {

    @Bean
    protected Aixolotl aixolotl() {
        return Aixolotl.builder()
                .build();
    }

    @Bean
    public ConverseWithAssistant converseWithAixolotl(final ChatModel model, final MyAssistantProperties assistantProperties) {
        return new MyAixolotlAssistant(model, assistantProperties);
    }
}
