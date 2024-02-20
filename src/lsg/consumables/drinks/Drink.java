package lsg.consumables.drinks;

import lsg.consumables.Consumable;
import lsg.utils.Constants;
import lsg_api.consumables.IDrink;

public class Drink extends Consumable implements IDrink
{
    public Drink(String name, int capacity) { super(name, capacity, Constants.CHARACTER_STAM_STAT_STRING); }
}
