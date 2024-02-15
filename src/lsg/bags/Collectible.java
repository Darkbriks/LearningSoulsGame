package lsg.bags;

/**
 * Interface Collectible
 * Cette interface permet de définir les objets qui peuvent être ramassés par le joueur
 * Lorsqu'elle est implémentée, la méthode getWeight() doit être définie. Elle permet de récupérer le poids de l'objet
 * @see lsg.bags.Bag
 * @see lsg.armor.ArmorItem
 * @see lsg.weapons.Weapon
 * @see lsg.consumables.Consumable
 * @see lsg.buffs.BuffItem
 */
public interface Collectible
{
    /**
     * Méthode permettant de récupérer le poids de l'objet
     * @return le poids de l'objet
     */
    public int getWeight();
}