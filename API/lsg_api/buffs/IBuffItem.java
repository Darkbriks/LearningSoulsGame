package lsg_api.buffs;

import lsg_api.consumables.ICollectible;

public interface IBuffItem extends ICollectible
{
    float computeBuffValue();
    String getName();
    @Override
    String toString();
}