package lsg.characters;
import lsg.utils.Constants;
import lsg_api.armor.IArmorItem;
import lsg_api.buffs.IBuffItem;
import lsg_api.buffs.IRing;
import lsg_api.characters.IHero;

public class Hero extends Character implements IHero
{
    /////////////// FIELDS ///////////////
    private final IArmorItem[] armor = new IArmorItem[Constants.HERO_MAX_ARMOR_PIECES];
    private int lifeRegen;
    private int stamRegen;

    /////////////// CONSTRUCTORS ///////////////
    public Hero () { this("Gregooninator"); }

    public Hero (String name)
    {
        super(name, Constants.INIT_HERO_LIFE, Constants.INIT_HERO_STAMINA);
        this.buffInventory = new IRing[Constants.HERO_MAX_BUFF_PIECES];
        this.lifeRegen = Constants.HERO_LIFE_RECOVER_BASE;
        this.stamRegen = Constants.HERO_STAMINA_RECOVER_BASE;
    }

    /////////////// GETTERS ///////////////
    public IArmorItem[] getArmorItems() { return armor; }
    public int getLifeRegen() { return lifeRegen; }
    public int getStamRegen() { return stamRegen; }

    public float getTotalArmor()
    {
        float somme = 0;
        for (int i = 0; i < Constants.HERO_MAX_ARMOR_PIECES; i++)
        {
            if (armor[i] != null) { somme += armor[i].getArmorValue(); }
        }
        return somme;
    }

    @Override
    public IRing[] getBuffInventory()
    {
        IRing[] rings = new IRing[2];
        for (int i = 0; i < 2; i++)
        {
            if (buffInventory[i] instanceof IRing) { rings[i] = (IRing) buffInventory[i]; }
        }
        return rings;
    }

    @Override
    public IRing getBuffItem(int index)
    {
        if (index < 0 || index > Constants.HERO_MAX_BUFF_PIECES) { return null; }
        if (buffInventory[index] instanceof IRing) { return (IRing) buffInventory[index]; }
        return null;
    }

    public float getTotalBuff()
    {
        float somme = 0;
        for (int i = 0; i < Constants.HERO_MAX_BUFF_PIECES; i++)
        {
            if (buffInventory[i] != null) { somme += buffInventory[i].computeBuffValue(); }
        }
        return somme;
    }

    /////////////// SETTERS ///////////////
    public void setLifeRegen(int lifeRegen) { this.lifeRegen = lifeRegen; }
    public void setStamRegen(int stamRegen) { this.stamRegen = stamRegen; }

    public void setArmorItem(IArmorItem item, int slot)
    {
        if (slot < 1 || slot > Constants.HERO_MAX_ARMOR_PIECES) { return; }
        armor[slot-1] = item;
    }

    @Override
    public void setBuffItem(IBuffItem item, int slot)
    {
        if (slot < 1 || slot > Constants.HERO_MAX_BUFF_PIECES || !(item instanceof IRing)) { return; }
        buffInventory[slot-1] = item;
    }

    /////////////// METHODS ///////////////
    public String armorToString()
    {
        return String.format("ARMOR: 1: %s, 2: %s, 3: %s (TOTAL: %.2f)",
                (armor[0] != null) ? this.armor[0].toString() : "empty",
                (armor[1] != null) ? this.armor[1].toString() : "empty",
                (armor[2] != null) ? this.armor[2].toString() : "empty",
                getTotalArmor());
    }

    public String ringToString()
    {
        return String.format("RING: 1: %s, 2: %s (TOTAL: %.2f)",
                (buffInventory[0] != null) ? this.buffInventory[0].toString() : "empty",
                (buffInventory[1] != null) ? this.buffInventory[1].toString() : "empty",
                getTotalBuff());

    }

    @Override
    public float computeProtection() { return getTotalArmor(); }

    @Override
    public float computeBuff() { return getTotalBuff(); }

    public void equip(IArmorItem item, int slot)
    {
        if (slot < 1 || slot > Constants.HERO_MAX_ARMOR_PIECES) { return; }
        if (item != null && bag != null)
        {
            IArmorItem pulled = (IArmorItem) bag.pop(item);
            if (pulled != null)
            {
                System.out.printf("%s equips %s%n", name, pulled);
                setArmorItem(pulled, slot);
            }
            else { System.out.printf("%s can't equip %s%n", name, item); }
        }
    }

    public void equip(IRing ring, int slot)
    {
        if (slot < 1 || slot > Constants.HERO_MAX_BUFF_PIECES) { return; }
        if (ring != null && bag != null)
        {
            IRing pulled = (IRing) bag.pop(ring);
            if (pulled != null)
            {
                System.out.printf("%s equips %s%n", name, pulled);
                setBuffItem(pulled, slot);
            }
            else { System.out.printf("%s can't equip %s%n", name, ring); }
        }
    }

    public void recuperate()
    {
        this.setLife(this.getLife() + lifeRegen);
        this.setStamina(this.getStamina() + stamRegen);
    }

    @Override
    public void printStats()
    {
        System.out.println(this);
        System.out.println("ARMOR : " + this.armorToString());
        System.out.println("RINGS : " + this.ringToString());
        System.out.println("CONSUMABLE : " + this.consumable);
        System.out.println("WEAPON : " + this.weapon);
        System.out.println(this.bag);
    }
}
