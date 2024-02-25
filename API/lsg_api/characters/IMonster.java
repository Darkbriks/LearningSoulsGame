package lsg_api.characters;

import lsg_api.buffs.IBuffItem;
import lsg_api.buffs.ITalismans;

public interface IMonster extends ICharacter
{
    /////////////// GETTERS ///////////////
    float getSkinThickness();
    ITalismans[] getBuffInventory();
    ITalismans getBuffItem(int index);
    float getTotalBuff();

    /////////////// SETTERS ///////////////
    void setBuffItem(IBuffItem item, int slot);
}
