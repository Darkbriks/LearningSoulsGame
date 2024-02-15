package lsg.buffs.rings;

import lsg.characters.Hero;

/**
 * Un anneau qui donne un bonus de 100% de puissance si le héros a moins de 50% de ses PV max.
 */
public class RingOfDeath extends Ring
{
	private static final float LIMIT = 0.5f ;

	public RingOfDeath() { super("Ring of Death", 10000); }

	@Override
	public float computeBuffValue() {
		if (hero != null){
			float gauge = (float)hero.getLife() / hero.getMaxLife() ;
			if(gauge <= LIMIT) return power ;
			else return 0f ;
		}else return 0f ;
	}
	
	/*
	 * Un test...
	 * @param args non utilisé
	 */
	/*public static void main(String[] args) {
		Hero hero = new Hero() ;
		Ring r = new RingOfDeath() ;
		hero.setRing(r, 1);
		hero.getHitWith(60) ; // pour abaisser les PV du hero
		System.out.println(r);
	}*/
}
