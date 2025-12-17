<!-- .slide: class="quote-slide full-center" -->
<span style="font-size: 6rem; font-weight: bold">Attention is all you need...</span>

[arXiv:1706.03762 [cs.CL]](https://arxiv.org/abs/1706.03762)
<!-- .element: class="credits" -->

##==##
<section>
  <h1>Les Transformers</h1>

  <div style="padding-bottom: 20px;">
    <h3>L'Attention est Tout ce Dont Vous Avez Besoin</h3>
    <p class="fragment fade-in" data-fragment-index="1">
      Le Transformer a révolutionné le traitement du langage naturel (NLP) en introduisant l'architecture basée uniquement sur les mécanismes d'attention.
    </p>
  </div>
  <div style="display: flex; justify-content: space-around; align-items: flex-start; margin-top: 10px; max-height: 70vh;">
    <div style="flex: 1; text-align: center; padding: 0 5px;">
      <h4 class="fragment fade-in" data-fragment-index="2">Architecture Globale</h4>
      <img 
        class="fragment fade-left" 
        data-fragment-index="3"
        src="./assets/images/attention_research_1.png" 
        alt="Architecture complète du Transformer" 
        style="max-width: 95%; max-height: 50vh; height: auto;"
      >
    </div>
    <div style="flex: 1; text-align: center; padding: 0 5px;">
      <h4 class="fragment fade-in" data-fragment-index="2">Mécanisme QKV</h4>
      <img 
        class="fragment fade-left" 
        data-fragment-index="4"
        src="./assets/images/qkv.png" 
        alt="Schéma Query, Key, Value" 
        style="max-width: 95%; max-height: 50vh; height: auto;"
      >
    </div>
    <div style="flex: 1; text-align: center; padding: 0 5px;">
      <h4 class="fragment fade-in" data-fragment-index="2">Explorateur Interactif</h4>
      <a 
        href="https://poloclub.github.io/transformer-explainer/" 
        target="_blank" 
        class="fragment fade-up" 
        data-fragment-index="5"
      >
        <img 
          src="./assets/images/transformer_explainer.png" 
          alt="Lien vers l'explicateur interactif"
          style="max-width: 95%; max-height: 50vh; height: auto;"
        >
      </a>
    </div>
  </div>
</section>

Notes:
https://waylandzhang.github.io/en/transformer-architecture.html
Le modèle se compose de deux parties : l'encodeur et le décodeur. En général, les architectures basées uniquement sur l'encodeur sont performantes 
pour extraire des informations d'un texte pour des tâches telles que la classification et la régression, tandis que les modèles basés uniquement 
sur le décodeur se spécialisent dans la génération de texte. Par exemple, GPT, qui se concentre sur la génération de texte, 
appartient à la catégorie des modèles basés uniquement sur le décodeur.
https://poloclub.github.io/transformer-explainer/

##==##

# LLM

## Modèle d'embedding
![](./assets/images/encodeur.png 'h-150 center')

Technique d'apprentissage automatique qui transforme des données complexes et de haute dimension en vecteur de nombres réels

![](./assets/images/spring-ai-embeddings.jpg 'h-300 center')


### *Cas d'usage*
Idéal pour la classification de texte, l'analyse de sentiments, la recherche sémantique et la réponse à 
des questions précises où la compréhension fine du contexte est cruciale.

Notes:
L'Encodeur (Encoder) : Lit et comprend le texte d'entrée.
Exemple : le modèle de langage BERT basé sur le transformeur, n'utilisant que sa partie « encodeur »

##==##


# LLM
## Modèle dit génératif

![](./assets/images/decodeur.png 'h-150 center')

Vu comme un moteur de génération
<br></br>


### *Cas d'usage*
Parfait pour la rédaction d'articles, les chatbots conversationnels, le résumé de textes, 
l'écriture de code et toute tâche nécessitant de produire de nouvelles séquences de mots.

Exemple : GPT

Notes:
Le Décodeur (Decoder) : Génère le texte de sortie (traduction, réponse, etc.).

##==##
<!-- .slide: class="quote-slide full-center" -->
<span style="font-size: 6rem; font-weight: bold">Un agent ça agit...</span>

##==##

# Agent IA


![](./assets/images/agent-ia-exemple.png 'h-300 center')

<br></br>
Système qui utilise un grand modèle de langage (LLM) comme cerveau central pour raisonner, 
planifier et exécuter des tâches complexes de manière autonome.


Notes:
Prenons l'exemple d'une réunion professionnelle très importante sur le choix du prochain team building
Plusieurs interlocuteurs dans la salle, chacun y va de son commentaire. Notre agent doit être capable : 
- de déclencher l'enregistrement audio de la réunion
- transcrire en texte avec l'intervention de chacun des participants
- puis faire une synthèse (ce sera le 18 décembre à 18h à l'éléphant)
- prévenir les membres de l'équipe par envoie d'un mail
- enregistrer le contenu et le stocker sur le drive pour preuve
- et enfin planifier dans l'agenda google
