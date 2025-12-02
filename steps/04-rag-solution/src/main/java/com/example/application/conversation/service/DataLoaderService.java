package com.example.application.conversation.service;

import com.example.application.conversation.persistence.RagDocument;
import com.example.application.conversation.persistence.RagDocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DataLoaderService {

  @Value("classpath:/rag/*")
  private Resource[] resources;

  private static final Map<String, String> METADATA_DOCUMENTS = Map.of(
    "contact.json", "contact",
    "dispositif_participation.pdf", "advantage",
    "formations.txt", "adminRh",
    "materiel.txt", "adminRh",
    "ticket_restaurant.txt", "advantage",
    "zenride.txt", "advantage"
  );

  private final VectorStore vectorStore;

  private final RagDocumentRepository ragDocumentRepository;

  public DataLoaderService(VectorStore vectorStore, RagDocumentRepository ragDocumentRepository) {
    this.vectorStore = vectorStore;
    this.ragDocumentRepository = ragDocumentRepository;
  }

  public void load() {

    TextSplitter textSplitter = new TokenTextSplitter();

    getDocuments().forEach(doc -> {
      if(ragDocumentRepository.findByTitle(doc.getFilename()) == null) {

        DocumentReader reader = getReaderForResource(doc);
        List<Document> documents = reader.get();
        List<Document> splitDocuments = textSplitter.apply(documents);
        splitDocuments.forEach(splitDocument -> splitDocument.getMetadata().put("category", METADATA_DOCUMENTS.get(doc.getFilename())));
        vectorStore.write(splitDocuments);

        ragDocumentRepository.save(new RagDocument(doc.getFilename()));

      }
    });
  }

  private DocumentReader getReaderForResource(Resource resource) {
    String filename = resource.getFilename();
    if (filename == null) {
      throw new IllegalArgumentException("The resource has no filename");
    }

    if (filename.toLowerCase().endsWith(".pdf")) {
      return new PagePdfDocumentReader(resource);
    } else if (filename.toLowerCase().endsWith(".txt")) {
      return new TextReader(resource);
    } else if (filename.toLowerCase().endsWith(".json")){
      return new JsonReader(resource);
    }
    // Ajouter l'extension que vous voulez
    else {
      throw new UnsupportedOperationException("Unsupported file type: " + filename);
    }
  }

  private List<Resource> getDocuments(){
    return Arrays.asList(resources);
  }

}
