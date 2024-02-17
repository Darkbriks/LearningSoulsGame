package lsg.buffs.rings;

import lsg.weapons.Sword;

/**
 * Un anneau qui donne un bonus de 10 de puissance si le héros a une épée.
 */
public class RingOfSwords extends Ring{
	
	public RingOfSwords() {
		super("Ring of Swords", 10) ;
	}
	
	@Override
	public float computeBuffValue() {
		if (hero != null && (hero.getWeapon() instanceof Sword) )  return power ;
		else return 0f ;
		
	}
}
