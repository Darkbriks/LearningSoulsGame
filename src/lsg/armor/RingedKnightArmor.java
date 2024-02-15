package lsg.armor;

/**
 * Classe RingedKnightArmor
 * La classe RingedKnightArmor représente un objet d'armure collectible dans le jeu.
 * Elle hérite de la classe ArmorItem.
 * Cette pièce d'armure est inspirée de la série de jeux vidéo Dark Souls.
 * La Ringed Knight Armor est une armure qui protège le corps du joueur.
 * Elle est très lourde et offre une bonne protection contre les dégâts physiques.
 * Lien vers le <a href="https://darksouls.fandom.com/wiki/Ringed_Knight_Armor">wiki</a> de Dark Souls pour plus d'informations sur la Ringed Knight Armor.
 * @see ArmorItem
 */
public class RingedKnightArmor extends ArmorItem
{
    /**
     * Crée une nouvelle Ringed Knight Armor.
     * Elle a pour nom "Ringed Knight Armor" et pour valeur d'armure 14.99.
     * @see ArmorItem
     */
    public RingedKnightArmor()
    {
        super("Ringed Knight Armor", 14.99f);
    }
}
