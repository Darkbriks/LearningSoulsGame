package lsg_api.consumables;

import javafx.beans.property.SimpleBooleanProperty;
import lsg.exceptions.ConsumeEmptyException;

public interface IConsumable extends ICollectible
{
    IConsumable NULL_CONSUMABLE = new IConsumable()
    {
        @Override
        public String getName() { return "NULL_CONSUMABLE"; }
        @Override
        public int getCapacity() { return 0; }
        @Override
        public String getStat() { return "NULL_STAT"; }
        @Override
        public int getWeight() { return 0; }
        @Override
        public SimpleBooleanProperty isEmpty() { return new SimpleBooleanProperty(true); }
        @Override
        public int use() throws ConsumeEmptyException { throw new ConsumeEmptyException(this); }
        @Override
        public String toString() { return getName() + " (empty)"; }
    };

    String getName();
    int getCapacity();
    String getStat();
    SimpleBooleanProperty isEmpty();
    int use() throws ConsumeEmptyException;

    @Override
    String toString();
}