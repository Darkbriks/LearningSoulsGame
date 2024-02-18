package lsg_api;

import lsg.LearningSoulsGameApplication;
import lsg.characters.Hero;
import lsg.characters.Monster;

public class CharacterAPI
{
    public static Hero getCurrentHero()
    {
        return LearningSoulsGameApplication.getInstance().getHero();
    }

    public static Monster getCurrentMonster()
    {
        return LearningSoulsGameApplication.getInstance().getZombie();
    }
}
