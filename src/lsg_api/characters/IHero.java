package lsg_api.characters;

import lsg_api.armor.IArmorItem;
import lsg_api.buffs.IBuffItem;
import lsg_api.buffs.IRing;

public interface IHero extends ICharacter
{
    /////////////// GETTERS ///////////////
    int getLifeRegen();
    int getStamRegen();
    IArmorItem[] getArmorItems();
    float getTotalArmor();
    IRing[] getBuffInventory();
    IRing getBuffItem(int index);
    float getTotalBuff();

    /////////////// SETTERS ///////////////
    void setLifeRegen(int lifeRegen);
    void setStamRegen(int stamRegen);
    void setArmorItem(IArmorItem item, int slot);
    void setBuffItem(IBuffItem item, int slot);

    /////////////// METHODS ///////////////
    String armorToString();
    String ringToString();
    void equip(IArmorItem item, int slot);
    void equip(IRing ring, int slot);
    void recuperate();
}
