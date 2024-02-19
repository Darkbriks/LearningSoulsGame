package lsg_api;

import lsg.LearningSoulsGameApplication;
import lsg.characters.Hero;
import lsg.characters.Monster;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;

public class CharacterAPI
{
    public static Hero getCurrentHero() { return LearningSoulsGameApplication.getInstance().getHero(); }

    public static Monster getCurrentMonster() { return LearningSoulsGameApplication.getInstance().getZombie(); }

    public static HeroRenderer getHeroRenderer() { return LearningSoulsGameApplication.getInstance().getHeroRenderer(); }

    public static ZombieRenderer getZombieRenderer() { return LearningSoulsGameApplication.getInstance().getZombieRenderer(); }
}
