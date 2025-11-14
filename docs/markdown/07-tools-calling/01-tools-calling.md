# Tools calling

## 3 problèmes

<br>

<div class="comparison-card-wrapper">
  <div class="comparison-card mcp">
    <h5 class="comparison-card-title"><i data-feather="alert-circle"></i> Hallucinations</h5>
  </div>
  <div class="comparison-card mcp">
    <h5 class="comparison-card-title"><i data-feather="database"></i> Limité aux données d'entrainement</h5>
  </div>
  <div class="comparison-card mcp">
    <h5 class="comparison-card-title"><i data-feather="cpu"></i> Calcul</h5>
  </div>
</div> 

<br>

Malgré leur grande connaissance les modèles de langages sont limités dans l'accès à l'information temps réel et 
aux intéractions avec des systèmes externes (agenda, boîte mail, ...). Les tools calling sont un moyen pour les LLMs 
de se connecter au monde extérieur

<br>

Spring AI propose des API simples d'utilisation pour définir des outils et gérer les demandes d'appel d'outils 
émises par un modèle et en assurer l'exécution. <!-- .element: class="admonition tip" -->

##==##


# Tools calling
## Définir un outil

L'annotation `@Tool` vous permet de déclarer un outil

```java
class DateTimeTools {

    @Tool(description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    @Tool(description = "Set a user alarm for the given time, provided in ISO-8601 format")
    void setAlarm(String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }

}
```


##==##


# Tools calling
## Spécifier des paramètres d'entrée

```java
class DateTimeTools {

    @Tool(description = "Set a user alarm for the given time")
    void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }
}
```


##==##


# Tools calling
## Spécifier des paramètres d'entrée

L'API `ToolContext` vous permet également de transmettre des informations à votre outil :

```java
class CustomerTools {

    @Tool(description = "Retrieve customer information")
    Customer getCustomerInfo(Long id, ToolContext toolContext) {
        return customerRepository.findById(id, toolContext.getContext().get("tenantId"));
    }

}
```

```java
ChatModel chatModel = ...

String response = ChatClient.create(chatModel)
        .prompt("Tell me more about the customer with ID 42")
        .tools(new CustomerTools())
        .toolContext(Map.of("tenantId", "acme"))
        .call()
        .content();

System.out.println(response);
```



##==##


# Tools calling
## Spécifier le format de sortie

```java
class CustomerTools {

    @Tool(description = "Retrieve customer information", resultConverter = CustomToolCallResultConverter.class)
    Customer getCustomerInfo(Long id) {
        return customerRepository.findById(id);
    }

}
```

##==##


# Tools calling
## Appeler un outil

L'API ChatClient vous permet de renseigner les outils appelables par le LLM via la méthode `.tools()` :

```java
ChatModel chatModel = ...

String response = ChatClient.create(chatModel)
        .prompt("Can you set an alarm 10 minutes from now?")
        .tools(new DateTimeTools())
        .call()
        .content();

System.out.println(response);
```
