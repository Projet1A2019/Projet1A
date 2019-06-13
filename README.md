# Ensi'Lost

**Champion Melvin** - **Bruger Marie-Camille**- **Cazaubon Colin**  - **Chaieb Adam**  - **Pavué Louis** - **Schummer Hugo** - **Sottani Gaëlle** 

## Introduction

​	Le but de notre projet est de réaliser une application permettant de trouver son chemin au sein de l'école. La réalité augmentée a été choisie pour permettre à l'utilisateur de se repérer géographiquement et de renseigner sa position à l'application de manière ludique.  

​	Un plan interactif, indiquant le chemin le plus court, a donc été mis en place. 

​	De plus, afin de permettre à l'utiliateur de s'orienter, des informations sur les salles ainsi que les bureaux des enseignants sont affichées en 3D. 

## Réalisations

### Réalité augmentée

​	Dans cette première partie, l'objectif fût d'identifier des QR Code dans le but de récupérer la position de l'utilisateur, pour ensuite lui afficher des informations en réalité augmentée sur sa position. Ces informations sont representées sous la forme d'objets 3D, qui peuvent être des textes, pour le nom des salles ou des enseignants, ou des images pour l'emploi du temps associé à la salle. 

​	Google met à disposition un framework, l'AR Core, afin de faciliter la mise en place d'une application de réalié augmentée. Ce dernier a été utilisé pour reconnaître des QR Code disposés devant chaque salle du 3ème étage, en les traitant comme de simples images. Une fois reconnus, le même framework permet d'afficher les objets 3D sur les QR Codes. Selon les salles, plusieurs objets peuvent être affichés grâce au même QR Code.

​	A certains endroits, plutôt que d'afficher le nom de certaines salles ou de certains lieux, des activités telles qu'un quiz sont pris en charge par l'application. De ce fait, si l'utilisateur scanne un QR Code se trouvant devant des toilettes, ou bien au niveau de la zone de détente (les canapés), la caméra est coupée, et le quizz se lance. 



### GPS

​	Pour permettre à une personne ne connaissant pas l'emplacement des différentes salles de se retrouver dans l'école, nous avons créé une fonction permettant d'afficher le trajet à suivre par l'utilisateur. Pour indiquer sa position et se retrouver facilement, l'utilisateur n'a qu'à scanner le QR Code le plus proche de lui pour mettre à jour sa position dans l'application. Il peut recherher la salle dans laquelle il souhaite se rendre en la selectionnant dans une liste déroulante. 

​	Il peut également choisir d'affiner sa recherche en fonction d'une catégorie (Salle, Enseignant, ...). De plus, une assistance vocale permet de lire les indications de directions en appuyant sur un bouton. 

​	Grâce à une autre fonction renvoyant une liste des salles devant lesquelles l'utilisateur passe durant son trajet, un plan en 2D à été créer. Celui-ci contient la position où le dernier QR Code à été scané, l'endroit où l'utilisateur souhaite se rendre et le chemin qu'il doit parcourir pour arriver à sa destination. Ce plan s'actualise tout seul à chaque nouveau trajet demandé.

​	Un petit plan vierge, sans trajet visible apparaît également lorsque la réalité augmentée est lancée. Celui-ci permet juste de se situer dans l'étage et de prendre connaissance de la disposition des salles, sans afficher de chemin. Ce plan peut être agrandi lorsque l'on clique dessus grâce à une animation.



### Plan

​	Pour faciliter La lisibilité à l'utilisateur, nous avons créé un plan en 2D du troisième étage contenant les informations nominatives des salles.
	Nous avons ensuite crée une onglet dans le menu pour accéder directement à la carte de l'étage actualisée avec le chemin à suivre pour se rendre à la destination.
	Pour éviter d'avoir à revenir dans cet onglet systématiquement, 
nous avons positionné un bouton contenant en fond le plan dans l'onglet réalité augmentée. 
	Une fois celui-ci pressé, le plan s'aggrandi pour occuper l'écran et peut à nouveau être diminué par un autre appui

.

### Audiodescription

​	Une fois que le trajet a été choisi par l’utilisateur, cela génère un texte qui décrit le chemin à parcourir. L’utilisateur pourra ensuite lire ce trajet en audiodescription grâce au bouton ‘’ECOUTER’’ et pourra soit stopper la lecture grâce au bouton ‘’STOP’’ ou attendre la fin de la lecture.



### Gamification

​	Pour les jeux, nous avons créé un questionnaire à choix multiples à côté de chaque endroit de repos tel que les canapés, le distributeur de café ou les toilettes.
Dès que l’on scanne les QR Codes, une nouvelle activité dans l’application se lance et créé une interface pour débuter le quiz.
	À chaque fois que l’on répond à une question, si la réponse est correcte, le score augmente et une nouvelle question apparaît, sinon la partie est perdue et on peut soit quitter le jeu soit le relancer.



### Navigation

​	Pour accéder facilement aux différentes fonctionnalités du programme, 
nous avons crée un menu permettant de lancer les activités correspondants aux sous programmes développés(Accueil, Plan, Gps, Réalité augmentée).





​	