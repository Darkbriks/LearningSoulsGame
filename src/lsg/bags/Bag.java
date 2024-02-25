package lsg.bags;

import lsg.exceptions.BagFullException;
import lsg.utils.Constants;
import lsg_api.bags.IBag;
import lsg_api.consumables.ICollectible;

import java.util.HashSet;

/**
 * Classe Bag
 * Cette classe permet de définir les sacs qui peuvent être utilisés par le héros et les monstres
 * Un sac a une capacité (nombre d'objets) et un poids total des objets actuellement dans le sac, ainsi qu'une liste des objets actuellement dans le sac
 * La capacité du sac esr fixée à l'instanciation et ne peut pas être modifiée
 * Un sac peut contenir des objets de type Collectible
 * @see lsg.bags.SmallBag
 * @see lsg.bags.MediumBag
 * @see ICollectible
 */
public class Bag implements IBag
{
    /////////////// FIELDS ///////////////
    /**
     * Capacité du sac en nombre d'objets (final) (int) (private)
     */
    private final int capacity;
    /**
     * Poids total des objets actuellement dans le sac (int) (private)
     */
    private int weight;
    /**
     * Liste des objets actuellement dans le sac (HashSet<Collectible>) (private)
     */
    private final HashSet<ICollectible> items;

    /////////////// CONSTRUCTEUR ///////////////
    /**
     * Constructeur de la classe Bag
     * @param capacity (int) : capacité du sac en nombre d'objets
     */
    public Bag(int capacity)
    {
        this.capacity = capacity;
        this.weight = 0;
        this.items = new HashSet<>();
    }

    /////////////// GETTERS ///////////////
    /**
     * Getter de la capacité du sac
     * @return la capacité du sac
     */
    public int getCapacity() { return capacity; }
    /**
     * Getter du poids total des objets actuellement dans le sac
     * @return le poids total des objets actuellement dans le sac
     */
    public int getWeight() { return weight; }
    /**
     * Getter de la liste des objets actuellement dans le sac
     * @return la liste des objets actuellement dans le sac
     */
    public ICollectible[] getItems() { return items.toArray(new ICollectible[0]); }

    /////////////// METHODS ///////////////
    /**
     * Méthode permettant d'ajouter un objet au sac
     * Si le poids de l'objet à ajouter est supérieur à la capacité restante du sac, l'objet n'est pas ajouté
     * @param item (Collectible) : objet à ajouter au sac
     */

    public void push(ICollectible item) throws BagFullException
    {
        if (weight + item.getWeight() > capacity) { throw new BagFullException(this); }

        items.add(item);
        weight += item.getWeight();
    }

    /**
     * Méthode permettant de retirer un objet du sac
     * @param item (Collectible) : objet à retirer du sac
     * @return l'objet retiré du sac
     */
    public ICollectible pop(ICollectible item)
    {
        if (items.contains(item))
        {
            items.remove(item);
            weight -= item.getWeight();
            return item;
        }
        return null;
    }

    /**
     * Méthode permettant de savoir si un objet est dans le sac
     * @param item (Collectible) : objet à rechercher dans le sac
     * @return true si l'objet est dans le sac, false sinon
     */
    public boolean contains(ICollectible item) { return items.contains(item); }

    /**
     * Méthode permettant de transférer les objets d'un sac vers un autre (dans la limite de la capacité du sac de destination)
     * @param from (Bag) : sac à vider
     * @param into (Bag) : sac à remplir
     */
    public static void transfer(Bag from, Bag into)
    {
        if (from == into | from == null | into == null) { return; }
        for (ICollectible item : from.getItems())
        {
            try { into.push(from.pop(item)); }
            catch (BagFullException e) { System.out.println(e.getMessage()); }
        }
    }

    /**
     * Méthode permettant d'afficher le contenu du sac
     * @return le contenu du sac
     */
    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder(String.format("Bag [%d | %d/%d kg ]", items.size(), weight, capacity));
        if (items.isEmpty()) { return string + "\n" + Constants.BULLET_POINT + "Empty"; }
        for (ICollectible item : items) { string.append("\n" + Constants.BULLET_POINT).append(item.toString()).append("[").append(item.getWeight()).append(" kg]"); }
        return string.toString();
    }
}