<!-- .slide -->

# Protocole MCP <img src="./assets/images/mcp.png" alt="MCP" style="height:80px;vertical-align:middle;margin-left:1rem;" />

### C'est quoi ?

![](./assets/images/mcp-simple-diagram.avif)

##==##

# Protocole MCP <img src="./assets/images/mcp.png" alt="MCP" style="height:80px;vertical-align:middle;margin-left:1rem;" />


<div>
  <blockquote style="padding: 2rem; width: 75%">
    <cite>Un standard construit autour de 3 pilliers afin de connecter les modèles à leur environnement <cite>
  </blockquote>

  <br>
  <br>
  <br>

  <div style="display:flex; justify-content:space-around;" class="fragment fade-up" data-fragment-index="1">
    <div>
    📚<br><strong>Ressources</strong>
      <br>
      <small class="fragment" data-fragment-index="2">Pour plus de contexte</small>
      <div class="fragment" data-fragment-index="3">
        <small class="fragment" data-fragment-index="3">
          <ul>
            <li>Lire des notes utilisateurs</li>
            <li>Extraire du contenu d’une base de données</li>
            <li>Interroger un moteur de recherche interne</li>
          </ul>
        </small>
      </div>
    </div>
    <div>🧰<br><strong>Outils</strong>
      <br>
      <small class="fragment" data-fragment-index="4">Pour agir</small>
      <div class="fragment" data-fragment-index="5">
        <small class="fragment" data-fragment-index="5">
          <ul>
            <li>Envoyer un e-mail</li>
            <li>Appeler une API</li>
            <li>Exécuter un calcul</li>
          </ul>
        </small>
      </div>
    </div>
    <div>💬<br><strong>Prompts</strong>
      <br>
      <small class="fragment" data-fragment-index="6">Modèles</small>
      <div class="fragment" data-fragment-index="7">
        <small class="fragment" data-fragment-index="7">
          <ul>
            <li>Réutilisation de prompts systèmes/utilisateurs</li>
            <li>Prompt as a function</li>
          </ul>
        </small>
      </div>
    </div>
  </div>
</div>

##==##

# Protocole MCP <img src="./assets/images/mcp.png" alt="MCP" style="height:80px;vertical-align:middle;margin-left:1rem;" />

### Tool calling vs MCP

<div class="comparison-card-wrapper">
  <div class="comparison-card toolcalling">
    <h4 class="comparison-card-title">🔧 Tool calling</h4>
    <div class="comparison-card-content">
      <div class="comparison-card-item">
        <h6 class="comparison-card-subtitle">Portée</h6>
        <span class="comparison-card-value">Une seule action</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="1">
        <h6 class="comparison-card-subtitle">État</h6>
        <span class="comparison-card-value">Stateless</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="2">
        <h6 class="comparison-card-subtitle">Flux</h6>
        <span class="comparison-card-value">Uni-directionnel</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="3">
        <h6 class="comparison-card-subtitle">Intégration</h6>
        <span class="comparison-card-value">Intégrée au modèle</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="4">
        <h6 class="comparison-card-subtitle">Configuration</h6>
        <span class="comparison-card-value">Minimale</span>
      </div>
    </div>
  </div>
  
  <div class="comparison-card mcp">
    <h4 class="comparison-card-title">🌐 MCP</h4>
    <div class="comparison-card-content">
      <div class="comparison-card-item">
        <h6 class="comparison-card-subtitle">Portée</h6>
        <span class="comparison-card-value">Écosystème complet</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="1">
        <h6 class="comparison-card-subtitle">État</h6>
        <span class="comparison-card-value">Stateful</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="2">
        <h6 class="comparison-card-subtitle">Flux</h6>
        <span class="comparison-card-value">Bi-directionnel</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="3">
        <h6 class="comparison-card-subtitle">Intégration</h6>
        <span class="comparison-card-value">Client/Serveur</span>
      </div>
      <div class="comparison-card-item fragment" data-fragment-index="4">
        <h6 class="comparison-card-subtitle">Configuration</h6>
        <span class="comparison-card-value">Avancée</span>
      </div>
    </div>
  </div>
</div>

##==##

# Protocole MCP <img src="./assets/images/mcp.png" alt="MCP" style="height:80px;vertical-align:middle;margin-left:1rem;" />

### Comment ça marche ? 🔍

<div class="r-stack">
  <div class="fragment fade-out" data-fragment-index="0" style="display: flex; justify-content: center; align-items: center; width: 100%;">
    <img src="https://docs.spring.io/spring-ai/reference/_images/mcp/mcp-stack.svg" alt="MCP Stack" style="width: 50%;" />
  </div>
  <div class="fragment" data-fragment-index="0" style="display: flex; justify-content: space-between; align-items: center; gap: 2rem; width: 100%;">
    <img src="https://docs.spring.io/spring-ai/reference/_images/mcp/mcp-stack.svg" alt="MCP Stack" style="width: 35%;" />
    <img src="https://docs.spring.io/spring-ai/reference/_images/mcp/java-mcp-client-architecture.jpg" alt="Java MCP Client Architecture" style="width: 45%;" />
  </div>
</div>