package lsg.buffs.rings;

import lsg.armor.ArmorItem;
import lsg.armor.DragonSlayerLeggings;

/**
 * Class DragonSlayerRing
 * Il donne un bonus de 14 en defense si le hero porte les DragonSlayerLeggings
 * Il donne un bonus de 0 en defense sinon
 * Il hérite de la classe Ring
 * Cette classe hérite de la classe abstraite Ring
 * @see lsg.buffs.rings.Ring
 */
public class DragonSlayerRing extends Ring
{
	/**
	 * Constructeur de DragonSlayerRing
	 * @see lsg.buffs.rings.Ring
	 */
	public DragonSlayerRing() { super("Dragon Slayer Ring", 14); }

	/**
	 * Methode qui permet de retourner la valeur du buff
	 * Pour cet anneau, la valeur du buff est de 14 si le hero a l'item DragonSlayerLeggings equipé, 0 sinon
	 * @return la valeur du buff (int).
	 */
	@Override
	public float computeBuffValue()
	{
		if (hero != null && hasDragonsSlayerItem()) { return power; }
		else return 0;
	}

	/**
	 * Methode qui permet de savoir si le hero a l'item DragonSlayerLeggings equipé sur lui
	 * @return boolean, true si le hero a l'item DragonSlayerLeggings equipé sur lui, false sinon
	 */
	private boolean hasDragonsSlayerItem()
	{
		ArmorItem[] items = hero.getArmorItems() ;
		for(ArmorItem item: items)
		{
			if(item instanceof DragonSlayerLeggings) return true ; 
		}
		return false ;
	}
}
