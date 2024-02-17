package lsg.consumables;

import javafx.beans.property.SimpleBooleanProperty;
import lsg.bags.Collectible;
import lsg.exceptions.ConsumeEmptyException;

public class Consumable implements Collectible
{
    /////////////// FIELDS ///////////////
    private final String name;
    private int capacity;
    private final String stat;
    private final SimpleBooleanProperty isEmpty;

    /////////////// CONSTRUCTOR ///////////////
    public Consumable(String name, int capacity, String stat)
    {
        this.name = name;
        this.capacity = capacity;
        this.stat = stat;
        this.isEmpty = new SimpleBooleanProperty(capacity == 0);
    }

    /////////////// GETTERS ///////////////
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String getStat() { return stat; }
    public SimpleBooleanProperty isEmpty() { return isEmpty; }
    @Override
    public int getWeight() { return 1; }

    /////////////// SETTERS ///////////////
    protected void setCapacity(int capacity)
    {
        this.capacity = capacity;
        isEmpty.set(capacity == 0);
    }

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
