package lsg.utils;

import javafx.util.Duration;

public class Constants
{
    /////////////// CHARACTER ///////////////
    public static final String CHARACTER_LIFE_STAT_STRING = "life";
    public static final String CHARACTER_STAM_STAT_STRING = "stamina";
    public static final String CHARACTER_PROTECTION_STAT_STRING = "protection";
    public static final String CHARACTER_BUFFS_STAT_STRING = "buff";

    /////////////// HERO ///////////////
    public static final int INIT_HERO_LIFE = 100;
    public static final int INIT_HERO_STAMINA = 50;
    public static final int HERO_LIFE_RECOVER_BASE = 10;
    public static final int HERO_STAMINA_RECOVER_BASE = 10;
    public static final int HERO_MAX_ARMOR_PIECES = 3;
    public static final int HERO_MAX_BUFF_PIECES = 2;

    /////////////// MONSTER ///////////////
    public static final int INIT_MONSTER_LIFE = 10;
    public static final int INIT_MONSTER_STAMINA = 10;
    public static final float SKIN_THICKNESS = 20;
    public static final int MONSTER_MAX_BUFF_PIECES = 1;

    /////////////// WEAPON ///////////////
    public static final String WEAPON_MIN_DMG_STAT_STRING = "minDamage";
    public static final String WEAPON_MAX_DMG_STAT_STRING = "maxDamage";
    public static final String WEAPON_STAM_COST_STAT_STRING = "stamCost";
    public static final String WEAPON_DURABILITY_STAT_STRING = "durability";
    public static final int WEAPON_DURABILITY_DEFAULT_VALUE = 100;

    /////////////// DISPLAY ///////////////
    public static final String BULLET_POINT = "\u2219";

    /////////////// GAME DISPLAY ///////////////
    public static final String GAME_TITLE = "Learning Souls Game";
    public static final String GAME_VERSION = "0.3.1.dev";
    public static final String API_VERSION = "0.2.2.dev";
    public static final String GAME_CSS = "LSG.css";
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 800;
    public static Duration TITLE_ANIMATION_DURATION = Duration.millis(1000);
    public static final int TITLE_ANIMATION_DELAY = 250;
}