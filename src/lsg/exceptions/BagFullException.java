package lsg.exceptions;

import lsg_api.bags.IBag;

/**
 * Classe BagFullException
 * Cette classe permet de définir une exception pour un sac plein
 */
public class BagFullException extends Exception
{
    /**
     * Sac plein (Bag) (private) (final)
     */
    private final IBag bag;

    /**
     * Constructeur de l'exception
     * Le message d'erreur est "(nom du sac) is full"
     * @param bag le sac plein
     */
    public BagFullException(IBag bag)
    {
        super(bag.getClass().getSimpleName() + " is full");
        this.bag = bag;
    }

    /**
     * Getter du sac plein
     * @return le sac
     */
    public IBag getBag() { return bag; }
}
