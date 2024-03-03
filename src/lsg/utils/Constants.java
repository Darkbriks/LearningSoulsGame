package lsg.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import lsg.graphics.ImageFactory;

import java.util.Objects;

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
    public static final Version GAME_VERSION = new Version(0, 3, 2, "dev");
    public static final Version API_VERSION = new Version(0, 2, 2, "dev");
    public static final String GAME_CSS = "LSG.css";
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 800;
    public static Duration TITLE_ANIMATION_DURATION = Duration.millis(1000);
    public static final int TITLE_ANIMATION_DELAY = 250;

    /////////////// GAME INFO ///////////////
    public static final boolean IS_JAR = Objects.requireNonNull(ImageFactory.class.getResource("images")).toString().startsWith("jar:");
    public static final String JAR_PATH = ImageFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    public static final String IMAGES_PATH = "lsg/graphics/";

    /////////////// LANG ///////////////
    /**
     * Cette énumération permet de définir les langues disponibles
     */
    public enum LANG {
        EN("en"), FR("fr"), DE("de"), ES("es");

        private final String lang;
        LANG(String lang) { this.lang = lang; }

        /**
         * Cette méthode permet de récupérer la langue correspondant à une chaîne de caractères
         * @return : la langue correspondante
         */
        public String toString() { return lang; }

        /**
         * Cette méthode permet de récupérer la langue correspondant à une chaîne de caractères
         * @param lang : la chaîne de caractères correspondant à la langue
         * @return : la langue correspondante
         * @throws IllegalArgumentException : si la chaîne de caractères ne correspond à aucune langue
         */
        public static LANG fromString(String lang) {
            for (LANG l : LANG.values()) { if (l.toString().equals(lang)) { return l; } }
            throw new IllegalArgumentException("No enum constant lsg.data.texts.InterpretableText.LANG." + lang);
        }
    }

    //private static LANG lang = LANG.EN;
    private static final SimpleStringProperty lang = new SimpleStringProperty(LANG.EN.toString());
    public static LANG getLang() { return LANG.fromString(lang.getValue()); }
    public static SimpleStringProperty getLangProperty() { return lang; }
    public static void setLang(LANG lang) { Constants.lang.setValue(lang.toString()); }
    public static String[] getLangList() { return new String[]{LANG.EN.toString(), LANG.FR.toString(), LANG.DE.toString(), LANG.ES.toString()}; }
}