package lsg_api.consumables;

import javafx.beans.property.SimpleBooleanProperty;
import lsg.exceptions.ConsumeEmptyException;

public interface IConsumable extends ICollectible
{
    String getName();
    int getCapacity();
    String getStat();
    SimpleBooleanProperty isEmpty();
    int use() throws ConsumeEmptyException;

    @Override
    String toString();
}