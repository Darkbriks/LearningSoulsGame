package lsg_api;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import lsg.LearningSoulsGameApplication;

public class GameStateAPI
{
    public static BooleanProperty heroCanPlay() { return LearningSoulsGameApplication.getInstance().getHeroCanPlay(); }

    public static IntegerProperty getScore() { return LearningSoulsGameApplication.getInstance().getScore(); }
}
