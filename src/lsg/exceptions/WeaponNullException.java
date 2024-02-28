package lsg.exceptions;

import lsg.data.XMLFactory;
import lsg.utils.Constants;

/**
 * Exception quand l'arme est null
 */
public class WeaponNullException extends Exception
{
    /**
     * Constructeur de l'exception
     * Affiche "Weapon is null"
     */
    public WeaponNullException() { super(XMLFactory.getText(XMLFactory.TEXTE_ID.WEAPON_NULL).toString(Constants.getLang())); }
}