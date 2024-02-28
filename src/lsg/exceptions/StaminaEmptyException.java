package lsg.exceptions;

import lsg.data.XMLFactory;
import lsg.utils.Constants;

/**
 * Classe d'exception quand la stamina est vide
 */
public class StaminaEmptyException extends Exception
{
    /**
     * Constructeur de l'exception
     * Affiche "Stamina is empty"
     */
    public StaminaEmptyException() { super(XMLFactory.getText(XMLFactory.TEXTE_ID.STAMINA_EMPTY).toString(Constants.getLang())); }
}