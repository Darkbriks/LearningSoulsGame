package lsg.armor;

import lsg_api.armor.IArmorItem;
import lsg_api.consumables.ICollectible;

/**
 * Classe ArmorItem
 * La classe ArmorItem représente un objet d'armure collectible dans le jeu.
 * Un objet d'armure a un nom et une valeur d'armure, et peut être équipé par le héros uniquement.
 * Elle implémente l'interface Collectible.
 * @see ICollectible
 * @see lsg.armor.DragonSlayerLeggings
 * @see lsg.armor.RingedKnightArmor
 * @see lsg.armor.BlackWitchVeil
 */
public class ArmorItem implements ICollectible, IArmorItem
{
    /////////////// FIELDS ///////////////
    /**
     * Le nom de l'objet d'armure. (final) (String) (private)
     */
    private final String name;
    /**
     * La valeur d'armure fournie par l'objet. (final) (float) (private)
     */
    private final float armorValue;

    /////////////// CONSTRUCTORS ///////////////
    /**
     * Crée un nouvel objet ArmorItem avec le nom et la valeur de l'armure spécifiés.
     *
     * @param name       Le nom de l'objet d'armure.
     * @param armorValue La valeur de l'armure fournie par l'objet.
     * @see lsg.armor.ArmorItem#name
     * @see lsg.armor.ArmorItem#armorValue
     */
    public ArmorItem(String name, float armorValue)
    {
        this.name = name;
        this.armorValue = armorValue;
    }

    /////////////// GETTERS ///////////////
    /**
     * Retourne le nom de l'objet d'armure.
     * @return Le nom de l'objet d'armure.
     */
    public String getName() { return name; }

    /**
     * Retourne la valeur d'armure fournie par l'objet.
     * @return La valeur d'armure fournie par l'objet.
     */
    public float getArmorValue() { return armorValue; }

    /**
     * Retourne le poids de l'objet d'armure.
     * Méthode de l'interface Collectible.
     * @see ICollectible
     * @return Le poids de l'objet d'armure.
     */
    @Override
    public int getWeight() { return 4; }

    /////////////// METHODS ///////////////
    /**
     * Retourne une chaîne de caractères décrivant l'objet d'armure.
     * @return Une chaîne de caractères décrivant l'objet d'armure.
     */
    @Override
    public String toString() { return getName() + " (AV : " + getArmorValue() + ")"; }
}