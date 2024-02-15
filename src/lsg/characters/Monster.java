package lsg.characters;
import lsg.buffs.BuffItem;
import lsg.buffs.talismans.Talisman;
import lsg.utils.Constants;

public class Monster extends Character
{
    /////////////// FIELDS ///////////////
    private static int instanceCount = 0;
    private float skinThickness;

    /////////////// CONSTRUCTORS ///////////////
    public Monster ()
    {
        this("Monster_");
        this.name += instanceCount;
    }

    public Monster (String name)
    {
        super(name, Constants.INIT_MONSTER_LIFE, Constants.INIT_MONSTER_STAMINA);
        instanceCount++;
        this.skinThickness = Constants.SKIN_THICKNESS;
        this.buffInventory = new Talisman[Constants.MONSTER_MAX_BUFF_PIECES];
    }

    /////////////// GETTERS ///////////////
    public float getSkinThickness() { return skinThickness; }

    @Override
    public Talisman[] getBuffInventory()
    {
        Talisman[] talismans = new Talisman[1];
        for (int i = 0; i < 1; i++)
        {
            if (buffInventory[i] instanceof Talisman) { talismans[i] = (Talisman) buffInventory[i]; }
        }
        return talismans;
    }

    @Override
    public Talisman getBuffItem(int index)
    {
        if (index < 0 || index > Constants.MONSTER_MAX_BUFF_PIECES) { return null; }
        if (buffInventory[index] instanceof Talisman) { return (Talisman) buffInventory[index]; }
        return null;
    }

    public float getTotalBuff()
    {
        float somme = 0;
        for (int i = 0; i < Constants.MONSTER_MAX_BUFF_PIECES; i++)
        {
            if (buffInventory[i] != null) { somme += buffInventory[i].computeBuffValue(); }
        }
        return somme;
    }

    /////////////// SETTERS ///////////////
    protected void setSkinThickness(float skinThickness) { this.skinThickness = skinThickness; }

    @Override
    public void setBuffItem(BuffItem item, int slot)
    {
        if (slot < 1 || slot > Constants.MONSTER_MAX_BUFF_PIECES || !(item instanceof Talisman)) { return; }
        buffInventory[slot-1] = item;
    }

    /////////////// METHODS ///////////////
    @Override
    public float computeProtection() { return skinThickness; }

    @Override
    public float computeBuff() { return getTotalBuff(); }

    @Override
    public void printStats()
    {
        System.out.println(this);
    }
}
