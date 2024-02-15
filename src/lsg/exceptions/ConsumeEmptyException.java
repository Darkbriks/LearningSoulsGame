package lsg.exceptions;

import lsg.consumables.Consumable;

/**
 * Classe pour les exceptions de consommables n'ayant plus de charges
 */
public class ConsumeEmptyException extends ConsumeException
{
    /**
     * Constructeur de l'exception
     * @param consumable consommable ayant provoqué l'exception (Consumable)
     */
    public ConsumeEmptyException(Consumable consumable)
    {
        super(consumable.getName() + " has no more charges", consumable);
    }
}
