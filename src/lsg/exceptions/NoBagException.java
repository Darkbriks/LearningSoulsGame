package lsg.exceptions;

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
        super("No bag has been equipped !");
    }
}
