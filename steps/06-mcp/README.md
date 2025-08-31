# Lab 06 - Protocole MCP

![image info](../../docs/assets/images/mcp.png)

Dans l'exercice précédent nous avons donné à notre aixolotl la capacité d'interagir avec le monde extérieur.
Mais imaginez notre assistant IA devant se connecter à une dizaine de sources de données externes, il faudrait alors 
développer un connecteur spécifique pour chaque source rendant notre application bien complexe. Le protocole MCP est là pour palier 
à cette problématique en standardisant la connection des modèles aux outils externes.

Pour notre aixolotl, nous vous mettons à disposition un serveur MCP prêt à l'emploi, votre agenda personnel ! Il vous 
permettra de consulter et saisir des tâches dans votre planning (pratique :bulb:).



## Ce qu'il faut faire

Dans ce lab vous devrez :

### Importer le starter mcp client de spring ai
Spring ai vous met à disposition un starter mcp client : 

```xml
    <dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-starter-mcp-client</artifactId>
    </dependency>
```

Il vous permettra de vous connecter simultanément à un ou plusieur serveur MCP

### Configurer votre client MCP
Trouver la bonne configuration pour vous connecter au serveur MCP calendar.
Vous pouvez vous aider de la [documentation officielle](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html#_common_properties) 
pour renseigner la/les bonnes properties.

:information_source: C'est la technologie SSE (Server-Sent Events) qui est utilisée pour les échanges de données avec le serveur MCP.

### Configurer le LLM pour qu'il puisse appeler votre outil externe

Pour que le LLM puisse appeler votre outil, vous devrez configurer le modèle de langage pour qu'il reconnaisse l'outil que vous avez créé.
Vous pouvez le faire en spécifiant un nouveau `ToolCallbackProvider` au niveau de votre ChatClient via la méthode `.toolCallbacks(...)`

[documentation officielle](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html)

### Tester l'outil
Amusez-vous à tester votre outil en demandant à AIxolotl de consulter votre agenda ou bien de saisir une nouvelle réunion.

### Rajouter des fonctionnalités à votre serveur MCP
N'hésitez pas à jouer avec la partie serveur MCP
