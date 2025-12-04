# Solution du Lab 03 - Ajoutons de la mémoire et un peu de modération à notre Aixolotl 🧠

Découverte des APIs `ChatMemory`, `ChatClient` et `Advisor`.


## Utilisation de `ChatClient` à la place de `ChatModel`

Afin d'intercepter les requêtes et les réponses entre l'utilisateur et le modèle d'IA, 
nous allons devoir utiliser l'API `Advisor`.
Pour en profiter, il est essentiel d'utiliser un `ChatClient` qui est une enveloppe autour
du `ChatModel` (qui était limité à simplement envoyer et recevoir des messages).

> 💡 Une instance de `ChatClient` utilise un `ChatModel` pour communiquer avec le modèle d'IA.
> Nous allons pouvoir doper notre Aixolotl avec plus d'options.

La fonction `converse` devient donc :

```java
public Flux<String> converse(final String prompt) {
    return ChatClient.builder(chatModel).build()
      .prompt()
      .system(buildSystemPrompt())
      .user(prompt)
      .stream()
      .content();
  }
```

Nous sommes maintenant prêt à ajouter un `Advisor` permettant de donner de la mémoire à notre IA.

## Utilisation de l'interface `ChatMemory`

La classe `AixolotlMemory` à créer devra implémenter l'interface `ChatMemory` de Spring AI.

Cette interface, très simple, nous forcera à implémenter trois méthodes : `add`, `get` et `clear`. 

Pour donner de la mémoire à notre mascotte, nous allons stocker les messages de la conversation dans
une `Map`. La clé sera un identifiant unique qui représente la conversation et la valeur sera une simple
liste de messages liée à cette conversation.

Cette `Map` sera déclarée et initialisée en tant qu'attribut de classe, les données seront donc dans la
mémoire RAM. On peut imaginer une implémentation plus complexe en stockant les messages dans
une base de données à part.

```java
@Component
public class AixolotlMemory implements ChatMemory {

  private static final Map<String, List<Message>> inMemoryMessages = new ConcurrentHashMap<>();

  @Override
  public void add(String conversationId, List<Message> messages) {
    inMemoryMessages.computeIfAbsent(conversationId, id -> new ArrayList<>()).addAll(messages);
  }

  @Override
  public List<Message> get(String conversationId, int lastN) {
    List<Message> messages = inMemoryMessages.getOrDefault(conversationId, new ArrayList<>());
    return messages
      .stream()
      .skip(Math.max(0, messages.size() - lastN))
      .toList();
  }

  @Override
  public void clear(String conversationId) {
    inMemoryMessages.clear();
  }
}
```

Pour augmenter notre agent de cette mémoire, nous allons utiliser l'advisor `MessageChatMemoryAdvisor` :

```java
private final UUID conversationId = UUID.randomUUID();
private final ChatMemory aixolotlMemory;

public Flux<String> converse(final String prompt) {
  return ChatClient.builder(chatModel)
    // ...
    .advisors(
      new MessageChatMemoryAdvisor(aixolotlMemory, conversationId.toString(), 50)
    )
    .stream()
    .content()
    ;
}
```

## Modération d'une liste de mots via `SafeGuardAdvisor` 🤐

```java
public Flux<String> converse(final String prompt) {
  return ChatClient.builder(chatModel)
    // ...
    .advisors(
      new SafeGuardAdvisor(List.of("caca", "boudin"))
    )
    .stream()
    .content()
    ;
}
```
