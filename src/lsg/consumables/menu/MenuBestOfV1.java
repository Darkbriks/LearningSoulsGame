package lsg.consumables.menu;

import lsg.consumables.Consumable;
import lsg.consumables.drinks.*;
import lsg.consumables.food.*;

public class MenuBestOfV1
{
    private Consumable[] menu;

    public MenuBestOfV1()
    {
        menu = new Consumable[5];
        menu[0] = new Hamburger();
        menu[1] = new Wine();
        menu[2] = new Americain();
        menu[3] = new Coffee();
        menu[4] = new Whisky();
    }

    @Override
    public String toString()
    {
        String str = getClass().getSimpleName() + ":\n";
        for (int i = 0; i < menu.length; ++i)
        {
            str += String.format("   %d: %s\n", i + 1, menu[i]);
        }
        return str;
    }
}