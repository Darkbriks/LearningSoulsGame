# Changelog

Tous les changements notables concernant le cœur du jeu et l'aspect graphique seront documentés dans ce fichier.

Le format est basé sur [Keep a Changelog](https://keepachangelog.com/fr/1.1.0/),
et ce projet adhère à [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Ajout de tooltips pour les compétences du héros
  - Modification du constructeur de la classe `SkillTrigger` pour ajouter un paramètre `Tooltip`
  - Ajout d'un champ `tooltip` dans la classe `SkillTrigger`, ainsi que les méthodes `getTooltip` et `setTooltip`
  - Modification de l'appel au super-constructeur dans la classe `ConsumableTrigger` pour ajouter un paramètre `Tooltip`
  - Modification de la méthode `init` de la classe `SkillBar` pour ajouter des tooltips initialisés a `null`
  - Modification de la méthode `createSkillBar` de la classe `LearningSoulsGameApplication` pour ajouter des tooltips aux compétences du héros
- CLasse `TextPart` pour stocker un morceau de texte qui peut être intérprété comme une succession de méthodes
    - Méthode `get` pour obtenir le texte (interprété si spécifié dans le constructeur)
    - Méthode `toXML` pour obtenir le texte sous forme de balise XML (renvoie un `org.w3c.dom.Node.Element`)
    - Méthode statique `fromXML` pour créer un `TextPart` à partir d'une balise XML (prend un `org.w3c.dom.Node` en paramètre)
- Classe `InterpretableText` pour stocker une phrase composée de `TextPart` dans plusieurs langues
    - Méthode `toString` pour obtenir le texte (si la langue n'est pas précisé, en anglais)
    - Méthode `toXML` pour obtenir le texte sous forme de balise XML (prend un `org.w3c.dom.Node.Element.Document` en paramétre et renvoie un `org.w3c.dom.Node.Element`)
    - Méthode statique `fromXML` pour créer un `InterpretableText` à partir d'une balise XML (prend un `org.w3c.dom.Node` en paramètre)
- Classe `XMLFactory` pour lire et écritre des fichiers XML
    - Méthode statique `preloadAll` pour charger tous les fichiers XML dans une `HashMap`
    - Méthode statique `getText` pour obtenir un `InterpretableText` à partir d'un identifiant
    - Méthode statique `createXML` pour créer un fichier XML à partir d'une liste d'id et de `InterpretableText`
    - TODO: other methods
    - Classe `XMLExceptionFile` pour créer le fichier XML contenant les textes des exceptions du jeu dans 4 langues (EN, FR, ES, DE)
    - Classe `XMLSkillTooltipFile` pour créer le fichier XML contenant les textes des tooltips des compétences du héros dans 4 langues (EN, FR, ES, DE)
    - Classe `XMLTemplateFile` pour créer un fichier XML de template (qui n'est pas exploité, uniquement pour avoir la structure d'un fichier XML)
- ComboBox sur l'écran de création du personnage pour choisir la langue du jeu

### Changed

- À chaque fois que le joueur tue un monstre, il obtient une nouvelle épée, dont les stats sont basée sur le score du joueur
- Le texte de toutes les exceptions du jeu est maintenant stocké dans un fichier XML
  - Les textes des exceptions sont stockés dans 4 langues (EN, FR, ES, DE)

### Fixed

- Ajout d'un test pour vérifier que l'action n'est pas `null` dans la méthode `trigger` de la classe `SkillTrigger`
  - L'absence de cette vérification provoquait un message d'erreur dans la console lorsqu'une compétence non initialisée était utilisée
- Correction du texte de l'exception `WeaponBrokenException` dans la classe `Weapon`
  - Le texte de l'exception était incorrect, car il affichait la représentation de l'arme au lieu de son nom

### Removed

- Classe `Test` supprimée
  - Cette classe avait été dépréciée dans la version 0.3.0 du jeu.
- Méthode `getHero` de la classe `LearningSoulsGameApplication` supprimée
  - Cette méthode avait été dépréciée dans la version 0.3.0 du jeu.
- Méthode `getZombie` de la classe `LearningSoulsGameApplication` supprimée
  - Cette méthode avait été dépréciée dans la version 0.3.0 du jeu.
- Singleton de la classe `LearningSoulsGameApplication` supprimé
  - Cette fonctionnalité avait été dépréciée dans la version 0.3.0 du jeu.

## [0.3.1] - 2024-02-24

### Added

- Support quasi-complet de la version 0.3.0 de l'API de modding
  - Pour cela, de nombreux appels a `ModLoader.invoke` ont été ajoutés dans le cœur du jeu
  - Seules les méthodes `endTurn`, `heroBeginTurn` et `heroEndTurn` ne sont pas encore prises en charge
  - Pour la liste complète des changements, veuillez consulter le [commit](https://github.com/Darkbriks/LearningSoulsGame/commit/379f8c2b7bdbcb055ca0f033976665ea0c4fa7e0) correspondant
- `README.MD` ajouté pour la branche `lsg-development`

### Fixed

- Correction d'un bug mâjeur qui empêchait le jeu de localiser les ressources graphiques lorsqu'il était lancé depuis un fichier JAR
  - Ce bug était dû à l'utilisation de `File` pour localiser les ressources graphiques, ce qui ne fonctionne pas à l'intérieur d'un fichier JAR
  - Pour corriger ce bug, une méthode `loadInJar` a été ajoutée à la classe `ImageFactory` pour charger les ressources graphiques en utilisant `JarFile` et `JarEntry`
  - Cette méthode est appelée à la place de `load` si le chemin de la classe `ImageFactory` contient `jar:`
  - Pour consulter tous les details de ce fix, veuillez consulter le [commit](https://github.com/Darkbriks/LearningSoulsGame/commit/970fd719473caa64642ba3da84e690429ea84001) correspondant

## [0.3.0] - 2024-02-20

### Changed

- Le cœur du jeu prend entièrement en charge la version 0.2.0 de l'API de modding
  - Pour cela, de nombreuses classes héritent des interfaces de l'API de modding. De plus, toutes les occurrences de ces classes dans le cœur du jeu ont été remplacées par interface correspondante
  - Le jeu reste en partie compatible avec les versions précédentes de l'API de modding, mais des comportements inattendus peuvent survenir
  - Pour la liste complète des changements, veuillez consulter le [commit](https://github.com/Darkbriks/LearningSoulsGame/commit/f2509a95813f08dc3605147916daa763ea0cb9bf) correspondant

### Deprecated

- Les parties de la classe `LearningSoulsGameApplication` qui sont liées au singleton sont dépréciées
  - Ces parties ne sont plus nécessaires avec la version 0.2.0 de l'API de modding
- La méthode `getHero` de la classe `LearningSoulsGameApplication` est dépréciée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding
- La méthode `getZombie` de la classe `LearningSoulsGameApplication` est dépréciée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding
- La classe `Test` est dépréciée
  - Cette classe n'est plus utilisée, car le jeu est suffisamment avancé pour ne plus nécessiter un programme de test
- La classe `LearningSoulsGame` est dépréciée
  - Cette classe n'est plus utilisée, car la liaison entre le cœur du jeu et les graphismes est entièrement fonctionnelle, il n'est donc plus nécessaire de lancer le jeu dans la console

### Removed

- Package `lsg.consumables.menu` et toutes ses classes
  - Ces classes sont supprimées, car elles étaient utilisées à des fins de tests et ne sont plus nécessaires
- La méthode `getHeroRenderer` de la classe `LearningSoulsGameApplication` est supprimée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding et n'a jamais été utilisée
- La méthode `getZombieRenderer` de la classe `LearningSoulsGameApplication` est supprimée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding et n'a jamais été utilisée
- La méthode `getHeroCanPlay` de la classe `LearningSoulsGameApplication` est supprimée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding et n'a jamais été utilisée
- La méthode `getScore` de la classe `LearningSoulsGameApplication` est supprimée
  - Cette méthode n'est plus nécessaire avec la version 0.2.0 de l'API de modding et n'a jamais été utilisée

## [0.2.2] - 2024-02-19

### Added

- Getter pour les champs `heroRenderer`, `zombieRenderer`, `heroCanPlay` et `score` dans la classe `LearningSoulsGameApplication`

## [0.2.1] - 2024-02-19

### Added

- Getter pour les champs `hero` et `zombie` dans la classe `LearningSoulsGameApplication`
- Appel de la méthode `start` de tous les mods une fois qu'un hero et un zombie sont créés dans la méthode `play` de la classe `LearningSoulsGameApplication`

### Changed

- Les sorties consoles de la classe `ModLoader` sont maintenant gérées par la classe `ConsoleAPI
- La classe `LearningSoulsGameApplication` devient un Singleton pour préparer la version 0.1.2 de l'API de modding
- La Méthode `start` de la classe `LearningSoulsGameApplication` initialise l'instance de `LearningSoulsGameApplication`
- Le message de game over passe de `Game Over` à `YOU DIED`

### Fixed

- Correction de la classe css `LSGFont.css` qui provoquait un averstissement dans la console lors du lancement du jeu

## [0.2.0] - 2024-02-18

### Added

- Prise en charge de la version 0.1.0 de l'API de modding
- Chargement des mods dans la méthode `start` de la classe `LearningSoulsGameApplication`
- Ajout de `stage.setOnCloseRequest` pour décharger les mods et quitter l'application dans la méthode `start` de la classe `LearningSoulsGameApplication`
- Appel de la méthode `awake` de tous les mods a la fin de la méthode `start` de la classe `LearningSoulsGameApplication`

### Changed

- La méthode `gameOver` de la classe `LearningSoulsGameApplication` prend en charge le déchargement des mods
- Amélioration de l'écran de game over
- Passage de `0.1.7` à `0.2.0` pour la constante `GAME_VERSION ` dans la classe `Constants`

## [0.1.7] - 2024-02-17

### Added

- Mention `stable` et `dev` à la fin de la version du jeu pour distinguer les versions stables et de développement

### Changed

- Tous les textes de `LearningSoulsGameApplication` sont en anglais
- Les statbars ont légèrement été remontées pour être plus proches du haut de l'écran
- Passage de `0.1.6` à `0.1.7` pour la constante `GAME_VERSION ` dans la classe `Constants`

### Removed

- Commentaires inutiles dans la classe `LearningSoulsGameApplication`

## [0.1.6] - 2024-02-17

### Added

- Méthode `lerp` ne prenant pas de paramètres `v1` dans la classe `Character`
- `Object.requireNonNull` pour le retour de la méthode `getStyleSheet` de la classe `CSSFactory`
- `Object.requireNonNull` dans la méthode `load` de la classe `ImageFactory`

### Changed

- `items` devient un champ `final` dans la classe `Bag`
- `buff`, `start`, `end` deviennent des champs `final` dans la classe `Talisman`
- `armor` devient un champ `final` dans la classe `Hero`
- `name`, `stat`, `isEmpty` deviennent des champs `final` dans la classe `Consumable`
- `menu` devient un champ `final` dans la classe `MenuBestOfV1`
- `menu` devient un champ `final` dans la classe `MenuBestOfV2`
- `CSS_DIR` devient un champ `final` dans la classe `CSSFactory`
- `sprites` devient un champ `final` dans la classe `ImageFactory`
- `score` devient un champ `final` dans la classe `HUDPane`
- `label` devient un champ `final` dans la classe `MessagePane`
- `scene`, `title` deviennent des champs `final` dans la classe `TitlePane`
- `groundY`, `parent` deviennent des champs `final` dans la classe `AnimationPane`
- `frames `, `animationTl `, `gotoTl ` deviennent des champs `final` dans la classe `CharacterRenderer`
- `DEFAULT_BINDING`, `consumableTrigger` deviennent des champs `final` dans la classe `SkillBar`
- `view`, `desaturate` deviennent des champs `final` dans la classe `SkillTrigger`
- `random` devient un champ `final` dans la classe `Dice`
- `name`, `minDamage`, `maxDamage`, `stamCost` deviennent des champs `final` dans la classe `Weapon`
- Utilisation de StringBuilder pour la méthode `toString` dans la classe `Consumable` en remplacement `String.format`
- Utilisation de StringBuilder pour la méthode `toString` dans la classe `MenuBestOfV1` en remplacement `String.format`
- Utilisation de StringBuilder pour la méthode `toString` dans la classe `MenuBestOfV2` en remplacement `String.format`
- Utilisation de StringBuilder pour la méthode `toString` dans la classe `MenuBestOfV3` en remplacement `String.format`
- Utilisation de StringBuilder pour la méthode `toString` dans la classe `MenuBestOfV4` en remplacement `String.format`
- Utilisation de `lerp(v2, t)` au lieu de `lerp(v1, v2, t)` dans les méthodes `attackWith` et `getHitWith` de la classe `Character`
- Réunion des catchs dans les méthodes `preloadAll` et `getSprites` de la classe `ImageFactory`
- Documentation du constructeur de la classe `AnimationPane`
- Documentation de la méthode `setConsumable` de la classe `ConsumableTrigger`
- Déclaration des listes de sprites dans la classe `CharacterRenderer` en utilisant le style java au lieu du style C
- Cast des valeursde `kf` en `double` dans les méthodes de la classe `CharacterRenderer` pour éviter les `Warnings`
- Passage de `0.1.5` à `0.1.6` pour la constante `GAME_VERSION ` dans la classe `Constants`

### Removed

- Méthode de test `main` dans la classe `Bag`
- Méthode de test `main` dans la classe `RingOfDeath`
- Méthode de test `main` dans la classe `RingOfSwords`
- Modificateur inutile `public` dans l'interface `Collectible` pour la méthode `getWeight`
- Modificateur inutile `public` dans l'interface `SkillAction` pour la méthode `execute`
- Imports inutiles dans la classe `RingOfDeath`
- Imports inutiles dans la classe `RingOfSwords`
- Imports inutiles dans la classe `Zombie`
- Imports inutiles dans la classe `ImageFactory`
- Imports inutiles dans la classe `AnimationPane`
- Imports inutiles dans la classe `MessagePane`
- Imports inutiles dans la classe `TitlePane`
- Imports inutiles dans la classe `LearningSoulsGame`
- Imports inutiles dans la classe `Test`
- Champ `name` dupliqué dans la classe `Character` dans la méthode `pickUp` lors de l'appel à `System.out.printf`
- Commentaires inutiles dans la classe `Statbar`
- Commentaires inutiles dans la classe `LearningSoulsGame`

## [0.1.5] - 2024-02-17

### Added

- Taille dynamique pour le nom du héros

### Changed

- Amélioration de l'écran de game over
- La méthode `start` de la classe `LearningSoulsGameApplication` ne peut lancer d'exceptions
- `heroCanPlay` devient un champ final dans la classe `LearningSoulsGameApplication`
- `score` devient un champ final dans la classe `LearningSoulsGameApplication`
- `name` devient un champ final dans la classe `Statbar`
- `lifeBar` devient un champ final dans la classe `Statbar`
- `staminaBar` devient un champ final dans la classe `Statbar`
- `rightPane` devient un champ final dans la classe `LearningSoulsGameApplication`
- Passage de `0.1.4` à `0.1.5` pour la constante `GAME_VERSION ` dans la classe `Constants`

### Removed

- Méthode `test` dans la classe `LearningSoulsGameApplication`
- Commentaires de debug dans la classe `HUDPane`
- Constantes inutilisées dans la classe `Constants` (`CHARACTER_NAME_STAT_STRING`, `CHARACTER_WEAPON_STAT_STRING` et `WEAPON_NAME_STAT_STRING`)

## [0.1.4] - 2024-02-17

### Added

- Numéro de version dans l'écran de titre
- Animation de fade-out pour le titre du jeu
- Méthode `main` dans la classe `LearningSoulsGameApplication` pour lancer le jeu
- Nom de héros `Gregooninator` par défaut si aucun nom n'est donné ou si le nom donné est vide

### Fixed

- Nom de la méthode `characterAttack` dans la classe `LearningSoulsGameApplicatio` corrigé
- Condition de déclanchement de l'animation `die` dans la classe `LearningSoulsGameApplication` corrigée

### Changed

- Amélioration de l'écran de game over

### Removed

- Messages de debug de la classe `LearningSoulsGameApplication` quand une touche est appuyée
- Messages de debug de la classe `LearningSoulsGameApplication` quand le héros est créé