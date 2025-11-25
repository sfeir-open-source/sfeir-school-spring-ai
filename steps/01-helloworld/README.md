# Lab 01 - Ma première IA locale avec Spring et Ollama 🦙

## Présentation de l'environnement de développement

La structure de l'application étant déjà faite, vous n'avez plus qu'à vous concentrer sur la construction
de votre Aixolotl.
Une application front-end est également déjà disponible, vous le trouverez dans le dossier ./aixolotl-chat-ui à la racine du repo.
Une fois dedans exécuter les commandes suivantes : 

* `npm install`
* `npm run dev`

Votre ui est prête !

#### retour au projet 01-helloworld

Dans l'application vous trouverez le controleur http permettant de connecter l'interface web et le back-end existe déjà (classe `ConverseWithAixolotlHttpApi`).
Ce dernier utilise un objet de type `ConverseWithAssistant` pour envoyer le prompt de l'utilisateur au modèle choisi.

## Implémentez l'interface ConverseWithAssistant

À l'instant où vous lisez ces lignes, le projet ne démarre pas car il manque une classe qui implémente
l'interface `ConverseWithAssistant`. Votre première mission sera de l'ajouter.

Créez cette classe dans le package `conversation`.
Pour que cette dernière fonctionne complètement, vous aurez besoin d'injecter un objet de type `org.springframework.ai.chat.model.ChatModel`.

N'oubliez pas d'annoter votre classe afin qu'elle soit visible par Spring.

Récapitulons :

- Créez une classe qui implémente `ConverseWithAssistant`
- Injectez `org.springframework.ai.chat.model.ChatModel` afin d'y utiliser une instance dans la fonction à implémenter
- Plusieurs `ChatModel` sont fournis par Spring AI,
  [utilisez l'implémentation pour Ollama](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html#_auto_configuration) qui vous permettra de vous y connecter
- Optionnel: depuis le fichier de propriétés `application.properties`, précisez quel modèle utiliser

Grâce à ce premier lab, vous utiliserez l'une des API les plus importantes de Spring AI : `ChatModel` !

À vous de jouer ! 😉

*La documentation Spring sur l'API `ChatModel` se trouve [ici](https://docs.spring.io/spring-ai/reference/api/chatmodel.html).*


