package lsg.exceptions;

import lsg.data.XMLFactory;
import lsg.utils.Constants;
import lsg_api.consumables.IConsumable;

/**
 * Classe pour les exceptions de consommables n'ayant plus de charges
 */
public class ConsumeEmptyException extends ConsumeException
{
    /**
     * Constructeur de l'exception
     * @param consumable consommable ayant provoqué l'exception (Consumable)
     */
    public ConsumeEmptyException(IConsumable consumable)
    {
        super(consumable.getName() + XMLFactory.getText(XMLFactory.TEXTE_ID.CONSUME_EMPTY).toString(Constants.getLang()), consumable);
    }
}
