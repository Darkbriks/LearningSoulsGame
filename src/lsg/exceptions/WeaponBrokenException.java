package lsg.exceptions;

import lsg_api.weapon.IWeapon;

/**
 * Exception quand l'arme est cassée
 */
public class WeaponBrokenException extends Exception
{
    /**
     * Arme cassée (Weapon) (private) (final)
     */
    private final IWeapon weapon;

    /**
     * Constructeur de l'exception
     * Affiche "(arme) is broken"
     * @param weapon arme cassée
     */
    public WeaponBrokenException(IWeapon weapon) { super(weapon + " is broken"); this.weapon = weapon;}
    /**
     * Getter de l'arme cassée
     * @return arme cassée
     */
    private IWeapon getWeapon() { return weapon; }
}