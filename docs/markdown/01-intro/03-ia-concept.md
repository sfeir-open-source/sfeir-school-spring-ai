

# Prompt

Le prompt désigne l'instruction, la question ou l'ensemble de données que l'on fournit à une intelligence 
artificielle pour solliciter une réponse ou une action de sa part

La structure d'un prompt se compose de plusieurs éléments : 

* un rôle
* une instruction ou une tâche
* un contexte
* les données d'entrée
* le format de sortie

L'art de concevoir des prompts efficaces est appelé "prompt engineering"


##==##


# Tokenisation

Processus consistant à découper un texte en plus petit morceau appelés "tokens". Etape fondamentale 
pour toute analyse par un LLM. 


"Quel temps fait-il demain ?" découpée en
```["Quel", "temps", "fait-il", "demain", "?"]```
<!-- .element: class="admonition example" -->

*La tokenisation est la traduction du langage humain en un format numérique compréhensible par la machine*
<br></br>

Les modèles ont des limites maximum de token admissibles par prompt
<!-- .element: class="admonition warning" -->

##==##

# Paramétrage

### Température
La température est un paramètre qui contrôle le degré de hasard et de créativité des réponses du modèle
<br></br>
### Top-k
Méthode de filtrage qui force un modèle de langage à choisir le prochain mot uniquement parmi les K mots les plus probables
<br></br>
### Top-p
Méthode de filtrage : Au lieu de fixer un nombre de mots arbitraire (comme le "Top-k" qui prendrait toujours les *K* meilleurs), 
le Top-p fonctionne par probabilité cumulée
<br></br>

Notes:
La température est un paramètre qui contrôle le degré de hasard et de créativité des réponses du modèle
* Température basse (proche de 0) -> modèle plus déterministe et prévisible.
* Température élevée (supérieur à 1) -> modèle prend plus de risques et peut choisir les mots moins probables.

Top-k
Méthode de filtrage qui force un modèle de langage à choisir le prochain mot uniquement parmi les K mots les plus probables
* Un K faible (ex=5) choisira le mot parmi les 5 mots les plus probable préalablement sélectionné. Cela peut rendre le modèle plus prévisible et sûr mais moins créatif.
* Un K élevé (ex=50) donnera plus de liberté au modèle

Top-p
filtrage plus intelligent
Si vous réglez le Top-p sur 0.90 (90%), le modèle va :

Trier les mots du plus probable au moins probable.

Additionner leurs pourcentages un par un.

S'arrêter dès que la somme atteint 90%.

Choisir le mot suivant uniquement parmi cette sélection restreinte.
##==##


# Entraînement et apprentissage

### Pre-training
un modèle est toujours entrainé initialement sur une immense quantité de données généralistes, on parle de pre-training
<br></br>
### Fine-tuning
Le fine-tuning consiste à ré-entrainer le modèle généraliste sur un domaine spécifique
<br></br>
### RLHF (Reinforcement Learning from Human Feedback)
Apprentissage par renforcement à partir du retour humain.

Notes: 
RLHF : Consiste à classer les réponses d'une IA par un humain. Le LLM
reçoit un "score" ou une "récompense". Il ajuste alors ses propres paramètres pour essayer de maximiser ce score.
