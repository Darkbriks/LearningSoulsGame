package lsg.consumables.food;

import lsg.consumables.Consumable;
import lsg.utils.Constants;

public class Food extends Consumable
{
    public Food(String name, int capacity) { super(name, capacity, Constants.CHARACTER_LIFE_STAT_STRING); }
}
