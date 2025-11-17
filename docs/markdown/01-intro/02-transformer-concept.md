<!-- .slide: class="quote-slide" -->
<blockquote>
  <cite>
    Attention is all you need...
  </cite>
</blockquote>

##==##
<section>
  <h1>Les transformers</h1>
  <div class="r-stack">
    <div style="display: flex; align-items: center; justify-content: space-between;">
      <div style="flex: 1; padding-right: 5px;">
        <img class= "fragment fade-left" src="../../assets/images/attention_research_1.png" width="60%" height="120%" alt="transformers">
      </div>
      <div style="flex: 1; display: flex; flex-direction: column; gap: 220px; padding-left: 5px;">
          <img class= "fragment fade-left" src="../../assets/images/qkv.png" width="60%" height="20%" alt="qkv">
          <img class= "fragment fade-left" src="../../assets/images/encodeur-decodeur.png" width="90%" height="40%" alt="encodeur-decodeur">
      </div>
    </div>
  </div>
</section>

Notes:
https://waylandzhang.github.io/en/transformer-architecture.html
Le modèle se compose de deux parties : l'encodeur et le décodeur. En général, les architectures basées uniquement sur l'encodeur sont performantes 
pour extraire des informations d'un texte pour des tâches telles que la classification et la régression, tandis que les modèles basés uniquement 
sur le décodeur se spécialisent dans la génération de texte. Par exemple, GPT, qui se concentre sur la génération de texte, 
appartient à la catégorie des modèles basés uniquement sur le décodeur.

##==##

# LLM

## Modèle d'embedding

<img src="../../assets/images/encodeur.png" width="50%" height="10%" alt="encoder">

Technique d'apprentissage automatique qui transforme des données complexes et de haute dimension en vecteur de nombres réels

<br></br>

### *Cas d'usage*
Idéal pour la classification de texte, l'analyse de sentiments, la recherche sémantique et la réponse à 
des questions précises où la compréhension fine du contexte est cruciale.

Exemple : les modèles basés sur l'architecture BERT 

Notes:
L'Encodeur (Encoder) : Lit et comprend le texte d'entrée.

##==##


# LLM
## Modèle dit génératif

<img src="../../assets/images/decodeur.png" width="50%" height="10%" alt="decoder">

Vu comme un moteur de génération
<br></br>

### *Cas d'usage*
Parfait pour la rédaction d'articles, les chatbots conversationnels, le résumé de textes, 
l'écriture de code et toute tâche nécessitant de produire de nouvelles séquences de mots.

Exemple : GPT

Notes:
Le Décodeur (Decoder) : Génère le texte de sortie (traduction, réponse, etc.).

##==##


<section>
<h1>Agent IA</h1>
  <p>Système qui utilise un grand modèle de langage (LLM) comme cerveau central pour raisonner, planifier et exécuter des tâches complexes de manière autonome.</p>
  <br></br>
  <div class="r-hstack">
    <img src="../../assets/images/agent-ia-exemple.png" width="80%" height="60%" alt="transformers">
  </div>
</section>
