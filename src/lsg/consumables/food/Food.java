package lsg.consumables.food;

import lsg.consumables.Consumable;
import lsg.utils.Constants;
import lsg_api.consumables.IFood;

public class Food extends Consumable implements IFood
{
    public Food(String name, int capacity) { super(name, capacity, Constants.CHARACTER_LIFE_STAT_STRING); }
}
