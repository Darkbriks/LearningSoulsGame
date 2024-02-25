package lsg_api.bags;

import lsg.exceptions.BagFullException;
import lsg_api.ConsoleAPI;
import lsg_api.consumables.ICollectible;

public interface IBag
{
    /////////////// GETTERS ///////////////
    int getCapacity();
    int getWeight();
    ICollectible[] getItems();

    /////////////// METHODS ///////////////
    void push(ICollectible item) throws BagFullException;
    ICollectible pop(ICollectible item);
    boolean contains(ICollectible item);
    static void transfer(IBag from, IBag into)
    {
        if (from == into || from == null || into == null) { return; }
        for (ICollectible item : from.getItems())
        {
            try { into.push(from.pop(item)); }
            catch (BagFullException e) { ConsoleAPI.warn(e.getMessage()); }
        }
    }

    /////////////// OVERRIDES ///////////////
    @Override
    String toString();
}
