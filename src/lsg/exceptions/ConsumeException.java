package lsg.exceptions;

import lsg_api.consumables.IConsumable;

/**
 * Classe abstraite pour les exceptions de consomables
 */
public abstract class ConsumeException extends Exception
{
    /**
     * Consommable ayant provoqué l'exception (Consumable) (private) (final)
     */
    private final IConsumable consumable;

    /**
     * Constructeur de l'exception
     * @param message message de l'exception (String)
     * @param consumable consommable ayant provoqué l'exception (Consumable)
     */
    public ConsumeException(String message, IConsumable consumable)
    {
        super(message + " (" + consumable.toString() + ")");
        this.consumable = consumable;
    }

    /**
     * Getter du consommable ayant provoqué l'exception
     * @return consommable ayant provoqué l'exception (Consumable)
     */
    public IConsumable getConsumable() { return consumable; }
}
