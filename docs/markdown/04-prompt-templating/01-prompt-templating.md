

# Structurer son prompt

## Prompt

Spring AI offre une couche d'abstraction performante qui unifie l'utilisation des différentes API de LLM grâce à un système de templates de prompts.
Le composant clé de ce système est la classe ```PromptTemplate```

<br></br>

```java
  public static String systemPromptTemplate = """
        You are a blue smart Axolotl chatbot. Your name is {name}.
        The company you're representing is called {company}.
  """;
```

```java
  private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate(Aixolotl.getSystemPromptTemplate());
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
```


##==##


# Structurer son prompt

## Message

Un prompt est constitué d'une liste de message dont chacun est associé à un rôle spécifique. Spring AI propose une interface ```Message``` 
avec différentes implémentations pour modéliser les messages. On distingue 4 rôles possible :

| Rôle      | Définition                                                                                                                                                           |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| USER      | Représente l'utilisateur dont le message est l'entrée directe du LLM                                                                                                 |
| ASSISTANT | Représente la réponse générée par le modèle d'IA. Elle maintient le flux de la conversation et peut également passer des instructions sur la demande d'appel d'outil |
| SYSTEM    | Message système ayant pour but de donner des instructions, des contraintes et un contexte général                                                                    |
| TOOL      | Utiliser pour fournir au modèle le résultat de l'exécution d'une fonction ou d'un outil externe                                                                      |



##==##


# Structurer son prompt

## Réponse structurée

A l'image du prompt, il peut être important de fournir une réponse structurée à l'utilisateur ou l'application. Transformer une réponse en fichier JSON, XML 
ou autre offre la possibilité de transmettre celle-ci à d'autre fonctions ou méthodes par exemple.

C'est l'interface ```StructuredOutputConverter``` et ses implémentations qui sont mises à disposition par Spring AI

Exemple de Spring AI :

```java
BeanOutputConverter<List<ActorsFilms>> outputConverter = new BeanOutputConverter<>(
        new ParameterizedTypeReference<List<ActorsFilms>>() { });

String format = this.outputConverter.getFormat();
String template = """
        Generate the filmography of 5 movies for Tom Hanks and Bill Murray.
        {format}
        """;

Prompt prompt = new PromptTemplate(this.template, Map.of("format", this.format)).create();

Generation generation = chatModel.call(this.prompt).getResult();

List<ActorsFilms> actorsFilms = this.outputConverter.convert(this.generation.getOutput().getText());
```
