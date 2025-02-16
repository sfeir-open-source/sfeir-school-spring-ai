package com.example.application.service;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class LocalMemory implements ChatMemory {

    private static final Map<String, List<Message>> inMemoryMessages = new ConcurrentHashMap<>();

    @Override
    public void add(String conversationId, List<Message> messages) {
        inMemoryMessages.compute(conversationId, (id, existingMessages) -> {
            if (existingMessages == null) {
                return new CopyOnWriteArrayList<>(messages);
            }
            existingMessages.addAll(messages);
            return existingMessages;
        });
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return inMemoryMessages.getOrDefault(conversationId, new CopyOnWriteArrayList<>());
    }

    @Override
    public void clear(String conversationId) {
        inMemoryMessages.clear();
    }
}
