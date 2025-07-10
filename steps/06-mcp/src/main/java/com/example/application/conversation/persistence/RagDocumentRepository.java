package com.example.application.conversation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RagDocumentRepository extends JpaRepository<RagDocument, Long> {

  RagDocument findByTitle(String title);
}
