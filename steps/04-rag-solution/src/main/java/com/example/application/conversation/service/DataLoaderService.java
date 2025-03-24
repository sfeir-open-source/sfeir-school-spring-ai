package com.example.application.conversation.service;

import com.example.application.conversation.persistence.RagDocument;
import com.example.application.conversation.persistence.RagDocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DataLoaderService {

  @Value("classpath:/rag/*")
  private Resource[] resources;

  private final VectorStore vectorStore;

  private final RagDocumentRepository ragDocumentRepository;

  public DataLoaderService(VectorStore vectorStore, RagDocumentRepository ragDocumentRepository) {
    this.vectorStore = vectorStore;
    this.ragDocumentRepository = ragDocumentRepository;
  }

  public void load() {

    TextSplitter textSplitter = new TokenTextSplitter(10,
      5,
      1,
      500,
      true);

    getDocuments().forEach(doc -> {
      if(ragDocumentRepository.findByTitle(doc.getFilename()) == null) {

        List<Document> documents = new TextReader(doc).get();
        List<Document> splitDocuments = textSplitter.apply(documents);
        splitDocuments.forEach(splitDocument -> splitDocument.getMetadata().put("category", "adminrh"));
        vectorStore.write(splitDocuments);

        ragDocumentRepository.save(new RagDocument(doc.getFilename()));

      }
    });

  }

  private List<Resource> getDocuments(){
    return Arrays.asList(resources);
  }

}
