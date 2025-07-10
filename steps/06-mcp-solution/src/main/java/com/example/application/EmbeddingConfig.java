package com.example.application;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfig {

  @Bean
  public EmbeddingModel embeddingModel() {
    return new OpenAiEmbeddingModel(OpenAiApi.builder()
      .baseUrl("http://localhost:11434")
      .apiKey("")
      .build(),
      MetadataMode.EMBED,
      OpenAiEmbeddingOptions.builder().model("nomic-embed-text").build());
  }
}
