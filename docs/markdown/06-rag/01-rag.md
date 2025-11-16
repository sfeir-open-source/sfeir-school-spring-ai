

# Retrieval Augmented Generation (RAG)

## Processus de RAG


L'architecture RAG est la combinaison d'un modèle d'embedding, d'une base de données vectorielle et d'un LLM.
<br></br>

![](./assets/images/rag_schema.svg 'w-1000 center')

Notes:
L'intérêt principal du RAG est de rendre les grands modèles plus fiables, plus précis et plus pertinents en leur donnant accès à une connaissance
externe et contrôlée. Plutôt que de se baser uniquement sur les données de leur entraînement initial

##==##
# Retrieval Augmented Generation (RAG)

## Ajout des documents externes en base (Ingestion)

<br></br>
![](./assets/images/ingestion_rag.svg 'w-800 center')
<br></br>
![](./assets/images/ingestion_pipeline.svg 'w-800 center')

##==##


# Retrieval Augmented Generation (RAG)

## Génération

<img class="center" src="./assets/images/flow_rag.png" style="width: 80vw">

![](./assets/images/cosinus_similarity.svg 'float-right')

##==##

# Retrieval Augmented Generation (RAG)

## Implémentation spring AI

#### Ingestion

Le framework d'Extraction, Transformation et Chargement (ETL) sert de colonne vertébrale au traitement des données dans le cas d'utilisation de RAG.

Il y a 3 composants principaux dans un ETL :

* `DocumentReader` implémente `Supplier<List<Document>>`
* `DocumentTransformer` implémente `Function<List<Document>, List<Document>>`
* `DocumentWriter` that implémente `Consumer<List<Document>>`

```java
    List<Document> documents = new TextReader(doc).get();  // Document Reader
    TextSplitter textSplitter = new TokenTextSplitter();
    List<Document> splitDocuments = textSplitter.apply(documents);  // Document Transformer
    vectorStore.write(splitDocuments); // Document Writer
```
<!-- .element: class="admonition example" -->


##==##

# Retrieval Augmented Generation (RAG)

## Implémentation spring AI

#### Query

Spring AI met à disposition un support prêt à l'emploi pour les flux RAG courants en utilisant l'API Advisor. Les deux principaux sont :


* QuestionAnwserAdvisor : Le plus simple, prêt à l'emploi.
* RetrievalAugmentationAdvisor : Permet d'implémenter des flux RAG complexe basés sur l'[architecture modulaire](https://arxiv.org/abs/2407.21059)

![](./assets/images/modular_rag.svg 'w-1000 center')


Il suffit de l'ajouter au chatClient de la manière suivante :

```java
ChatResponse response = ChatClient.builder(chatModel)
        .build().prompt()
        .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .user(userText)
        .call()
        .chatResponse();
```

Notes:

The QuestionAnswerAdvisor uses a default template to augment the user question with the retrieved documents.
You can customize this behavior by providing your own PromptTemplate object via the .promptTemplate() builder method.

Pre-Retrieval modules are responsible for processing the user query to achieve the best possible retrieval results.
QueryTransformer RewriteQueryTransformer

Retrieval VectorStoreDocumentRetriever

