package lsg.consumables.menu;

import lsg.consumables.Consumable;
import lsg.consumables.drinks.*;
import lsg.consumables.food.*;

import java.util.HashSet;

public class MenuBestOfV2
{
    private HashSet<Consumable> menu;

    public MenuBestOfV2()
    {
        menu = new HashSet<>();
        menu.add(new Hamburger());
        menu.add(new Wine());
        menu.add(new Americain());
        menu.add(new Coffee());
        menu.add(new Whisky());
    }

    @Override
    public String toString()
    {
        String str = getClass().getSimpleName() + ":\n";
        for (int i = 0; i < menu.size(); ++i)
        {
            str += String.format("   %d: %s\n", i + 1, menu.toArray()[i]);
        }
        return str;
    }
}