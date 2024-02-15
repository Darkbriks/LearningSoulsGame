package lsg.consumables;

import lsg.bags.Collectible;
import lsg.exceptions.ConsumeEmptyException;

public class Consumable implements Collectible
{
    /////////////// FIELDS ///////////////
    private String name;
    private int capacity;
    private String stat;

    /////////////// CONSTRUCTOR ///////////////
    public Consumable(String name, int capacity, String stat)
    {
        this.name = name;
        this.capacity = capacity;
        this.stat = stat;
    }

    /////////////// GETTERS ///////////////
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String getStat() { return stat; }
    @Override
    public int getWeight() { return 1; }

    /////////////// SETTERS ///////////////
    protected void setCapacity(int capacity) { this.capacity = capacity; }

    /////////////// METHODS ///////////////
    public int use() throws ConsumeEmptyException
    {
        int capacity = getCapacity();
        if (capacity == 0) { throw new ConsumeEmptyException(this); }
        setCapacity(0);
        return capacity;
    }

    @Override
    public String toString()
    {
        return String.format("%s [ %s:%d ]",
                getName(),
                getStat().toUpperCase(),
                getCapacity());
    }
}
