package com.example.application.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AixolotlMemory implements ChatMemory {

  final Map<String, List<Message>> redisStore = new ConcurrentHashMap<>();

  @Override
  public void add(String conversationId, List<Message> messages) {
    redisStore.computeIfAbsent(conversationId, id -> new ArrayList<Message>()).addAll(messages);
  }

  @Override
  public List<Message> get(String conversationId) {
    List<Message> messages = redisStore.getOrDefault(conversationId, new ArrayList<>());
    return messages
      .stream()
      .skip(Math.max(0, messages.size()) - 50)
      .toList();
  }

  @Override
  public void clear(String conversationId) {
    redisStore.get(conversationId).clear();
  }
}
