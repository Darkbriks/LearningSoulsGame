package lsg.texts;

import lsg_api.consumables.IConsumable;
import lsg_api.weapon.IWeapon;

public class TooltipTexts
{
    public static String attackTooltip(IWeapon weapon) { return "Attacks the monster with " + weapon.getName() + ".\nInflicts between " + weapon.getMinDamage() + " and  " + weapon.getMaxDamage() + "  points of damage.\nCosts " + weapon.getStamCost() + " stamina points"; }
    public static String recuperateTooltip(int lifeRegen, int stamRegen) { return "Recovers " + lifeRegen + " life points and " + stamRegen + " stamina points"; }
    public static String consumeTooltip(IConsumable consumable) { return "Consume " + consumable.getName() + " and recover " + consumable.getCapacity() + " point of " + consumable.getStat(); }
}
