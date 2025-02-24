package com.example.application.conversation;

import reactor.core.publisher.Flux;

public interface ConverseWithAssistant {

    default Flux<String> converse(final String prompt){
      return Flux.just(prompt);
    }
}
