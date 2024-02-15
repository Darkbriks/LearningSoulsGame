package lsg.exceptions;

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
        super("Weapon is null");
    }
}
