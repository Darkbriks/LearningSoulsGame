package lsg.buffs.rings;

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
}
