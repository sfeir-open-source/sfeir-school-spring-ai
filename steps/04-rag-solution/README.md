# Solution Lab 04 - Retrievel-augmented generation (RAG) - Apprenons à notre AIxolotl 📑


## Ingestion des documents externes

### Appel du service de chargement des données

Avant de poser des questions spécifiques au chatbot, il nous faut mettre à disposition les documents 
dans une base de données vectorielle. Nous choisissons de le faire au démarrage de l'application.

Le service de chargement est donc appelé via la méthode `run()` de l'interface `CommandLineRunner` :

```java
@SpringBootApplication
public class RagSolutionApplication implements CommandLineRunner {

  private final DataLoaderService dataLoaderService;

  public RagSolutionApplication(DataLoaderService dataLoaderService) {
    this.dataLoaderService = dataLoaderService;
  }

  public static void main(String[] args) {
    SpringApplication.run(RagSolutionApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    dataLoaderService.load();
  }
}
```

### Le service `DataLoaderService`

Attaquons-nous au cœur du sujet : le service d'ingestion.
Les documents sont mis à disposition ici : `src/main/resources/rag`.
Il nous suffit d'y faire référence pour récupérer l'ensemble de nos documents à traiter : 

```java
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
  
```

Que faire ensuite ? Souvenez-vous l'ingestion se décompose en 3 étapes : 
- Document reader
- Document transformer
- Document writer

#### Document reader
Vous remarquerez que l'on a différent format de fichiers à traiter. Pour cela spring nous met à 
disposition un ensemble d'implémentation de l'interface `DocumentReader`. Voici un exemple de traitement possible :

```java
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
```
Et l'appel de cette méthode : 

```java
        DocumentReader reader = getReaderForResource(doc);
        List<Document> documents = reader.get();
```

#### Document transformer
Maintenant que l'on a notre liste de document, nous pouvons les découper en morceaux (chunk) dans l'objectif dans les transformer en vecteur :

```java
    TextSplitter textSplitter = new TokenTextSplitter();
    DocumentReader reader = getReaderForResource(doc);
    List<Document> documents = reader.get();
    List<Document> splitDocuments = textSplitter.apply(documents);
```

#### Document writer
Enfin, nous n'avons plus qu'à charger nos "chunks" en base. Pour cela, nous utilisons le service `PgVectorStore` (implémente l'interface 
`DocumentWriter`) disponible avec notre dépendance `spring-ai-starter-vector-store-pgvector` :

```java
private final VectorStore vectorStore;

public DataLoaderService(VectorStore vectorStore, RagDocumentRepository ragDocumentRepository) {
  this.vectorStore = vectorStore;
  this.ragDocumentRepository = ragDocumentRepository;
}

public void load() {
  ...
  vectorStore.write(splitDocuments);
}
```

#### Chargement à chaque démarrage
De cette manière, à chaque démarrage de notre application nous chargerons l'ensemble des documents en base. Pour éviter cela,
nous ajoutons une table de suivi des documents déjà chargés en base :

```java
@Entity
@Data
@NoArgsConstructor
public class RagDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  public RagDocument(String title) {
    this.title = title;
  }

}
```

Il n'y a plus qu'à s'assurer que le document n'est pas déjà présent en base : 

```java
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
```

## `Advisor` API

Nous allons maintenant implémenter la partie `query`.

### `QuestionAnswerAdvisor`

La première possibilité consiste à utiliser `QuestionAnswerAdvisor` pour lequel il suffit de l'instancier dans
le `ChatClient` :

```java
  @Override
public Flux<String> converse(final String prompt) {
  return builder
    .build()
    .prompt()
    .system(buildSystemPrompt())
    .user(prompt)
    .advisors(
      MessageChatMemoryAdvisor.builder(aixolotlMemory)
                              .conversationId(conversationId.toString())
                              .build(),
      QuestionAnswerAdvisor.builder(vectorStore)
                           .searchRequest(searchRequest)   // requête custom
                           .build(),
      new SafeGuardAdvisor(sensitiveWords)
    )
    .stream()
    .content()
    ;
}
```

Il est possible de lui spécifier une requête de recherche par similarité :

```java
  SearchRequest searchRequest = SearchRequest
    .builder()
    .filterExpression(
      // préciser la recherche de document dans la catégorie adminrh
      new FilterExpressionBuilder().eq("category", "adminrh")
        .build()
    )
    .similarityThreshold(0.9)
    .topK(MAX_RESULTS)
    .build();
```
:information_source: N'hésitez pas à tester différente requête 

### `RetrievalAugmentationAdvisor`

Le RetrievalAugmentationAdivsor ne permet de d'implémentater une solution RAG plus avancée et modulaire. 
Pour cela nous allons créer un nouveau service `ModularRagService` :

```java
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
                                       // GENERATION
                                       //.queryAugmenter(queryAugmenter())
                                       .build();
  }
```
Le code ci-desssus propose plusieurs solutions de construction de notre Advisor. On distingue 3 étapes :

* Pre-retrieval
* Retrieval
* Post-retrieval

#### Pre-retrieval
Ici, nous utilisons le service `RewriteQueryTransformer` qui nous permet de reformuler la requête en fonction de la cible, par défault celle-ci est une base de 
données vectorielles. Le prompt par défault peut-être remplacer par un autre adapté à votre besoin :

```java
RewriteQueryTransformer.builder()
                       .chatClientBuilder(chatClientBuilder.clone())
                       .promptTemplate() // ICI pour spécifier son prompt
                       .targetSearchSystem() // ICI pour spécifier sa cible
                       .build();
```

:information_source: N'hésitez pas à tester la technique `MultiQueryExpander` qui change l'approche séquentielle en une multitude de requêtes à la base.

```java
  private QueryExpander queryExpander(ChatClient.Builder chatClientBuilder) {
    return MultiQueryExpander.builder()
                             .chatClientBuilder(chatClientBuilder)
                             .numberOfQueries(3)
                             .build();
  }
```

#### Retrieval
Cette étape concerne la récupération des documents en base. Etape déjà abordée avec l'advisor `QuestionAnswerAdvisor` :

```java
  private DocumentRetriever documentRetriever() {
    return VectorStoreDocumentRetriever.builder()
                                       .vectorStore(this.vectorStore)
                                       .filterExpression(
                                         new FilterExpressionBuilder().eq("category", "adminrh")
                                          .build()
                                       )
                                       .build();
  }
```

#### Post-Retrieval
Il est possible de réaliser un traitement sur les documents récupérés de la base. Par exemple, réduire le contexte en supprimant les informations les moins 
pertinentes récupérées de la base (problème du "[lost in the middle](https://medium.com/@abheshith7/mastering-the-lost-in-the-middle-problem-in-rag-e08482780b0f)").

```java
RetrievalAugmentationAdvisor.builder()
                            .documentPostProcessors() // passer votre service dans cette méthode 
                            .build();
```

Vous constatez également le composant `QueryAugmenter`. Un module destiné à enrichir la requête initiale par des données additionnelles, 
afin d'apporter au LLM le contexte requis pour répondre à l'utilisateur. Ici, on utilise l'implémentation `ContextualQueryAugmenter` :

```java
  private QueryAugmenter queryAugmenter() {
    return ContextualQueryAugmenter.builder()
                                   .allowEmptyContext(true)
                                   .build();
  }
```
