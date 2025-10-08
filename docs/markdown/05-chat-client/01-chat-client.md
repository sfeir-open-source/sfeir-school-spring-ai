

# ChatClient API

Un des outil essentiel de spring AI est son API ```ChatClient```. Il s'agit d'une interface plus évoluée et 
conviviale conçue pour simplifier les interfactions avec le modèle. C'est une interface de plus haut niveau que ```ChatModel```.

Principales fonctionnalités :
* API fluide : permet de construire des requêtes de manière plus lisible et chaînée
* Gestion des "Advisors"
* Appels de fonctions/outils
* Templates de prompts : aide à construire des prompts complexes à partir de modèle
<br></br>
```java
    String generation(String userInput) {
        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
```


##==##


# ChatClient API

## Advisors

C'est une des parties les plus importantes de ```ChatClient``` . Elle simplifie grandement l'ajout de fonctionnalités tranverses comme la gestion de l'historique 
de la conversation ou l'ajout de contexte

```java
var chatClient = ChatClient.builder(chatModel)
    .defaultAdvisors(
        MessageChatMemoryAdvisor.builder(chatMemory).build(), // chat-memory advisor
        QuestionAnswerAdvisor.builder((vectorStore).builder() // RAG advisor
    )
    .build();

var conversationId = "678";

String response = this.chatClient.prompt()
    // Set advisor parameters at runtime
    .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
    .user(userText)
    .call()
	.content();
```
