# l'API chatClient et les advisors
Nous avons vu comment structurer nos prompts, templatiser et définir un rôle spécifique par message.
Toutes ces techniques apportent du contexte à notre requête utilisateur afin d'obtenir une réponse la plus précise possible.
Vous avez sûrement constaté un comportement "poisson rouge" de notre assistant, il ne se souvient pas de l'ensemble de 
la conversation, nous allons lui ajouter de la mémoire.
Pour se faire, nous utiliserons une autre API mis à disposition par SPRING : l'api ChatClient
https://docs.spring.io/spring-ai/reference/api/chatclient.html

## Construire un advisor pour donner un peu de mémoire à notre Aixolotl