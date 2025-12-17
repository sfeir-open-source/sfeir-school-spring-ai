# Lab 05 - Tools calling - Donnons à notre AIxolotl la capacité d'interagir avec le monde extérieur 🌍
Donnons maintenant à notre agent AIxolotl la capacité  d'utiliser des outils externes.
En effet, en tant qu'assistant d'onboarding, nous aimerions qu'il puisse faire à notre place certaines tâches essentielles en tant que nouvel 
arrivant, mais sans grande valeur ajoutée. Par exemple, il pourrait enregistrer la maillist de l'équipe, envoyer un mail de demande de droits au service 
informatique, ou encore poster un message de bienvenue dans une conversation.

## Ce qu'il faut faire

Dans ce lab vous devrez :

### Choisir le bon LLM
En effet, certains modèles de langage sont plus adaptés que d'autres pour appeler des outils. Pour tester en local, à l'heure actuelle, 
nous vous conseillons d'utiliser le modèle `llama3-groq-tool-use`, une version fine tuné de llama 3.

### Créer un service d'envoie de mail `EmailService`
Pour gagner du temps vous pourrez vous servir du starter de mail : 
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
```
Ce service devra être capable d'envoyer un mail à une adresse donnée avec un sujet et un contenu.
Vous pourrez utiliser le serveur SMTP suivant pour vos tests :

```
###############
### Mailing ###
###############
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=0c43d774d76cb2
spring.mail.password=8c1097c006d2f9
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Home sweet home
Si vous préférez rester en local, exécutez la commande suivante dans un terminal pour démarrer un serveur SMTP de test :

```shell
docker run --rm -it -p 5000:80 -p 2525:25 rnwood/smtp4dev
```

### Le définir comme un outil appelable par le LLM

Une fois votre service prêt, vous devrez le définir comme un outil appelable par le LLM. 
Pour cela, vous pourrez vous appuyer sur la documentation de Spring AI https://docs.spring.io/spring-ai/reference/api/tools.html

Plusieurs choix s'offrent à vous, comme l'utilisation de l'annotation `@Tool` pour définir votre service comme un outil.

### Configurer le LLM pour qu'il puisse appeler l'outil
Pour que le LLM puisse appeler votre outil, vous devrez configurer le modèle de langage pour qu'il reconnaisse l'outil que vous avez créé. 
Vous pouvez le faire en spécifiant votre service d'envoie de mail au niveau de votre ChatClient via la méthode `.tools()`.

### Transmettre des paramètres à l'outil
Il est possible de renseigner via le prompt de l'utilisateur les paramètres nécessaires à l'appel de l'outil. Cela se fait en 
utilisant l'annotation `@ToolParam`.

### Tester l'outil
Amusez-vous à tester votre outil en demandant à AIxolotl d'envoyer un mail afin d'obtenir les droits d'accès.
