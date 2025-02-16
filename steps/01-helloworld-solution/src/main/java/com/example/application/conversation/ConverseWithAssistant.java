package com.example.application.conversation;

import reactor.core.publisher.Flux;

public interface ConverseWithAssistant {
   Flux<String> converse(final String prompt);
}
