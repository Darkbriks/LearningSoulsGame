package lsg.consumables.menu;

import lsg.consumables.Consumable;
import lsg.consumables.drinks.*;
import lsg.consumables.food.*;

import java.util.HashSet;

public class MenuBestOfV3 extends HashSet<Consumable>
{
    public MenuBestOfV3()
    {
        add(new Hamburger());
        add(new Wine());
        add(new Americain());
        add(new Coffee());
        add(new Whisky());
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

    // L'iteration sur un HashSet est renvoyee sans ordre particulier.
    // Tandis que l'iteration sur un LinkedHashSet est renvoyee dans l'ordre d'insertion.
}