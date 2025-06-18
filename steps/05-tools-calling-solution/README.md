# Lab 05 - Tool calling - Donnons à notre AIxolotl la capacité d'interagir avec le monde extérieur 🌍


Découverte de l'API `Tool` de Spring AI.

## 🧰 Pré-requis
Avant de commencer, nous devons simuler un serveur d’e-mails afin de pouvoir visualiser les messages envoyés.

1. Lancer un faux serveur SMTP

Exécutez la commande suivante dans un terminal pour démarrer un serveur SMTP de test :

```shell
docker run --rm -it -p 5000:80 -p 2525:25 rnwood/smtp4dev
```
Ce serveur permet de capturer les e-mails envoyés sans qu’ils quittent ta machine.

2. Ouvrir l’interface d’administration

Dans ton navigateur préféré (Brave), navigue à cette adresse :
👉 http://localhost:5000.

Tous les e-mails simulés y apparaîtront.

3. Configurer Spring Boot pour utiliser ce serveur local

Dans `application.properties`, ajoutons les lignes suivantes :

```properties
spring.mail.host=localhost
spring.mail.port=2525
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false
```

## Ajout d’un outil d’envoi d’e-mails

Spring AI permet à un modèle d’IA d’interagir avec des outils : des fonctions métiers que l'on peut exposer via l’annotation `@Tool`.
Cela donne littéralement des "super-pouvoirs" à notre modèle d'IA, lui permettant d’agir concrètement au lieu de simplement répondre à des questions.

Dans cet exemple, nous allons enregistrer un outil permettant à l’IA d’envoyer un e-mail à un administrateur.

```java
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSenderTool {
  private final EmailSender emailSender;

  @Tool(
    name = "sendEmail",
    description = "Send an email to ask for administrator rights"
  )
  @Async
  public void sendEmail(@ToolParam(description = "Mail subject") String subject,
                        @ToolParam(description = "Name of the current user asking for admin rights") String userNameToGrantAdminRights) {

    log.info("Préparation de l'envoi d'un mail...");
    log.info("Objet du mail : {}", subject);
    log.info("Nom du collaborateur : {}", userNameToGrantAdminRights);

    emailSender.sendTo(subject, "crazy-admin@sfeir.com", userNameToGrantAdminRights);
    log.info("Mail envoyé !");
  }
}
```

## 🔍 Explication

`@Tool` : Cette annotation signale à Spring AI que cette méthode est un outil utilisable par l’IA.
On peut lui donner un nom (utilisé par le modèle pour l’invoquer) et une description (qui aide le modèle à comprendre quand l’utiliser).

`@ToolParam` : Permet de décrire chaque paramètre de la méthode pour que l’IA sache comment les remplir.


## ⚠️ Attention à la compatibilité des modèles
Tous les modèles d’IA ne prennent pas en charge les Tools.
Avant de lancer l'appli, vérifiez que le modèle que utilisé supporte cette fonctionalité.

Sur le site d’Ollama, il est possible de filtrer les modèles compatibles avec les Tools.
