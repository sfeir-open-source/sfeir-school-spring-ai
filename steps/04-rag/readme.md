# Lab 04 - Retrieve augmented generation (RAG) - Apprenons à notre AIxolotl :bookmark_tabs:
Maintenant qu'AIxolotl se souvient de ses échanges, il ne lui manque plus 
qu'une chose pour devenir un véritable assistant d'onboarding. Il doit avoir accès à un
ensemble de documents auquel il n'a pas eu accès lors de son entrainement initial. 

La génération augmentée de récupération ou RAG (Retrieval Augmented Generation) va nous
permettre de compléter les données que contient le LLM avec une sélection de sources de
connaissances externes.

Ainsi dans ce 4ème lab, nous allons apprendre à travailler avec une base de données
vectorielle, technologie indispensable à cette méthode. 

## Ce qu'il faut faire

Dans ce lab vous devrez :

- exécuter le docker-compose.yml fourni à la racine du projet
Il vous permettra de récupérer une image docker pgvector, base de données vectorielle. Il vous
faudra lancer le script init.sql pour initialiser la bdd :
![image](/assets/init-sql.png)

:bulb: : *le starter `spring-ai-pgvector-store-spring-boot-starter` nous permettra de ne pas avoir
à exécuter le init.sql*

- ajouter au pom.xml la dépendance suivante : 
```xml
    <!-- Vector Databases -->
    <dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
    </dependency>
```

- Créer un service `DataLoaderService` qui aura pour objectif de charger un ensemble
de document au démarrage de l'application via l'implémentation de l'interface
`CommandLineRunner`
  https://docs.spring.io/spring-ai/reference/1.0/api/retrieval-augmented-generation.html
