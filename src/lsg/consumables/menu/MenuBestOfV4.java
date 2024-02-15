package lsg.consumables.menu;

import lsg.consumables.Consumable;
import lsg.consumables.drinks.*;
import lsg.consumables.food.*;
import lsg.consumables.repair.*;

import java.util.LinkedHashSet;

public class MenuBestOfV4 extends LinkedHashSet<Consumable>
{
    public MenuBestOfV4()
    {
        add(new Hamburger());
        add(new Wine());
        add(new Americain());
        add(new Coffee());
        add(new Whisky());
        add(new RepairKit());
    }

    @Override
    public String toString()
    {
        String str = getClass().getSimpleName() + ":\n";
        for (int i = 0; i < size(); ++i)
        {
            str += String.format("   %d: %s\n", i + 1, toArray()[i]);
        }
        return str;
    }
}