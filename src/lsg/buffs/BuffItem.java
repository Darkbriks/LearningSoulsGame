package lsg.buffs;

import lsg.bags.Collectible;
import java.util.Locale;

/**
 * Abstract class BuffItem
 * Un buff est un objet qui peut être équipé par le héros (anneau) ou un monstre (talisman)
 * Un buff a un nom et une valeur de buff
 * La valeur de buff est calculée par une méthode abstraite
 * Cette classe implémente l'interface Collectible
 * Les objets de cette classe ont un poids de 1 par défaut
 * @see lsg.buffs.rings.Ring
 * @see lsg.buffs.talismans.Talisman
 * @see lsg.bags.Collectible
 */
public abstract class BuffItem implements Collectible
{
	/**
	 * Le nom du buff (String) (final) (private)
	 */
	private final String name;

	/**
	 * Constructeur de BuffItem
	 * @param name le nom du buff (String)
	 */
	public BuffItem(String name) { this.name = name; }

	/**
	 * Méthode abstraite pour calculer la valeur du buff
	 * @return la valeur du buff (float)
	 */
	public abstract float computeBuffValue();

	/**
	 * Getter pour le nom du buff
	 * @return le nom du buff (String)
	 */
	public String getName() { return name; }

	/**
	 * Getter pour le poids du buff
	 * Méthode de l'interface Collectible
	 * @see lsg.bags.Collectible
	 * @return le poids du buff (int)
	 */
	@Override
	public int getWeight() { return 1; }

	/**
	 * Méthode toString pour afficher le buff
	 * @return le buff (String)
	 * @see java.lang.Object#toString()
	 * @see java.util.Locale
	 * Exemple : [Ring of the Hunter, 0.00]
	 */
	@Override
	public String toString() { return String.format(Locale.US, "[%s, %.2f]", getName(), computeBuffValue()); }
}