package lsg.consumables.repair;

import lsg.consumables.Consumable;
import lsg.utils.Constants;

public class RepairKit extends Consumable
{
    public RepairKit() { super("Repair Kit", 10, Constants.WEAPON_DURABILITY_STAT_STRING); }

    @Override
    public int use()
    {
        if (this.getCapacity() == 0) return 0;
        this.setCapacity(this.getCapacity() - 1);
        return 1;
    }
}
