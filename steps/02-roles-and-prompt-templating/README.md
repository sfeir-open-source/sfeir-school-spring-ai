# Lab 02 - Je personnalise mon assistant Aixolotl

Vous venez de créer un assistant permettant d'échanger avec un modèle de langage, félicitations ! 🥳

Nous allons maintenant personnaliser notre assistant. Pour l'instant, celui-ci ne fait que transmettre le prompt
de notre utilisateur au modèle de langage et renvoie la réponse du modèle telle quelle.

Afin d'améliorer ses performances, nous allons lui apporter un peu de contexte et d'identité.

L'objectif de cet exercice est de vous familiariser avec l'API `Message` et `PromptTemplate` de Spring AI,
en mettant en place un chatbot Axolotl. Vous apprendrez à utiliser des templates pour personnaliser les
messages envoyés par le chatbot en fonction des informations fournies au runtime.

## Structurons notre prompt

Les prompts sont structurés autour de différents rôles, tels que le système, l'utilisateur, l'assistant et
parfois d'autres entités.

Pour rajouter du contexte au prompt envoyé par notre nouvel employé, nous allons nous appuyer sur
les APIs `Message` et `PromptTemplate` de Spring AI.
Le prompt du système définira l'identité du chatbot, ainsi que le contexte dans lequel il opère : Donnez un
nom à votre assistant intelligent. Son objectif est d'assister un nouvel employé de l'entreprise dans ses démarches
d'onboarding.
Vous utiliserez des placeholders ({name} et {company}) qui seront remplacés par des valeurs spécifiques lors de l'exécution.

Quand vous aurez terminé, renommez la classe `ConverseWithRawAssistant` en `ConverseWithAixolotl` pour plus de cohérence.

*Modifiez la fonction `converse` et changez les paramètres passés à la méthode stream du `ChatModel` pour prendre
en compte les nouveaux prompts (système et utilisateur).*

À la fin de ce lab, vous serez capable de :

- Créer un prompt système et utilisateur
- Remplacer dynamiquement les valeurs dans le prompt système au moment de l'exécution
- Passez les prompts au `ChatModel`
- Interagir avec Aixolotl et observer comment il utilise les informations fournies pour répondre aux utilisateurs

*La documentation Spring sur l'API `Message` et `PromptTemplate`  est disponible
[ici](https://docs.spring.io/spring-ai/reference/api/chatmodel.html#_prompt)
et [ici](https://docs.spring.io/spring-ai/reference/api/prompt.html#_prompt)*
