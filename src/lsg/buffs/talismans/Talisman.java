package lsg.buffs.talismans;

import java.util.Calendar;

import lsg.buffs.BuffItem;

/**
 * Classe abstraite pour les talismans.
 * Un talisman est un buff qui s'active entre deux heures de la journée.
 */
public class Talisman extends BuffItem {
	
	private final float buff ;
	private final int start;
    private final int end ;
	
	public Talisman(String name, float buff, int start, int end) {
		super(name) ;
		this.buff = buff ;
		this.start = start ;
		this.end = end ;
	}
	
	@Override
	public float computeBuffValue() {
		int now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ;
		if(start <= end){
			if(now >= start && now < end) return buff ;
			else return 0f ;
		}else{
			if( (now <= 22 && now >= start) || (now >= 1 && now < end)) return buff ;
			else return 0f ;
		}
	}
	
}
