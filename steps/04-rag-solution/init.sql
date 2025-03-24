CREATE TABLE IF NOT EXISTS rag_document
(
  id    uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  title text
);
