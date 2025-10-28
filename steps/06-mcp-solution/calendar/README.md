# Serveur MCP Calendar - Exposition d'outils pour manipuler l'agenda 📅

Ce serveur MCP (Model Context Protocol) expose des outils permettant à un client MCP de gérer un agenda.
Nous avons choisi d'utiliser SSE (Server-Sent Events) pour le transport des données entre client et serveur.

## 🚀 Démarrer le serveur

Pour lancer le serveur MCP Calendar, exécute la commande suivante dans un terminal :

```shell
./gradlew bootRun
```

Le serveur sera accessible à l'adresse : `http://localhost:8080`

> 💡 Tout client MCP pourra se connecter à cette URL et découvrir automatiquement les outils exposés.

## 📦 Base de données H2

Une base de données H2 sera automatiquement créée en mémoire et alimentée au démarrage avec des données d'un petit agenda.

Ces dernières sont dans le fichier `data.sql` qui contient les requêtes SQL pour insérer des tâches d'exemple dans l'agenda.

> ⚠️ Les données sont réinitialisées à chaque redémarrage.

## 🛠️ Outils exposés

Le serveur MCP propose **3 outils** que les clients peuvent invoquer :

### 1. `getCurrentDateTime`
Récupère la date et l'heure actuelles. Cela permettra au modèle se re-situer dans le temps.

```java
@Tool(
  name = "getCurrentDateTime",
  description = "Get the current date and time"
)
public String getCurrentDateTime() {
  return LocalDateTime.now().toString();
}
```

### 2. `listAgenda`
Liste toutes les tâches présentes dans l'agenda.

```java
@Tool(
  name = "listAgenda",
  description = "Read all calendar/agenda items"
)
public List<AgendaItem> listAgenda() {
  return agendaRepository.findAll();
}
```

### 3. `createAgendaItem`
Ajoute une nouvelle tâche à l'agenda à partir d'une requête utilisateur.

```java
@Tool(
  name = "createAgendaItem",
  description = "Add a new calendar item from user query"
)
public AgendaItem createAgendaItem(
  @ToolParam(description = "Task title") String title,
  @ToolParam(description = "Task description") String description,
  @ToolParam(description = "Date and time (ISO format)") String dateTime
) {
  // ...
}
```

> 🎯 Le client MCP n'a pas besoin de connaître l'implémentation : il reçoit automatiquement la signature et la description de chaque outil au moment de la connexion.

## 🔄 Comment ça marche ?

1. Le serveur démarre et expose un endpoint SSE
2. Un client MCP se connecte (via la property `spring.ai.mcp.client.sse.connections.first.url`)
3. Le serveur envoie la liste des outils disponibles (format JSON Schema).
4. Quand le modèle IA du client invoque un outil, la requête transite par le protocole MCP
5. Le serveur exécute la méthode Java correspondante et retourne le résultat
6. Le client injecte ce résultat dans la conversation

## 🧪 Testez le serveur

Une fois lancé, on peut :
- Démarrez le client MCP
- Demandez : "Quelles sont mes tâches ?", "Ajoute une réunion demain à 14h".
- Observez les logs et/ou la base de données pour vérifier que Aixolotl n'est pas un mytho et a bien modifié les données via les outils exposées
