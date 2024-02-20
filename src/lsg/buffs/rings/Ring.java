package lsg.buffs.rings;

import lsg.buffs.BuffItem;
import lsg_api.buffs.IRing;
import lsg_api.characters.IHero;

/**
 * Abstract class Ring
 * Un anneau est un buff qui peut être équipé par le héros
 * Un anneau a un nom, une valeur de buff et une référence vers le héros qui le porte
 * La valeur de buff est calculée par une méthode abstraite
 * Cette classe hérite de la classe abstraite BuffItem
 * @see lsg.buffs.BuffItem
 * @see lsg.characters.Hero
 */
public abstract class Ring extends BuffItem implements IRing
{
	/**
	 * Le pouvoir de l'anneau (int) (protected)
	 */
	protected int power ;

	/**
	 * Le héros qui porte l'anneau (Hero) (protected)
	 */
	protected IHero hero ;

	/**
	 * Constructeur de Ring
	 * @param name le nom de l'anneau (String)
	 * @param power le pouvoir de l'anneau (int)
	 */
	public Ring(String name, int power)
	{
		super(name) ;
		this.power = power ;
	}

	 /**
	  * Getter qui retourne le héros portant l'anneau
	  * @return le héros portant l'anneau (Hero)
	  */
	public IHero getIHero() { return hero; }

	/**
	 * Setter qui définit le héros portant l'anneau
	 * @param hero le héros portant l'anneau (Hero)
	 */
	public void setIHero(IHero hero) { this.hero = hero; }
}