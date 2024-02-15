package lsg.exceptions;

import lsg.weapons.Weapon;

/**
 * Exception quand l'arme est cassée
 */
public class WeaponBrokenException extends Exception
{
    /**
     * Arme cassée (Weapon) (private) (final)
     */
    private final Weapon weapon;

    /**
     * Constructeur de l'exception
     * Affiche "(arme) is broken"
     * @param weapon arme cassée
     */
    public WeaponBrokenException(Weapon weapon) { super(weapon + " is broken"); this.weapon = weapon;}
    /**
     * Getter de l'arme cassée
     * @return arme cassée
     */
    private Weapon getWeapon() { return weapon; }
}