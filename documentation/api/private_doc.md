# Guide de création et de maintenance de l'API
## Introduction
Ce document a pour but de guider les développeurs dans la création et la maintenance de l'API. Il est divisé en plusieurs sections, chacune correspondant à une étape du processus de création et de maintenance de l'API.

## Compilation de l'API
- Ouvrir le terminal
- Se placer dans le dossier out/production (pour les projets IntelliJ IDEA)
- Exécuter la commande `jar cvf LSG_API_V0-2-1.jar lsg_api/*.class lsg_api/armor/*.class lsg_api/bags/*.class lsg_api/buffs/*.class lsg_api/characters/*.class lsg_api/consumables/*.class lsg_api/weapon/*.class user_mods/Mod.class user_mods/ModdedWindow.class lsg\utils\Constants.class`