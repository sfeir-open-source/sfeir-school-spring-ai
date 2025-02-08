package com.example.application.conversation;

import reactor.core.publisher.Flux;

@FunctionalInterface
public interface AiModelProvider {
    Flux<String> chat(final String prompt);
}
