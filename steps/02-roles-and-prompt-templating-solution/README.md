# Solution du Lab 02 - Rôles et Prompt templating

## Découverte de l'API `Message` et `PromptTemplate`

Pour le prompt représentant le système, nous allons utiliser le template suivant :

> You are a blue smart Axolotl chatbot. Your name is {name}.
> You also have memory of your conversations.
> The company you're representing is called {company}.
> It's a French company composed of developers.
> Be concise in your responses but give meaningful information.
> When you're greeting, be very concise by asking how you can help.

Notre Aixolotl a maintenant une "identité" et "sait" à quoi il sert grâce au prompt ayant le rôle `SYSTEM`.

Le code : 

```java
public Flux<String> converse(final String prompt) {
  final var systemPromptTemplate = new SystemPromptTemplate(Aixolotl.getSystemPromptTemplate());

  final var systemPromptWithTemplateApplied = systemPromptTemplate.createMessage(ofEntries(
    entry("name", "Azul"),
    entry("company", "Sfeir"))
  ).getText();

  return chatModel.stream(
    new SystemMessage(systemPromptWithTemplateApplied),
    new UserMessage(prompt));
}
```

On peut refactorer ainsi : 

```java
public Flux<String> converse(final String prompt) {
  return chatModel.stream(
    new SystemMessage(buildSystemPrompt()),
    new UserMessage(prompt));
}

private static String buildSystemPrompt() {
    final var systemPromptTemplate = new SystemPromptTemplate(Aixolotl.getSystemPromptTemplate());
    return systemPromptTemplate.createMessage(ofEntries(
      entry("name", "Azul"),
      entry("company", "Sfeir"))
    ).getText();
  }
```

*La documentation Spring sur l'API `Message` et `PromptTemplate`  est disponible
[ici](https://docs.spring.io/spring-ai/reference/api/chatmodel.html#_prompt)'
et [ici](https://docs.spring.io/spring-ai/reference/api/prompt.html#_prompt)*
