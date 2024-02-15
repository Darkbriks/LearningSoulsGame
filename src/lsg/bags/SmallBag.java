package lsg.bags;

/**
 * Classe SmallBag
 * Cette classe permet de définir les petits sacs (10) qui peuvent être utilisés par le joueur
 * @see lsg.bags.Bag
 */
public class SmallBag extends Bag
{
    /**
     * Constructeur de la classe SmallBag
     * Appelle le constructeur de la classe mère (Bag) avec une capacité de 10
     */
    public SmallBag() { super(10); }
}
