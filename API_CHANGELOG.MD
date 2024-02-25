# Changelog

Tous les changements notables concernant l'API du jeu seront documentés dans ce fichier.

Le format est basé sur [Keep a Changelog](https://keepachangelog.com/fr/1.1.0/),
et ce projet adhère à [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Removed

- Messages de debug oubliés dans les méthodes de la classe `Mod`

## [0.2.2] - 2024-02-22

### Added

- Diverses méthodes dans la classe `Mod` pour gérer les événements du jeu. Le déclenchement de ces méthodes nessécite au minimum la version 0.3.1 du jeu
  - Méthode `beginTurn` dans la classe `Mod` qui est déclenchée au début du tour
  - Méthode `endTurn` dans la classe `Mod` qui n'est pas encore déclenchée
  - Méthode `heroBeginAttack` dans la classe `Mod` qui est déclenchée au début de l'attaque du héros
  - Méthode `heroFinishAttack` dans la classe `Mod` qui est déclenchée à la fin de l'attaque du héros
  - Méthode `heroBeginRecuperate` dans la classe `Mod` qui est déclenchée au début de la récupération du héros
  - Méthode `heroFinishRecuperate` dans la classe `Mod` qui est déclenchée à la fin de la récupération du héros
  - Méthode `heroBeginConsume` dans la classe `Mod` qui est déclenchée au début de la consommation du héros
  - Méthode `heroFinishConsume` dans la classe `Mod` qui est déclenchée à la fin de la consommation du héros
  - Méthode `heroBeginTurn` dans la classe `Mod` qui n'est pas encore déclenchée
  - Méthode `heroEndTurn` dans la classe `Mod` qui n'est pas encore déclenchée
  - Méthode `heroDie` dans la classe `Mod` qui est déclenchée à la mort du héros
  - Méthode `monsterBeginAttack` dans la classe `Mod` qui est déclenchée au début de l'attaque du monstre
  - Méthode `monsterFinishAttack` dans la classe `Mod` qui est déclenchée à la fin de l'attaque du monstre
  - Méthode `monsterBeginTurn` dans la classe `Mod` qui est déclenchée au début du tour du monstre, avant l'action du monstre
  - Méthode `monsterEndTurn` dans la classe `Mod` qui est déclenchée à la fin du tour du monstre, après l'action du monstre
  - Méthode `monsterDie` dans la classe `Mod` qui est déclenchée à la mort du monstre
  - Méthode `newMonsterCreate` dans la classe `Mod` qui est déclenchée à la création d'un nouveau monstre

### Removed

- Méthode `earlyUpdate` dans la classe `Mod` qui est remplacée par la méthode `beginTurn`
- Méthode `update` dans la classe `Mod` qui est remplacée par plusieurs méthodes permettant de gérer de manière plus précise les événements du jeu
- Méthode `lateUpdate` dans la classe `Mod` qui est remplacée par la méthode `endTurn`
- Méthode `destroy` dans la classe `Mod` qui est redondante avec la méthode `onUnloaded`

## [0.2.1] - 2024-02-20

### Added

- Classe abstraite `ModdedWindow` qui permet au mods de créer des fenêtres supplémentaires en utilisant `swing`

### Changed

- Mise à jour de la documentation privée (paragraphes compilation)
- Suppression d'un espace inutile dans toutes les méthodes d'écriture de la classe `ConsoleAPI` entre le préfixe et le message
- Le champ `id` de la classe `Mod` est maintenant final
- Méthode `setWIndow` dans la classe `Mod` pour permettre aux mods en ayant besoin de créer et utiliser une fenêtre supplémentaire
- Méthode `load` dans la classe `ModLoader` prend en charge les mods qui utilisent une fenêtre supplémentaire

## [0.2.0] - 2024-02-20

### Added
- Interface `IArmorItem` qui permet de gérer les armures
  - Méthode `getName` qui permet de récupérer le nom de l'armure
  - Méthode `getValue` qui permet de récupérer la valeur de l'armure
  - Méthode `toString` qui permet de récupérer une représentation textuelle de l'armure
- Interface `IBag` qui permet de gérer les sacs
  - Méthode `getCapacity` qui permet de récupérer la capacité du sac
  - Méthode `getWeight` qui permet de récupérer le poids du sac
  - Méthode `getItems` qui permet de récupérer les objets contenus dans le sac
  - Méthode `push` qui permet d'ajouter un objet de type `ICollectible` dans le sac
  - Méthode `pop` qui permet de retirer un objet de type `ICollectible` du sac
  - Méthode `contains` qui permet de vérifier si un objet de type `ICollectible` est contenu dans le sac
  - Méthode statique `transfer` qui permet de transférer des objets de type `ICollectible` d'un sac à un autre
  - Méthode `toString` qui permet de récupérer une représentation textuelle du sac
- Interface `IBuffItem` qui permet de gérer les buffs. Cette interface hérite de `ICollectible`
  - Méthode `getName` qui permet de récupérer le nom du buff
  - Méthode `computeValue` qui permet de calculer la valeur du buff
  - Méthode `toString` qui permet de récupérer une représentation textuelle du buff
- Interface `IRing` qui permet de gérer les anneaux. Cette interface hérite de `IBuffItem`
  - Méthode `getHero` qui permet de récupérer le héros portant l'anneau
  - Méthode `setHero` qui permet de définir le héros portant l'anneau
- Interface `ITalisman` qui permet de gérer les talismans. Cette interface hérite de `IBuffItem`
- Interface `ICharacter` qui permet de gérer les personnages
  - Méthode statique `addCharacter` qui permet d'ajouter un personnage à la liste des personnages contenu dans l'interface
  - Méthode statique `removeCharacter` qui permet de retirer un personnage de la liste des personnages contenu dans l'interface
  - Méthode statique `getCharacter` qui permet de récupérer un personnage de la liste des personnages contenu dans l'interface
  - Méthode `getName` qui permet de récupérer le nom du personnage
  - Méthode `getLife` qui permet de récupérer la vie du personnage
  - Méthode `getMaxLife` qui permet de récupérer la vie maximale du personnage
  - Méthode `getStamina` qui permet de récupérer la stamina du personnage
  - Méthode `getMaxStamina` qui permet de récupérer la stamina maximale du personnage
  - Méthode `lifeRateProperty` qui permet de récupérer le taux de vie du personnage
  - Méthode `staminaRateProperty` qui permet de récupérer le taux de stamina du personnage
  - Méthode `getWeapon` qui permet de récupérer l'arme du personnage
  - Méthode `getBuffInventory` qui permet de récupérer l'inventaire de buffs du personnage
  - Méthode `getBuffItem` qui permet de récupérer le buff d'index `i` de l'inventaire de buffs du personnage
  - Méthode `getConsumable` qui permet de récupérer l'objet consommable équipé du personnage
  - Méthode `getBagCapacity` qui permet de récupérer la capacité du sac du personnage
  - Méthode `getBagWeight` qui permet de récupérer le poids du sac du personnage
  - Méthode `getBagItems` qui permet de récupérer les objets contenus dans le sac du personnage
  - Méthode `getBag` qui permet de récupérer le sac du personnage
  - Méthode `setMaxLife` qui permet de définir la vie maximale du personnage
  - Méthode `setMaxStamina` qui permet de définir la stamina maximale du personnage
  - Méthode `setStamina` qui permet de définir la stamina du personnage
  - Méthode `setWeapon` qui permet de définir l'arme du personnage
  - Méthode `setBuffItem` qui permet de définir le buff d'index `i` de l'inventaire de buffs du personnage
  - Méthode `setConsumable` qui permet de définir l'objet consommable équipé du personnage
  - Méthode `setBag` qui permet de définir le sac du personnage. Retourne l'ancien sac si existant
  - Méthode `isAlive` qui permet de vérifier si le personnage est en vie
  - Méthode `attack` qui permet au personnage d'attaquer un autre personnage
  - Méthode `getHitWith` qui permet de gérer les dégâts reçus par le personnage
  - Méthode `use` qui permet au personnage d'utiliser un objet consommable
  - Méthode `consume` qui permet au personnage de consommer l'objet consommable équipé
  - Méthode `pickUp` qui permet au personnage de ramasser un objet de type `ICollectible`
  - Méthode `pullOut` qui permet au personnage de retirer un objet de type `ICollectible` de son sac
  - Méthode `printBag` qui permet au personnage d'afficher son sac
  - Méthode `equip` qui permet au personnage d'équiper une arme contenue dans son sac
  - Méthode `equip` qui permet au personnage d'équiper un objet consommable contenu dans son sac
  - Méthode `fastDrink` qui permet au personnage de boire le premier objet consommable trouvé dans son sac
  - Méthode `fastEat` qui permet au personnage de manger le premier objet consommable trouvé dans son sac
  - Méthode `fastRepair` qui permet au personnage d'utiliser le premier objet de réparation trouvé dans son sac
  - Méthode `printStats` qui permet au personnage d'afficher ses statistiques
  - Méthode `toString` qui permet de récupérer une représentation textuelle du personnage
- Interface `IHero` qui permet de gérer les héros. Cette interface hérite de `ICharacter`
  - Méthode `getLifeRegen` qui permet de récupérer la régénération de vie du héros
  - Méthode `getStamRegen` qui permet de récupérer la régénération de stamina du héros
  - Méthode `getArmorItems` qui permet de récupérer les pièces d'armure équipées du héros
  - Méthode `getTotalArmor` qui permet de récupérer la valeur totale de l'armure du héros
  - Méthode `getBuffInventory` qui permet de récupérer l'inventaire de buffs du héros
  - Méthode `getBuffItem` qui permet de récupérer le buff d'index `i` de l'inventaire de buffs du héros
  - Méthode `getTotalBuff` qui permet de récupérer la valeur totale des buffs du héros
  - Méthode `setLifeRegen` qui permet de définir la régénération de vie du héros
  - Méthode `setStamRegen` qui permet de définir la régénération de stamina du héros
  - Méthode `setArmorItem` qui permet de définir la pièce d'armure d'index `i` équipée du héros
  - Méthode `setBuffItem` qui permet de définir le buff d'index `i` de l'inventaire de buffs du héros
  - Méthode `armorToString` qui permet de récupérer une représentation textuelle des pièces d'armure équipées du héros
  - Méthode `ringToString` qui permet de récupérer une représentation textuelle des anneaux équipés du héros
  - Méthode `equip` qui permet au héros d'équiper une pièce d'armure à l'emplacement `i`
  - Méthode `equip` qui permet au héros d'équiper un anneau à l'emplacement `i`
  - Méthode `recuperate` qui permet au héros de se reposer
- Interface `IMonster` qui permet de gérer les monstres. Cette interface hérite de `ICharacter`
  - Méthode `getSkinThickness` qui permet de récupérer l'épaisseur de la peau du monstre
  - Méthode `getBuffInventory` qui permet de récupérer l'inventaire de buffs du monstre
  - Méthode `getBuffItem` qui permet de récupérer le buff d'index `i` de l'inventaire de buffs du monstre
  - Méthode `getTotalBuff` qui permet de récupérer la valeur totale des buffs du monstre
  - Méthode `setBuffItem` qui permet de définir le buff d'index `i` de l'inventaire de buffs du monstre
- Interface `IConsumable` qui permet de gérer les objets consommables
  - Méthode `getName` qui permet de récupérer le nom de l'objet consommable
  - Méthode `getCapacity` qui permet de récupérer la capacité de l'objet consommable
  - Méthode `getStat` qui permet de récupérer la statistique modifiée par l'objet consommable
  - Méthode `isEmpty` qui permet de vérifier si l'objet consommable est vide
  - Méthode `use` qui permet d'utiliser l'objet consommable
  - Méthode `toString` qui permet de récupérer une représentation textuelle de l'objet consommable
- Interface `IDrink` qui permet de gérer les objets consommables de type boisson. Cette interface hérite de `IConsumable`
- Interface `IFood` qui permet de gérer les objets consommables de type nourriture. Cette interface hérite de `IConsumable`
- Interface `IRepair` qui permet de gérer les objets consommables de type réparation. Cette interface hérite de `IConsumable`
  - Méthode `use` redéfinie pour supprimer les exceptions levées par la méthode `use` de l'interface `IConsumable`
- Interface `IWeapon` qui permet de gérer les armes
  - Méthode `getName` qui permet de récupérer le nom de l'arme
  - Méthode `getMinDamage` qui permet de récupérer les dégâts minimaux de l'arme
  - Méthode `getMaxDamage` qui permet de récupérer les dégâts maximaux de l'arme
  - Méthode `getStamCost` qui permet de récupérer le coût en stamina de l'arme
  - Méthode `getDurability` qui permet de récupérer la durabilité de l'arme
  - Méthode `use` qui permet d'utiliser l'arme
  - Méthode `isBroken` qui permet de vérifier si l'arme est cassée
  - Méthode `repairWith` qui permet de réparer l'arme avec un objet de type `IRepair`
  - Méthode `toString` qui permet de récupérer une représentation textuelle de l'arme

### Changed

- L'interface `Collectible` devient `ICollectible` et est déplacée dans le package `lsg_api`

### Deprecated

- La classe `CharacterAPI` et toutes ses méthodes
  - Ces méthodes sont dépréciées, car elles enfreignent les principes de la nouvelle version de l'API de modding
  - Lors de l'utilisation de cette classe ou de ses méthodes, un avertissement avec la procédure à suivre pour migrer vers la nouvelle version de l'API de modding sera affiché dans la console

### Removed

- Classe `GameStateAPI` et toutes ses méthodes
  - Ces méthodes sont supprimées, car elles enfreignent les principes de la nouvelle version de l'API de modding
  - Cette classe est supprimée sans être dépréciée, car elle n'a jamais été utilisée

## [0.1.3] - 2024-02-19

### Added

- Getter pour les champs `heroRenderer` et `zombieRenderer` dans la classe `CharacterAPI`
  - Ces getters utilisent les getters de la classe `LearningSoulsGameApplication` pour récupérer les informations des personnages du jeu
- Classe `GameStateAPI` qui permet de gérer l'état du jeu
  - Getter pour les champs `heroCanPlay` et `score` qui utilisent les getters de la classe `LearningSoulsGameApplication` pour récupérer les informations du jeu

## [0.1.2] - 2024-02-18

### Added

- Documentation privée traitant la création et la maintenance de l'API
- Méthodes `log`, `warn`, `error`, `info`, `debug`, `raw` et `print` dans la classe `Mod` qui permettent de simplifier l'accès à la console dans les mods
  - Ces méthodes utilisent la classe `ConsoleAPI` pour afficher des messages dans la console
- Classe `CharacterAPI` qui permet de gérer les personnages du jeu
  - Méthode `getHero` qui permet aux mods de récupérer le héros du jeu
  - Méthode `getMonster` qui permet aux mods de récupérer le monstre du jeu

### Changed

- Le type du champ `loadedMods` de la classe `ModLoader` passe de `HashSet<>` à `LinkedHashSet<>` pour conserver l'ordre d'insertion des mods

### Fixed

- Correction de la méthode `unloadMods` de la classe `ModLoader` qui ne déchargeait pas correctement les mods quand plusieurs mods étaient chargés
  - L'erreur était due à une boucle `for` qui modifiait la liste qu'elle parcourait. La solution a été de boucler sur un entier plutôt que sur les éléments de la liste


## [0.1.1] - 2024-02-18

### Added
- Méthode `warn` dans la classe `ConsoleAPI` qui permet d'afficher un message d'avertissement de couleur jaune dans la console avec le préfixe `[WARN]`
- Méthode `error` dans la classe `ConsoleAPI` qui permet d'afficher un message d'erreur de couleur rouge dans la console avec le préfixe `[ERROR]`
- Méthode `info` dans la classe `ConsoleAPI` qui permet d'afficher un message d'information de couleur bleue dans la console avec le préfixe `[INFO]`
- Méthode `debug` dans la classe `ConsoleAPI` qui permet d'afficher un message de débogage de couleur verte dans la console avec le préfixe `[DEBUG]`
- Méthode `raw` dans la classe `ConsoleAPI` qui permet d'afficher un message sans couleur dans la console avec le préfixe `[RAW]`
- Méthode `print(String message, String prefix, OutputStream stream)` dans la classe `ConsoleAPI` qui permet d'afficher un message avec le préfixe `[LSG_API]<prefix>` dans un flux de sortie donné
- Méthode `testStream` dans la classe `ConsoleAPI` qui permet de tester les flux de sortie
- Classe privée `ConsoleAPI.APIOutputStream` héritant de `OutputStream` qui permet de créer des flux de sortie pour la classe `ConsoleAPI`

## [0.1.0] - 2024-02-18

### Added

- Guide de modding pour la version 0.1.0 de l'API
- Classe `ConsoleAPI` qui permet de gérer les sorties console des mods
   - Méthode `log` qui permet d'afficher un message dans la console avec le préfixe `[MOD]`
- Classe abstraite `Mod` qui permet de créer des mods pour le jeu
    - Getter pour les champs `name`, `description`, `version`, `id`
    - Méthode `getMethod(String methodName)` qui permet de récupérer une méthode d'un mod
    - Méthodes abstraites `onLoaded`, `onUnloaded` qui permettent de gérer le chargement et le déchargement d'un mod
    - Méthodes `awake`, `start`, `earlyUpdate`, `update`, `lateUpdate`, `stop`, `destroy` qui permettent de gérer le cycle de vie d'un mod
    - Méthode `toString` qui permet d'afficher les informations d'un mod
- Classe `ModLoader` qui permet de charger et décharger des mods (Singleton)
    - Méthode `getInstance` qui permet de récupérer l'instance du mod loader
    - Méthode `loadMods` qui permet de charger les mods
    - Méthode `unloadMods` qui permet de décharger les mods
    - Méthode `invoke(String methodName)` qui permet d'appeler une méthode de tous les mods