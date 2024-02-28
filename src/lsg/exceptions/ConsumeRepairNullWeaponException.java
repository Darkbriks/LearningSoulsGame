package lsg.exceptions;

import lsg.data.XMLFactory;
import lsg.utils.Constants;

/**
 * Exception pour les consommables de réparation appelés sur des armes nulles
 */
public class ConsumeRepairNullWeaponException extends Exception
{
    /**
     * Constructeur de l'exception
     * Affiche "Weapon is null"
     */
    public ConsumeRepairNullWeaponException()
    {
        super(XMLFactory.getText(XMLFactory.TEXTE_ID.CONSUME_REPAIR_NULL_WEAPON).toString(Constants.getLang()));
    }
}
