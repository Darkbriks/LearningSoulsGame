package lsg.exceptions;

import lsg.data.XMLFactory;
import lsg.utils.Constants;

/**
 * Classe NoBagException
 * Hérite de la classe Exception
 * Exception levée lorsqu'aucun sac n'est équipé
 */
public class NoBagException extends Exception
{
    /**
     * Constructeur de la classe NoBagException
     * Affiche le message d'erreur "No bag has been equipped !"
     */
    public NoBagException()
    {
        super(XMLFactory.getText(XMLFactory.TEXTE_ID.NO_BAG).toString(Constants.getLang()));
    }
}
