package com.example.application.conversation;

import reactor.core.publisher.Flux;

public class ConverseWithAixolotl implements ConverseWithAssistant{
    private final AiModelProvider model;

    public ConverseWithAixolotl(final AiModelProvider model) {
        this.model = model;
    }

    public Flux<String> converse(final String prompt) {
        return model.chat(prompt);
    }
}
