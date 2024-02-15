package lsg.armor;

/**
 * Class DragonSlayerLeggings
 * La classe DragonSlayerLeggings représente un objet d'armure collectible dans le jeu.
 * Elle hérite de la classe ArmorItem.
 * Cette pièce d'armure est inspirée de la série de jeux vidéo Dark Souls.
 * Les Dragon Slayer Leggings sont des jambières qui protègent les jambes du joueur.
 * Elles sont très lourdes et offrent une bonne protection contre les dégâts physiques.
 * Lien vers le <a href="https://darksouls.fandom.com/wiki/Dragonslayer_Leggings">wiki</a> de Dark Souls pour plus d'informations sur les Dragon Slayer Leggings.
 * @see ArmorItem
 */
public class DragonSlayerLeggings extends ArmorItem
{
    /**
     * Crée une nouvelle paire de Dragon Slayer Leggings.
     * Elle a pour nom "Dragon Slayer Leggings" et pour valeur d'armure 10.2.
     * @see lsg.armor.ArmorItem
     */
    public DragonSlayerLeggings() { super("Dragon Slayer Leggings", 10.2f); }

    /**
     * En raison de son poids, les Dragon Slayer Leggings ont un poids de 3, contrairement aux autres objets d'armure.
     * @return Le poids des Dragon Slayer Leggings.
     */
    @Override
    public int getWeight() { return 3; }
}