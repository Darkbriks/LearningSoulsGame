# Learning Souls Game (LSG) - Branche de l'API

Bienvenue sur la branche de l'API du projet Learning Souls Game (LSG).

## Aperçu des spécificités de la branche de l'API

La branche de l'API du projet Learning Souls Game (LSG) est dédiée à la conception et au développement de l'API de LSG. Elle est donc dédiée à la gestion des données et des fonctionnalités du jeu.

Cette API est développée en Java, et peut être utilisée pour la création de mods pour le jeu.

## Aperçu des fonctionnalités de l'API

L'API de LSG est conçue pour permettre la gestion des données et des fonctionnalités du jeu. Elle permet par exemple de manipuler les données des personnages, des ennemis, créer de nouvelles fenêtres, étendre les fonctionnalités du jeu ou objets du jeu, etc.

**Attention :** L'API est **requise** pour exécuter le jeu. Elle est actuellement fournie dans le `jar` du jeu pour simplifier le développement, mais elle sera séparée du jeu dans une version future.

## Téléchargement de l'API

L'API de LSG est actuellement fournie dans le `jar` du jeu, et il n'est pas nécessaire de la télécharger séparément.
Cepandant, si vous souhaitez créer un mod pour le jeu, vous devrez télécharger l'API pour pouvoir l'utiliser dans votre projet.

**Attention :** L'API est actuellement en cours de développement, et peut contenir des bugs ou des fonctionnalités non implémentées, être instable, provoquer des crashs, ou être modifiée sans préavis.
Il est nécessaire d'être conscient de ces risques avant de l'utiliser.

Pour télécharger l'API, vous pouvez soit télécharger l'une des [releases](https://github.com/Darkbriks/LearningSoulsGame/releases) du jeu, soit cloner le dépôt et compiler le projet vous-même.

*Un guide pour cloner et compiler le projet est en cours de rédaction.*

## Utilisation de l'API

L'API de LSG est conçue pour être utilisée par des développeurs et des développeuses pour créer des mods afin d'étendre LSG.

Elle est composée de plusieurs classes et interfaces que vous pouvez utiliser pour interagir avec le jeu.

*L'API est actuellement en cours de développement, et la documentation est en cours de rédaction.*

En attendant une documentation complète et a jour, voici un aperçu de la documentation de la première version de l'API :

**Attention :** Cette documentation est obsolète et ne correspond pas à la version actuelle de l'API. Elle est fournie à titre indicatif, et sera mise à jour prochainement.

### Prérequis
Pour pouvoir modder LearningSoulsGame, vous aurez besoin de:
- Un éditeur de texte ou un IDE
- Connaissances de base en java
- Le jeu LearningSoulsGame (pour tester vos mods)
- L'API de modding de LearningSoulsGame

### Créer un mod

#### Structure d'un mod
Un mod est une archive java (.jar) contenant un fichier META-INF/MANIFEST.MF et un ou plusieurs fichiers .class qui étendent ou modifient les classes du jeu.
Exemple de structure de mod:
```
mod.jar
├── META-INF
    |── MANIFEST.MF
├── com
    |-- gregooninator
        |-- learningsoulsgame
            |-- mod
                |-- MyMod.class
                |-- utils
                    |-- MyUtils.class
```

#### Classe Mod
Lors du chargement du mod, le jeu va chercher une classe qui étend la classe `Mod` implémentée dans l'API. Cette classe ne doit pas être modifiée, mais étendue.
Toute modification de cette classe peut entrainer des comportements inattendus, des erreurs ou des crashs.
Voici ce à quoi ressemble la classe `Mod` pour la version actuelle (0.1.0) de l'API:
```java
package user_mods;

import java.lang.reflect.Method;

public abstract class Mod
{
    protected String name;
    protected String description;
    protected String version;
    private int id;

    private static int nextId = 0;

    public Mod(String name, String description, String version)
    {
        this.name = name;
        this.description = description;
        this.version = version;
        this.id = nextId++;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getVersion() { return version; }
    public int getId() { return id; }

    public Method getMethod(String methodName)
    {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                return method;
            }
        }
        return null;
    }

    public void awake() {}
    public void start() {}
    public void earlyUpdate() {}
    public void update() {}
    public void lateUpdate() {}
    public void stop() {}
    public void destroy() {}

    abstract public void onLoaded();
    abstract public void onUnloaded();

    public String toString()
    {
        return "Mod:\n" + name + "\n - " + description + "\n - " + version;
    }
}
```
Cette classe contient des méthodes qui seront appelées à des moments précis du cycle de vie du jeu. Vous pouvez les redéfinir dans votre classe mod pour y ajouter du code.
De plus, votre classe mod **doit** implémenter les méthodes `onLoaded` et `onUnloaded` qui seront appelées lors du chargement et du déchargement du mod.
Si ces methodes ne sont pas utiles pour votre mod, vous pouvez les laisser vides, mais elles doivent être présentes.
<br>
Dans un soucis de cohérence lors du debug, il n'est pas recommandé redéfinir la méthode `toString` de la classe `Mod`.
<br>
Dans la version actuelle de l'API, vous ne pouvez definir les informations de votre mod que dans le constructeur de votre classe mod.
De plus, vous ne pourrez pas changer ces informations après l'initialisation de votre mod.

#### Utiliser l'API
L'API de modding de LearningSoulsGame est distribuée sous forme de fichier .jar que vous pouvez ajouter à votre projet pour bénéficier de toutes les fonctionnalités de l'API.
<br>
Elle est composée de plusieurs classes et interfaces que vous pouvez utiliser pour modifier le jeu.
<br>
La documentation de l'API est en cours de rédaction et sera disponible prochainement.

#### Charger un mod
Pour charger un mod, vous devez ajouter le fichier .jar de votre mod dans le dossier `user_mods` du jeu.
Le jeu va charger tous les mods présents dans ce dossier lors de son démarrage.
<br>
Si un mod est mal formé ou contient des erreurs, le jeu va afficher un message d'erreur et ne chargera pas le mod.
Il est donc conseillé de lancez le jeu depuis un terminal pour voir les messages d'erreur lors du debug de votre mod.

#### Compiler un mod
##### Windows
La compilation d'un mod s'effectue en quatre étapes:
1. Création du manifest
- Dans la racine de votre mod, créez un dossier `META-INF`
- Dans ce dossier, créez un fichier `MANIFEST.MF`
- Ouvrez le fichier avec un éditeur de texte
- Ajoutez les lignes suivantes:
```
Manifest-Version: 1.0
Main-Class: <Nom de votre classe principale>
```
Exemple:
```
Manifest-Version: 1.0
Main-Class: com.gregooninator.learningsoulsgame.mod.MyMod
```

2. Compilation des fichiers .java en fichiers .class
- Ouvrez un terminal
- Placez-vous dans le dossier contenant les fichiers .java de votre mod
- Exécutez la commande `javac -source 8 -target 8 -cp <Chemin vers l'API de modding> *.java <...>`

Exemple: `javac -source 8 -target 8 -cp C:\Users\Greg\Documents\LearningSoulsGame\api.jar *.java utils/*.java`

NB: Vous pouvez utiliser l'option `-d` pour spécifier le dossier de destination des fichiers .class (il est recommandé d'utiliser cette option si votre mod ne possède pas une classe unique)

3. Création de l'archive .jar
- Placez-vous dans le dossier contenant les fichiers .class de votre mod
- Exécutez la commande `jar cfm <Nom de votre mod>.jar <Chemin vers le manifest> *.class <...>`

Exemple: `jar cfm mod.jar META-INF/MANIFEST.MF com/gregooninator/learningsoulsgame/mod/*.class com/gregooninator/learningsoulsgame/mod/utils/*.class`

4. Profitez de votre mod
- Déplacez le fichier .jar de votre mod dans le dossier `user_mods` du jeu
- Lancez le jeu
- Si votre mod ne contient pas d'erreurs, il sera chargé et vous pourrez profiter de ses fonctionnalités

##### Linux
*En cours de rédaction*

### Exemples de mods
#### Afficher un message au chargement et au déchargement du mod

```java
import lsg_api.ConsoleAPI;

public class MyConsoleMod extends user_mods.Mod {
    public MyConsoleMod() {
        super("MyConsoleMod", "Affiche un message dans la console", "1.0.0");
    }

    @Override
    public void onLoaded() {
        ConsoleAPI.log("ConsoleMod loaded.");
    }

    @Override
    public void onUnloaded() {
        ConsoleAPI.log("ConsoleMod unloaded.");
    }
}
```
Ce mod très simple utilise la classe `ConsoleAPI` de l'API pour afficher un message dans la console du jeu lors de son chargement et de son déchargement.

Le résultat dans la console ressemblera à ceci:
```
...
[LOG] ConsoleMod loaded.
...
[LOG] ConsoleMod unloaded.
```