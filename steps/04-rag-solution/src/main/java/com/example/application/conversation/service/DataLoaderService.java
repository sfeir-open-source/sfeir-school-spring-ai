package com.example.application.conversation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataLoaderService {

  @Value("classpath:/rag/ticket_restaurant.txt")
  private Resource ticketRestaurant;

  private final VectorStore vectorStore;

  public DataLoaderService(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  public void load() {

    List<Document> documents = new TextReader(ticketRestaurant).get();
    TextSplitter textSplitter = new TokenTextSplitter(10,
      5,
      1,
      500,
      true);
    List<Document> splitDocuments = textSplitter.apply(documents);
    vectorStore.write(splitDocuments);

  }

}
