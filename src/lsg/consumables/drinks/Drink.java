package lsg.consumables.drinks;

import lsg.consumables.Consumable;
import lsg.utils.Constants;

public class Drink extends Consumable
{
    public Drink(String name, int capacity) { super(name, capacity, Constants.CHARACTER_STAM_STAT_STRING); }
}
