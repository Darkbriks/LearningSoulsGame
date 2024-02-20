package lsg_api;

import lsg.LearningSoulsGameApplication;
import lsg.characters.Hero;
import lsg.characters.Monster;

/**
 * This class is deprecated and will be removed in a future version.
 * Please use {@link lsg_api.characters.ICharacter} instead.
 */
@Deprecated
public class CharacterAPI
{
    static
    {
        ConsoleAPI.warn("CharacterAPI is deprecated and will be removed in a future version. Please use ICharacter instead.");
    }
    /**
     * This method is deprecated and will be removed in a future version.
     * Please use {@link lsg_api.characters.ICharacter#getCharacter(String)} instead.
     */
    @Deprecated
    public static Hero getCurrentHero()
    {
        ConsoleAPI.warn("CharacterAPI.getCurrentHero() is deprecated and will be removed in a future version. Please use ICharacter.getCharacter(String) instead.");
        return LearningSoulsGameApplication.getInstance().getHero();
    }

    /**
     * This method is deprecated and will be removed in a future version.
     * Please use {@link lsg_api.characters.ICharacter#getCharacter(String)} instead.
     */
    @Deprecated
    public static Monster getCurrentMonster()
    {
        ConsoleAPI.warn("CharacterAPI.getCurrentMonster() is deprecated and will be removed in a future version. Please use ICharacter.getCharacter(String) instead.");
        return LearningSoulsGameApplication.getInstance().getZombie();
    }
}
