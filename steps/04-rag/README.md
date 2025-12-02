# Lab 04 - Retrieve augmented generation (RAG) - Apprenons à notre AIxolotl 📑
Maintenant qu'AIxolotl se souvient de ses échanges, il ne lui manque plus 
qu'une chose pour devenir un véritable assistant d'onboarding. Il doit avoir accès à un
ensemble de documents auquel il n'a pas eu accès lors de son entrainement initial. 

La génération augmentée de récupération ou RAG (Retrieval Augmented Generation) va nous
permettre de compléter les données que contient le LLM avec une sélection de sources de
connaissances externes.

Ainsi dans ce 4ᵉ lab, nous allons apprendre à travailler avec une base de données
vectorielle, technologie indispensable à cette méthode. 

## Ce qu'il faut faire

Dans ce lab vous devrez :

### Initialiser la base de données vectorielle

- exécuter le docker-compose.yml fourni à la racine du projet.
Il vous permettra de récupérer une image docker pgvector, base de données vectorielle. Il vous
faudra lancer le script init.sql pour l'initialiser :

```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
                                            id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content text,
    metadata json,
    embedding vector(1024)
    );

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
```

💡 : *avec le starter `spring-ai-pgvector-store-spring-boot-starter` il n'est plus nécessaire de charger
le script sql, il est exécuté par défaut lorsque la propriété `spring.ai.vectorstore.pgvector.initialize-schema`
est mise à true*

- ajouter au pom.xml la dépendance suivante : 
```xml
    <!-- Vector Databases -->
    <dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-starter-vector-store-pgvector</artifactId>
    </dependency>
```

### Chargement des données
- Créer un service `DataLoaderService` qui aura pour objectif de charger un ensemble
de document au démarrage de l'application via l'implémentation de l'interface
`CommandLineRunner`

- Ce service nécessitera l'injection d'un vectoreStore (`PGVectorStore` dans notre cas) pour échanger avec la base de
donnée et de l'utilisation de la classe `TokenTextSplitter` pour découper les documents en morceaux (chunks) avant
leur intégration en base (https://docs.spring.io/spring-ai/reference/1.0/api/etl-pipeline.html#_tokentextsplitter)

  - Comment rajouter des metadonnées à notre document. Nous venons d'intégrer nos documents tels quel, mais pour permettre
  une meilleure recherche dans la suite de l'exercice, nous allons rajouter des métadonnées à nos documents. Et notamment
  la catégorie à laquelle ils sont rattachés :
  
    | Document                     | Catégorie |
    |------------------------------|-----------|
    | dispositif_participation.pdf | avantages   |
    | formations.txt               | adminRh   |
    | ticket_restaurant.txt        | avantages |
    | zenride.txt                  | avantages |
    | contact.json                 | contact   |


### RAG simple

Nos documents sont maintenant en base, il va falloir les rechercher. Nous allons donc entrer dans le vif du sujet
de la méthode RAG (https://docs.spring.io/spring-ai/reference/1.0/api/retrieval-augmented-generation.html)

1. Utilisez l'advisor `QuestionAnswerAdvisor`. 
2. Construire votre propre requête de recherche en base pour la rendre plus précise et efficace. Pour cela, il vous suffit de la construire 
via la classe `SearchRequest`. Vous pourrez jouer sur les différents paramètres LLM (top K, top P, temperature), ainsi que sur les metadatas de
vos documents par exemple.

Les techniques de RAG évoluent rapidement et les flows se complexifient. Afin de s'adpater
facilement au besoin, une architecture modulaire a vu le jour https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html#modules

### RAG modulaire

Dans cette partie, vous utiliserez l'advisor `RetrievalAugmentationAdvisor`. L'objectif est d'implémenter les différentes "briques" du pattern :

1. PRE-RETRIEVAL
2. RETRIEVAL
3. POST-RETRIEVAL

Appuyez-vous sur la documentation https://docs.spring.io/spring-ai/reference/1.0/api/retrieval-augmented-generation.html#_retrievalaugmentationadvisor. 
