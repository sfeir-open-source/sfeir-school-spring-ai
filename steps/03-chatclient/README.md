# Lab 03 - Ajoutons de la mémoire et un peu de modération à notre Aixolotl 🧠

Nous avons vu comment structurer nos prompts, "templatiser" et définir un rôle spécifique par message.
Toutes ces techniques apportent du contexte à notre requête utilisateur afin d'obtenir une réponse la plus précise possible.

Vous avez sûrement constaté un comportement "poisson rouge" de notre assistant, il ne se souvient pas de l'ensemble de
la conversation.

Dans ce troisème lab, nous allons découvrir les APIs `ChatMemory`, `ChatClient` et `Advisor` et les utiliser pour
ajouter de la mémoire à notre agent Aixolotl. Vous allez également apprendre à modérer une liste de mots 🤐.
 
## Ce qu'il faut faire
Pour ce lab vous devez :
- Dans la fonction `converse`, remplacer l'utilisation de `ChatModel` par `ChatClient` pour pouvoir utiliser les advisors
- Dans le package `configuration`, créer la classe `AixolotlMemory` qui implémente l'interface `ChatMemory` de Spring AI 
Cette classe devra stocker les messages de la conversation dans une Map
  - Implémenter les méthodes add, get et clear de l'interface `ChatMemory`
- Utiliser le builder de l'advisor `MessageChatMemoryAdvisor` pour donner de la mémoire à votre agent Aixolotl
- Amusez-vous à modérer une liste de mots via l'advisor `SafeGuardAdvisor`

*Vous trouverez [ici](https://docs.spring.io/spring-ai/reference/api/chatclient.html) la documentation sur l'API `ChatClient`*
