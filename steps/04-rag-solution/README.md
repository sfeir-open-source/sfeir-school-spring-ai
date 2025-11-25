# Solution Lab 04 - Retrieve augmented generation (RAG) - Apprenons à notre AIxolotl 📑


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
        splitDocuments.forEach(splitDocument -> splitDocument.getMetadata().put("category", "adminrh"));
        vectorStore.write(splitDocuments);

        ragDocumentRepository.save(new RagDocument(doc.getFilename()));

      }
    });
```

## `Advisor` API

### `QuestionAnswerAdvisor`

Implémenter la partie `query`
