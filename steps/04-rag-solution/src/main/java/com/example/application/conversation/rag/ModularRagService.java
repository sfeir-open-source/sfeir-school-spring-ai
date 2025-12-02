package com.example.application.conversation.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.generation.augmentation.QueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.expansion.QueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ModularRagService {

  private final VectorStore vectorStore;

  public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(ChatClient.Builder chatClientBuilder) {
    return RetrievalAugmentationAdvisor.builder()
                                       // PRE-RETRIEVAL
                                       .queryTransformers(RewriteQueryTransformer.builder()
                                                                                 .chatClientBuilder(chatClientBuilder.clone())
                                                                                 .build())
                                       //.queryExpander(queryExpander(chatClientBuilder.clone()))
                                       //.scheduler(Schedulers.boundedElastic())
                                       // RETRIEVAL
                                       .documentRetriever(documentRetriever())
                                       // POST-RETRIEVAL
                                       //.documentPostProcessors()
                                       //.queryAugmenter(queryAugmenter())
                                       .build();
  }

  private DocumentRetriever documentRetriever() {
    FilterExpressionBuilder b = new FilterExpressionBuilder();
    return VectorStoreDocumentRetriever.builder()
                                       .vectorStore(this.vectorStore)
                                       .filterExpression(
                                         b.eq("category", "adminrh")
                                          .build()
                                       )
                                       .build();
  }

  private QueryAugmenter queryAugmenter() {
    return ContextualQueryAugmenter.builder()
                                   .allowEmptyContext(true)
                                   .build();
  }

  private QueryExpander queryExpander(ChatClient.Builder chatClientBuilder) {
    return MultiQueryExpander.builder()
                             .chatClientBuilder(chatClientBuilder)
                             .numberOfQueries(3)
                             .build();
  }

}
