package lsg_api.weapon;

import lsg_api.characters.ICharacter;
import lsg_api.consumables.ICollectible;
import lsg_api.consumables.IRepairKit;

public interface IWeapon extends ICollectible
{
    String getName();
    int getMinDamage();
    int getMaxDamage();
    int getStamCost();
    int getDurability();
    void use();
    boolean isBroken();
    void repairWith(IRepairKit kit);
    <T extends ICharacter> int weaponSkill(T character);

    @Override
    String toString();
}
