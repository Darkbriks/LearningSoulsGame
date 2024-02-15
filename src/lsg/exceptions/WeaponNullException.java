package lsg.exceptions;

/**
 * Exception quand l'arme est null
 */
public class WeaponNullException extends Exception
{
    /**
     * Constructeur de l'exception
     * Affiche "Weapon is null"
     */
    public WeaponNullException() { super("Weapon is null"); }
}