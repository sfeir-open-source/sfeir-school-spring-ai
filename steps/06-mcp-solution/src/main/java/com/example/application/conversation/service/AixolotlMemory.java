package com.example.application.conversation.service;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AixolotlMemory implements ChatMemory {

  private static final int MAX_MESSAGES = 100;
  private static final Map<String, List<Message>> inMemoryMessages = new ConcurrentHashMap<>();

  @Override
  public void add(String conversationId, List<Message> messages) {
    inMemoryMessages.computeIfAbsent(conversationId, id -> new ArrayList<>()).addAll(messages);
  }

  @Override
  public List<Message> get(String conversationId) {
    List<Message> messages = inMemoryMessages.getOrDefault(conversationId, new ArrayList<>());
    return  messages
      .stream()
      .skip(Math.max(0, messages.size() - MAX_MESSAGES))
      .toList();
  }

  @Override
  public void clear(String conversationId) {
    inMemoryMessages.clear();
  }
}
