package lsg_api.buffs;

import lsg_api.characters.IHero;

public interface IRing extends IBuffItem
{
    IHero getIHero();
    void setIHero(IHero hero);
}
