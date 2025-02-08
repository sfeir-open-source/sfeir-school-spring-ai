package com.example.application.conversation.http;

import com.example.application.conversation.ConverseWithAssistant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ConverseWithAixolotlHttpApi {

    private final ConverseWithAssistant converseWithAssistant;

    public ConverseWithAixolotlHttpApi(final ConverseWithAssistant converseWithAssistant) {
        this.converseWithAssistant = converseWithAssistant;
    }


    @GetMapping(value = "/conversation/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> streamResponse(@RequestParam(value = "userMessage") final String userMessage) {
        return converseWithAssistant.converse(userMessage);
    }
}
