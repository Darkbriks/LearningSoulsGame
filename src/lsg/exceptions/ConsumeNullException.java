package lsg.exceptions;

import lsg.consumables.Consumable;

/**
 * Classe pour les exceptions de consommables null
 */
public class ConsumeNullException extends ConsumeException
{
    /**
     * Constructeur de l'exception
     * On crée un consommable null pour pouvoir l'afficher et ne pas avoir d'erreur pour les méthodes definies en tant que nonnull
     */
    public ConsumeNullException()
    {
        super("Consumable is null", new Consumable("null", 0, "null"));
    }
}
