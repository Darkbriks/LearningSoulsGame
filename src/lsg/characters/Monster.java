package lsg.characters;
import lsg.utils.Constants;
import lsg_api.buffs.IBuffItem;
import lsg_api.buffs.ITalismans;
import lsg_api.characters.IMonster;

public class Monster extends Character implements IMonster
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
        this.buffInventory = new ITalismans[Constants.MONSTER_MAX_BUFF_PIECES];
    }

    /////////////// GETTERS ///////////////
    public float getSkinThickness() { return skinThickness; }

    @Override
    public ITalismans[] getBuffInventory()
    {
        ITalismans[] talismans = new ITalismans[1];
        for (int i = 0; i < 1; i++)
        {
            if (buffInventory[i] instanceof ITalismans) { talismans[i] = (ITalismans) buffInventory[i]; }
        }
        return talismans;
    }

    @Override
    public ITalismans getBuffItem(int index)
    {
        if (index < 0 || index > Constants.MONSTER_MAX_BUFF_PIECES) { return null; }
        if (buffInventory[index] instanceof ITalismans) { return (ITalismans) buffInventory[index]; }
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
    public void setBuffItem(IBuffItem item, int slot)
    {
        if (slot < 1 || slot > Constants.MONSTER_MAX_BUFF_PIECES || !(item instanceof ITalismans)) { return; }
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
