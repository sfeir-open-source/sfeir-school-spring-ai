# Solution du Lab 01 - Ma première IA locale avec Spring et Ollama 🦙

## Avec auto-configuration

Nous allons tirer parti de la dépendance `spring-ai-starter-model-ollama`.
Cette dernière contient une classe `OllamaChatModel` qui implémente l'interface `ChatModel`.
Elle est automatiquement configurée et mise à disposition pour nous sous forme de `@Bean` 🤗.
Elle contient l'URL par défaut d'Ollama et autres paramètres pertinents.

Pour profiter de cette configuration automatique, il vous suffit de déclarer un attribut de classe de type `ChatModel`. C'est aussi simple que cela ! 😉
Quand Spring instanciera votre classe annotée avec `@Service` (ou `@Component`), le framework initialisera automatiquement les attributs de cette classe via
l'injection par constructeur.

On peut enfin commencer à utiliser un modèle d'IA sans avoir à nous soucier d'une quelconque configuration manuelle.

```xml

<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-model-ollama</artifactId>
  <version>${spring-ai.version}</version>
</dependency>

```

```java

@Service
public class ConverseWithRawAssistant implements ConverseWithAssistant {

  private final ChatModel chatModel;

  public ConverseWithRawAssistant(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public Flux<String> converse(final String prompt) {
    return chatModel.stream(new UserMessage(prompt));
  }
}
```

N'oubliez pas de préciser le modèle que vous souhaitez utiliser via la propriété
`spring.ai.ollama.chat.options.model` dans le fichier `application.properties`.
Si aucun modèle n'est paramétré, Mistral sera le choix par défaut, cocorico 🐓🇫🇷 !

## Sans auto-configuration

Dans certaines situations, il peut être préférable de ne pas utiliser l'auto-configuration.
Dans ce cas, il faudra importer la dépendance `spring-ai-ollama` au lieu de `spring-ai-starter-model-ollama`.
Cela permettra d'instancier manuellement l'instance `OllamaChatModel`. Il suffit de créer une classe de
configuration annotée avec `@Configuration`, dans laquelle on définira un `@Bean` qui retournera l'instance `OllamaChatModel`.
Cette approche donne un contrôle total sur la configuration sans forcément passer par les fichiers de propriétés par exemple.

Dépendance à ajouter :

```xml

<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-ollama</artifactId>
  <version>${spring-ai.version}</version>
</dependency>
```

Configuration du bean manuellement :

```java

@Bean
public ChatModel ollamaChatModel() {
  final var ollamaApi = new OllamaApi(OLLAMA_URL);
  return OllamaChatModel.builder()
    .ollamaApi(ollamaApi)
    .defaultOptions(OllamaOptions.builder()
      .model()
      .temperature(0.0)
      .build())
    .build();
}

```
