<!-- .slide: -->

<section>
  <h1>Les transformers</h1>
  <p><a href="https://proceedings.neurips.cc/paper_files/paper/2017/file/3f5ee243547dee91fbd053c1c4a845aa-Paper.pdf">Attention is all you need</a></p>
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


##==##
# LLM

## Modèle d'embedding

<img src="../../assets/images/encodeur.png" width="50%" height="10%" alt="transformers">

Technique d'apprentissage automatique qui transforme des données complexes et de haute dimension en vecteur de nombres réels

<br></br>

### *Cas d'usage*
Idéal pour la classification de texte, l'analyse de sentiments, la recherche sémantique et la réponse à 
des questions précises où la compréhension fine du contexte est cruciale.

Exemple : les modèles basés sur l'architecture BERT 

##==##

# LLM
## Modèle dit génératif

<img src="../../assets/images/decodeur.png" width="50%" height="10%" alt="transformers">

Vu comme un moteur de génération
<br></br>

### *Cas d'usage*
Parfait pour la rédaction d'articles, les chatbots conversationnels, le résumé de textes, 
l'écriture de code et toute tâche nécessitant de produire de nouvelles séquences de mots.

Exemple : GPT

##==##

<section>
<h1>Agent IA</h1>
  <p>Système qui utilise un grand modèle de langage (LLM) comme cerveau central pour raisonner, planifier et exécuter des tâches complexes de manière autonome.</p>
  <br></br>
  <div class="r-hstack">
    <img src="../../assets/images/agent-ia-exemple.png" width="80%" height="60%" alt="transformers">
  </div>
</section>
