# Learning Souls Game (LSG) - Branche de Développement

Bienvenue dans la branche de développement de Learning Souls Game (LSG), un jeu en Java 8 utilisant JavaFX. Ce jeu offre une expérience de jeu en PVE au tour par tour.

## Aperçu des spécificités de la branche de développement

La branche de développement est utilisée pour le développement actif du jeu. Elle contient les dernières fonctionnalités et améliorations en cours de développement. Les fonctionnalités et améliorations sont testées et validées avant d'être fusionnées dans la branche principale.

**Attention :** La branche de développement peut contenir des bugs et des fonctionnalités non terminées. Elle est destinée à être utilisée par des développeurs et des testeurs.

Pour une expérience de jeu plus stable, nous vous recommandons de télécharger la dernière version de la branche principale.

## Structure du dépôt

Le dépôt est structuré de la manière suivante :
- `src` : Contient le code source du jeu
    - `lsg` : Contient le code source du jeu
        - `armor` : Contient la classe `ArmorItem` et ses sous-classes
        - `bags` : Contient la classe `Bag` et ses sous-classes
        - `buffs` : Contient la classe `BuffItem` et ses sous-classes
            - `rings` : Contient la classe `Ring` et ses sous-classes
            - `talismans` : Contient la classe `Talisman` et ses sous-classes
        - `characters` : Contient la classe `Character` et ses sous-classes
        - `consumables` : Contient la classe `Consumable` et ses sous-classes
            - `drinks` : Contient la classe `Drink` et ses sous-classes
            - `food` : Contient la classe `Food` et ses sous-classes
            - `repair` : Contient la classe `RepairKit`
        - `exceptions` : Contient les classes d'exceptions
        - `graphics` : Contient les classes de gestion des graphismes 
            - `css` : Contient les fichiers CSS
            - `fonts` : Contient les fichiers de police
            - `images` : Contient les images
            - `panes` : Contient les classes de gestion des panneaux
            - `widgets` : Contient les classes de gestion des widgets
                - `characters` : Contient les classes de gestion des personnages
                    - `renderers` : Contient les classes de rendu des personnages
                    - `statbars` : Contient les classes de gestion des barres de statistiques
                - `skills` : Contient les classes de gestion des compétences
                - `texts` : Contient la classe `GameLabel`
        - `helpers` : Contient la classe `Dice`
        - `main` : Contient la classe `LearningSoulsGame` **Dépréciée**
        - `utils` : Contient la classe `Test` **Dépréciée** et la classe `Constants`
        - `weapons` : Contient la classe `Weapon` et ses sous-classes
        - `LearningSoulsGameApplication.java` : Classe principale de l'application
    - `META-INF` : Contient le fichier `MANIFEST.MF`
- `API` : Contient les classes de l'API
    - `lsg_api` : Contient les interfaces de l'API
        - `armors` : Contient l'interface `IArmorItem`
        - `bags` : Contient l'interface `IBag`
        - `buffs` : Contient l'interface `IBuffItem` et ces sous-interfaces `IRing` et `ITalismans`
        - `characters` : Contient l'interface `ICharacter` et ces sous-interfaces `IHero` et `IMonster`
        - `consumables` : Contient l'interface `ICollectible`, sa sous-interface `IConsumable` et ces sous-interfaces `IDrink`, `IFood` et `IRepairKit`
        - `weapons` : Contient l'interface `IWeapon`
        - `CharacterAPI.java` : Classe pour la gestion des personnages **Dépréciée**
        - `ConsoleAPI.java` : Classe pour la gestion des sorties console
    - `user_mods` : Contient les classes pour les mods utilisateur
        - `Mod.java` : Classe dont doivent hériter les mods utilisateur
        - `ModdedWindow.java` : Classe dont doivent hériter les fenêtres modifiées
        - `ModLoader.java` : Classe pour le chargement des mods utilisateur

**Attention :** Les classes dépréciées pourraient être supprimées dans les prochaines versions. Il est fortement déconseillé de les utiliser.

**Attention :** L'API est nécessaire au bon fonctionnement du jeu. Il est imperatif de l'inclure dans la compilation.
    
## Téléchargement, compilation et exécution du jeu

Pour pouvoir profiter des dernières fonctionnalités et améliorations en cours de développement, vous pouvez utiliser la version de développement du jeu.

**Attention :** L'exécution de la version de développement nécessite la compilation du code source. Vous devez donc être à l'aise avec l'utilisation du terminal de votre système d'exploitation.

### Prérequis

- [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) ou toute autre version prenant en charge JavaFX
- [Git](https://git-scm.com/downloads) pour cloner le dépôt
- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/other.html) ou tout autre IDE pour éditer le code source et compiler les fichiers .java en fichiers .class
- Un terminal pour exécuter les commandes
- Environ 50 Mo d'espace disque pour le code source et les dépendances
- Environ 50 Mo d'espace disque pour les fichiers de compilation
- Environ 50 Mo d'espace disque pour l'exécutables et les ressources du jeu

Créez un dossier pour le jeu et placez-vous dedans.

```bash
mkdir LearningSoulsGame
cd LearningSoulsGame
```

### Téléchargement

Pour télécharger la version de développement du jeu, vous devez cloner la branche de développement du dépôt Git.

```bash
git clone --branch lsg-devellopment https://github.com/Darkbriks/LearningSoulsGame.git
```

### Compilation en .class

Pour compiler le code source en fichiers .class, vous devez
- Ouvrir le projet dans votre IDE
- Compiler les fichiers .java en fichiers .class en utilisant les outils de votre IDE (par exemple, en utilisant la fonction "Build Project" dans IntelliJ IDEA)

Vous obtiendrez un dossier `out` (ou `bin` ou tout autre nom de dossier de sortie) contenant les fichiers `.class`.

### Compilation en .jar

Pour compiler les fichiers .class en un fichier .jar exécutable, vous devez
- Ouvrir un terminal
- Vous placer dans le dossier `out/production/` (ou equivalent)
- Exécuter la commande suivante

```bash
jar cvfm LearningSoulsGameDev.jar META-INF/MANIFEST.MF lsg lsg_api user_mods
```

Vous obtiendrez un fichier `LearningSoulsGameDev.jar` contenant les fichiers `.class` et les ressources du jeu.

### Exécution

Pour exécuter le jeu, vous devez
- Ouvrir un terminal
- Vous placer dans le dossier contenant le fichier `LearningSoulsGameDev.jar`
- Exécuter la commande suivante

```bash
java -jar LearningSoulsGameDev.jar
```

**Attention :** Si vous avez des problèmes pour exécuter le jeu, assurez-vous que vous utilisez bien Java 8 ou une version prenant en charge JavaFX.

## Rapport de bugs et demandes de fonctionnalités

En cours de construction