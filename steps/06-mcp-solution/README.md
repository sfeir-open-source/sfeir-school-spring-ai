# Solution du Lab 06 - Manipulation d'un agenda via Model Context Protocol 📅

Découverte de l'API MCP côté client et serveur avec Spring AI.

## Challenge

Notre Aixolotl veut gérer un agenda. Plutôt que de coder une intégration spécifique, on va utiliser MCP : un protocol commun entre clients et serveurs pour exposer/consommer des outils.

**Le principe** :
1. Le serveur Agenda (Gradle) expose des outils via MCP (`listAgenda`, `createAgendaItem`, `getCurrentDateTime`).
2. Le client AIxolotl (ce projet Maven) se connecte au serveur via SSE (Server-Sent Events).
3. Le serveur annonce ses outils au client (format JSON Schema).
4. Le LLM peut décider d'invoquer un outil : Spring AI fait transiter la requête vers le serveur MCP.
5. Le serveur exécute l'action et retourne le résultat.
6. Le résultat est réinjecté dans la conversation.

> 💡 Aucun code métier agenda n'est écrit côté client : tout passe par le protocole standardisé MCP.

## Ajout de la dépendance MCP Client

Dans `pom.xml`, on ajoute le starter Spring AI pour le client MCP :

```xml
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-mcp-client</artifactId>
</dependency>
```

> 🎯 Ce starter fournit automatiquement les beans nécessaires :
> - Gestion des connexions SSE.
> - Découverte automatique des outils exposés par les serveurs MCP.
> - Implémentation du `ToolCallbackProvider` pour rendre ces outils disponibles au LLM.

## Configuration de la connexion SSE

Dans `application.properties`, on déclare l'URL du serveur MCP Agenda :

```properties
spring.ai.mcp.client.sse.connections.first.url=http://localhost:8080
```

> 🌐 Cette propriété ouvre automatiquement une connexion SSE (Server-Sent Events) vers le serveur MCP.
> Le client reçoit en temps réel les métadonnées et la liste des outils exposés.

### 🧬 Cycle de vie de la connexion

1. **Démarrage** : le starter MCP initialise la connexion SSE au serveur.
2. **Découverte** : le serveur envoie la liste des outils avec leurs schémas JSON (signatures, descriptions).
3. **Enregistrement** : Spring AI crée dynamiquement des callbacks MCP pour chaque outil.
4. **Invocation** : quand le LLM décide d'appeler un outil, le callback envoie la requête au serveur MCP.
5. **Réponse** : le serveur exécute l'action et retourne le résultat.
6. **Injection** : le résultat est réinjecté dans la conversation comme un message supplémentaire.

## Enregistrement des outils MCP dans le ChatClient

Dans la classe `ConverseWithAixolotl`, on ajoute un nouveau paramètre au constructeur :

```java
public ConverseWithAixolotl(ChatModel chatModel,
                            ChatMemory chatMemory,
                            VectorStore vectorStore,
                            EmailSenderTool emailService,
                            ToolCallbackProvider tools, // <-- Nouveau paramètre
                            @Value("${sensitiveWords}") List<String> sensitiveWords) {
  
  this.chatClient = ChatClient.builder(chatModel)
    .defaultSystem(buildSystemPrompt())
    .defaultTools(emailService)          // Outils locaux (@Tool)
    .defaultToolCallbacks(tools)          // Outils distants MCP
    .defaultAdvisors(
       MessageChatMemoryAdvisor.builder(chatMemory).build(),
       new QuestionAnswerAdvisor(vectorStore),
       new SafeGuardAdvisor(sensitiveWords)
    )
    .build();
}
```

> 🛠️ `.defaultToolCallbacks(tools)` est la ligne clé : elle enregistre les outils MCP distants.
> Sans elle, le LLM ne verrait que les outils locaux (annotés `@Tool`).

La méthode `converse` reste inchangée : aucune modification nécessaire dans la logique métier !

## 🔍 Comment ça marche concrètement ?

Voici le flux complet d'une requête utilisateur faisant appel à un outil MCP :

```
1. Utilisateur : "Ajoute une tâche 'Préparer démo' demain après-midi"
   ↓
2. LLM analyse et décide d'utiliser l'outil createAgendaItem
   ↓
3. Spring AI sérialise l'appel (JSON) vers le serveur MCP Agenda
   ↓
4. Le serveur Agenda exécute la méthode Java correspondante
   ↓
5. Le serveur retourne le résultat (ex: nouvelle tâche créée)
   ↓
6. Spring AI injecte ce résultat dans la conversation
   ↓
7. LLM génère une réponse enrichie : "J'ai ajouté la tâche à votre agenda !"
```

> 💡 Le modèle IA combine outils locaux (`EmailSenderTool`) et outils distants (MCP Agenda) de manière transparente.

## 🧪 Testez l'intégration MCP

### 1. Démarrez le serveur MCP Agenda

Depuis le dossier `calendar/` :
```shell
./gradlew bootRun
```

Le serveur démarre sur `http://localhost:8080` et expose ses 3 outils via SSE.

### 2. Lancez l'application client Aixolotl

```shell
mvn spring-boot:run
```

### 3. Posez des questions agenda à Aixolotl

Exemples de prompts :
- "Quelles sont mes tâches prévues ?"
- "Ajoute une réunion 'Sprint Planning' demain à 10h"
- "Quelle heure est-il actuellement ?"

### 4. Observer les logs

Côté serveur MCP, on verra les appels d'outils :
```
Tool invoked: listAgenda
Tool invoked: createAgendaItem with params: {...}
```

Côté client, on verra les callbacks MCP se déclencher.

> ⚠️ Le serveur MCP Agenda doit être démarré **avant** le client Aixolotl.


## 🧠 Pourquoi c'est puissant ?

Le protocole MCP permet de **découpler** totalement les outils métier de l'application IA :
- **Extensibilité** : on peut facilement brancher un autre serveur MCP (CRM, météo, tickets, etc.)
- **Réutilisabilité** : un même serveur MCP peut servir clients (web, CLI, mobile).
- **Standardisation** : plus besoin de coder des intégrations spécifiques pour chaque source de données.

> 💡 On pourrait facilement ajouter un autre serveur MCP (pour la météo par exemple). Il n'y a qu'à rajouter une propriété :
> ```properties
> spring.ai.mcp.client.sse.connections.weather.url=http://localhost:8081
> ```

C'est tout pour l'intégration MCP côté client ! 🚀🦎
