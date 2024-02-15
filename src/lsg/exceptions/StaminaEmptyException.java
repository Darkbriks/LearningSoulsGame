package lsg.exceptions;

/**
 * Classe d'exception quand la stamina est vide
 */
public class StaminaEmptyException extends Exception
{
    /**
     * Constructeur de l'exception
     * Affiche "Stamina is empty"
     */
    public StaminaEmptyException() { super("Stamina is empty"); }
}