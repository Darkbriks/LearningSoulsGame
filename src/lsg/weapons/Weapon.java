package lsg.weapons;

import lsg.utils.Constants;
import lsg_api.characters.ICharacter;
import lsg_api.characters.IHero;
import lsg_api.consumables.IRepairKit;
import lsg_api.weapon.IWeapon;
import lsg_api.weapon.IWeaponSkillArgs;

public class Weapon implements IWeapon
{
    /////////////// FIELDS ///////////////
    private final String name;
    private final int minDamage;
    private final int maxDamage;
    private final int stamCost;
    private int durability;

    /////////////// CONSTRUCTORS ///////////////
    public Weapon(String name, int minDamage, int maxDamage, int stamCost, int durability)
    {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.stamCost = stamCost;
        this.durability = durability;
    }

    public Weapon() { this("Basic Sword", 5, 10, 20, 100); }

    /////////////// GETTERS ///////////////
    public String getName() { return name; }
    public int getMinDamage() { return minDamage; }
    public int getMaxDamage() { return maxDamage; }
    public int getStamCost() { return stamCost; }
    public int getDurability() { return durability; }
    @Override
    public int getWeight() { return 2; }

    /////////////// SETTERS ///////////////
    private void setDurability(int durability) { this.durability = Math.min(durability, Constants.WEAPON_DURABILITY_DEFAULT_VALUE); }

    /////////////// METHODS ///////////////
    public void use() { if (getDurability() > 0) { setDurability(getDurability() - 1); } }

    public boolean isBroken() { return getDurability() <= 0; }

    public void repairWith(IRepairKit kit) { setDurability(getDurability() + kit.use()); }

    public <T extends ICharacter> int weaponSkill(T character)
    {
        // Attaque qui utilise toute la stamina du héros et inflige minDamage * (stamina / 2)
        int stam = character.getStamina();
        character.setStamina(0);
        this.durability -= 5;
        if (this.durability < 0) { int diff = Math.abs(this.durability); this.durability = 0; return (int) (minDamage * (stam / 2) / (0.5 * diff)); }
        return minDamage * (stam / 2);
    }

    @Override
    public String toString()
    {
        return String.format("%s (%s:%d %s:%d %s:%d %s:%d)",
                getName(),
                Constants.WEAPON_MIN_DMG_STAT_STRING.toUpperCase(),
                getMinDamage(),
                Constants.WEAPON_MAX_DMG_STAT_STRING.toUpperCase(),
                getMaxDamage(),
                Constants.WEAPON_STAM_COST_STAT_STRING.toUpperCase(),
                getStamCost(),
                Constants.WEAPON_DURABILITY_STAT_STRING.toUpperCase(),
                getDurability());
    }
}
