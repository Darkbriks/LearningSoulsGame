package lsg_api.armor;

import lsg_api.consumables.ICollectible;

public interface IArmorItem extends ICollectible
{
    String getName();
    float getArmorValue();

    @Override
    String toString();
}
