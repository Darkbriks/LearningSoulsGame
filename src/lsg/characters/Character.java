package lsg.characters;

import javafx.beans.property.SimpleDoubleProperty;
import lsg.bags.SmallBag;
import lsg.exceptions.*;
import lsg.helpers.Dice;
import lsg.utils.Constants;
import lsg.weapons.Sword;
import lsg_api.bags.IBag;
import lsg_api.buffs.IBuffItem;
import lsg_api.characters.ICharacter;
import lsg_api.consumables.*;
import lsg_api.weapon.IWeapon;

import java.util.Locale;

public abstract class Character implements ICharacter
{
    /////////////// FIELDS ///////////////
    protected String name;
    protected int maxLife, maxStamina, life, stamina;
    protected SimpleDoubleProperty lifeRateProperty, staminaRateProperty;
    protected Dice dice;
    protected IWeapon weapon;
    protected IBuffItem[] buffInventory;
    protected IConsumable consumable;
    protected IBag bag;

    /////////////// CONSTRUCTORS ///////////////
    public Character(String name, int maxLife, int maxStamina)
    {
        this.name = name;
        this.maxLife = maxLife;
        this.maxStamina = maxStamina;
        this.life = maxLife;
        this.stamina = maxStamina;
        this.lifeRateProperty = new SimpleDoubleProperty(1);
        this.staminaRateProperty = new SimpleDoubleProperty(1);
        this.dice = new Dice(101);
        this.weapon = new Sword();
        this.bag = new SmallBag();

        ICharacter.addCharacter(this);
    }

    /////////////// GETTERS ///////////////
    public String getName() { return name; }
    public int getMaxLife() { return maxLife; }
    public int getMaxStamina() { return maxStamina; }
    public int getLife() { return life; }
    public int getStamina() { return stamina; }
    public SimpleDoubleProperty lifeRateProperty() { return lifeRateProperty; }
    public SimpleDoubleProperty staminaRateProperty() { return staminaRateProperty; }
    public IWeapon getWeapon() { return weapon; }
    public abstract IBuffItem[] getBuffInventory();
    public abstract IBuffItem getBuffItem(int index);
    public IConsumable getConsumable() { return consumable; }
    public int getBagCapacity() throws NoBagException
    { if (bag == null) { throw new NoBagException(); } return bag.getCapacity(); }

    public int getBagWeight() throws NoBagException
    { if (bag == null) { throw new NoBagException(); } return bag.getWeight(); }
    public ICollectible[] getBagItems() throws NoBagException
    { if (bag == null) { throw new NoBagException(); } return bag.getItems(); }

    public IBag getBag() throws NoBagException
    { if (bag == null) { throw new NoBagException(); } return bag; }

    /////////////// SETTERS ///////////////
    public void setMaxLife(int maxLife)
    {
        this.setLife((int) Math.round((this.life / (double) this.maxLife) * maxLife));
        this.maxLife = maxLife;
        // Pas besoin de recalculer le lifeRate car il est recalculé dans setLife()
        //this.lifeRate = new SimpleDoubleProperty((double) this.life / (double) this.maxLife);
    }
    public void setMaxStamina(int maxStamina)
    {
        this.setStamina((int) Math.round((this.stamina / (double) this.maxStamina) * maxStamina));
        this.maxStamina = maxStamina;
        // Pas besoin de recalculer le staminaRate car il est recalculé dans setStamina()
        //this.staminaRate = new SimpleDoubleProperty((double) this.stamina / (double) this.maxStamina);
    }
    protected void setLife(int life)
    {
        this.life = Math.min(life, this.maxLife);
        this.lifeRateProperty.set((double) this.life / (double) this.maxLife);
    }
    public int setStamina(int stamina)
    {
        // Stamina can't be set to a value higher than maxStamina or lower than 0
        // Returns the amount of stamina that was not set
        // Its negative if stamina was set to a value lower than 0, positive if it was set to a value higher than maxStamina
        int overflow = 0;
        if (stamina > maxStamina)
        {
            overflow = stamina - maxStamina;
            this.stamina = maxStamina;
        }
        else if (stamina < 0)
        {
            overflow = stamina;
            this.stamina = 0;
        }
        else { this.stamina = stamina; }
        this.staminaRateProperty.set((double) this.stamina / (double) this.maxStamina);
        return overflow;
    }

    public void setWeapon(IWeapon weapon) { this.weapon = weapon; }
    public abstract void setBuffItem(IBuffItem item, int slot);
    public void setConsumable(IConsumable consumable) { this.consumable = consumable; }
    public IBag setBag(IBag bag)
    {
        if (bag == null) { System.out.printf("null bag can't be set to %s%n", name); return null; }
        IBag oldBag = this.bag;
        this.bag = bag;
        IBag.transfer(oldBag, this.bag);
        System.out.printf("%s changes %s for %s%n", name, oldBag.getClass().getSimpleName(), this.bag.getClass().getSimpleName());
        return oldBag;
    }

    /////////////// PRIVATE METHODS ///////////////
    /**
     * @param weapon Weapon to attack with
     * @return Damage dealt
     * @throws WeaponNullException if weapon is null
     * @throws WeaponBrokenException if weapon is broken
     * @throws StaminaEmptyException if stamina is empty
     */
    private int attackWith(IWeapon weapon) throws
            WeaponNullException,
            WeaponBrokenException,
            StaminaEmptyException
    {
        if (weapon == null) { throw new WeaponNullException(); }
        if (weapon.isBroken()) { throw new WeaponBrokenException(weapon); }
        if (stamina <= 0) { throw new StaminaEmptyException(); }

        double damage = 0;
        if (!weapon.isBroken() && isAlive())
        {
            int precision = dice.roll();
            damage = weapon.getMinDamage() + lerp(weapon.getMaxDamage() - weapon.getMinDamage(), precision / 100.0);
            damage += damage * computeBuff();
            int overflow = setStamina(stamina - weapon.getStamCost());
            if (overflow < 0) { damage = lerp(damage, 1 + overflow / (double) weapon.getStamCost()); }
            weapon.use();
        }
        return (int) Math.round(damage);
    }

    private void drink(IDrink drink) throws ConsumeNullException, ConsumeEmptyException
    {
        if (drink == null) { throw new ConsumeNullException(); }
        if (isAlive())
        {
            System.out.printf("%s drinks %s%n", name, drink.toString());
            setStamina(stamina + drink.use());
        }
    }

    private void eat(IFood food) throws ConsumeNullException, ConsumeEmptyException
    {
        if (food == null) { throw new ConsumeNullException(); }
        if (isAlive())
        {
            System.out.printf("%s eats %s%n", name, food.toString());
            setLife(life + food.use());
        }
    }

    private void repairWeaponWith(IConsumable consumable) throws ConsumeNullException, ConsumeRepairNullWeaponException
    {
        if (weapon == null) { throw new ConsumeRepairNullWeaponException(); }
        if (consumable == null) { throw new ConsumeNullException(); }
        if (isAlive())
        {
            System.out.printf("%s repairs %s with %s%n", name, weapon.toString(), consumable.toString());
            weapon.repairWith((lsg.consumables.repair.RepairKit) consumable);
        }
    }

    private double lerp(double v1, double v2, double t) { return (v1 + (v2 - v1) * t); }
    private double lerp(double v2, double t) { return (v2 * t); }

    private <T extends IConsumable> IConsumable fastUseFirst(Class<T> type)
            throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException
    {
        if (bag != null)
        {
            IConsumable consumable = null;
            System.out.printf("%s looks for %s in his bag%n", name, type.getSimpleName());
            ICollectible[] items = bag.getItems();

            for (ICollectible item : items) { if (type.isInstance(item)) { consumable = (IConsumable) item; break; } }

            if (consumable == null) { throw new ConsumeNullException(); }

            use(consumable);
            if ((consumable).getCapacity() == 0) { pullOut(consumable); }
            return consumable;
        }
        return null;
    }

    /////////////// PROTECTED METHODS ///////////////
    protected abstract float computeProtection();
    protected abstract float computeBuff();

    /////////////// PUBLIC METHODS ///////////////
    public boolean isAlive() { return life > 0; }

    public int attack() throws
            WeaponNullException,
            WeaponBrokenException,
            StaminaEmptyException
    {
        return attackWith(weapon);
    }

    public int getHitWith(int value)
    {
        int minor = (life - value >= 0) ? value : life;
        minor = (int) Math.round(lerp(minor, 1 - this.computeProtection() / 100));
        this.setLife(life - minor);
        return minor;
    }

    public void use(IConsumable consumable)
            throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException
    {
        if (consumable == null) { throw new ConsumeNullException(); }
        if (consumable instanceof IDrink) { drink((IDrink) consumable); }
        else if (consumable instanceof IFood) { eat((IFood) consumable); }
        else if (consumable instanceof IRepairKit)
        {
            if (weapon == null) { throw new ConsumeRepairNullWeaponException(); }
            repairWeaponWith(consumable);
        }
    }

    public void consume()
            throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException
    {
        use(consumable);
        consumable = null;
    }

    public void pickUp(ICollectible item) throws NoBagException, BagFullException
    {
        if (bag == null) { throw new NoBagException(); }
        if (item != null)
        {
            bag.push(item);
            if (bag.contains(item)) { System.out.printf("%s picks up %s%n", name, item.toString()); }
            else { System.out.printf("%s's bag is full, he can't pick up %s%n", name, item.toString()); }
        }
    }

    public ICollectible pullOut(ICollectible item) throws NoBagException
    {
        if (bag == null) { throw new NoBagException(); }
        if (item != null)
        {
            ICollectible pulled = bag.pop(item);
            if (pulled != null) { System.out.printf("%s pulls out %s%n", name, pulled.toString()); }
            else { System.out.printf("%s can't pull out %s%n", name, item.toString()); }
            return pulled;
        }
        return null;
    }

    public void printBag() { if (bag != null) { System.out.println(bag); } }

    public void equip(IWeapon weapon) throws NoBagException, BagFullException
    {
        if (bag == null) { throw new NoBagException(); }
        if (weapon != null)
        {
            if (bag.contains(weapon))
            {
                IWeapon oldWeapon = this.weapon;
                this.weapon = (IWeapon) pullOut(weapon);
                if (oldWeapon != null) { pickUp(oldWeapon); }
                System.out.printf("%s equips %s%n", name, weapon);
            }
            else { System.out.printf("%s can't equip %s because he doesn't have it%n", name, weapon.toString()); }
        }
    }

    public void equip(IConsumable consumable) throws NoBagException, BagFullException
    {
        if (bag == null) { throw new NoBagException(); }
        if (consumable != null)
        {
            if (bag.contains(consumable))
            {
                IConsumable oldConsumable = this.consumable;
                this.consumable = (IConsumable) pullOut(consumable);
                if (oldConsumable != null) { pickUp(oldConsumable); }
                System.out.printf("%s equips %s%n", name, consumable);
            }
            else { System.out.printf("%s can't equip %s because he doesn't have it%n", name, consumable.toString()); }
        }
    }

    public IDrink fastDrink() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException
    { return (IDrink) fastUseFirst(IDrink.class); }
    public IFood fastEat() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException
    { return (IFood) fastUseFirst(IFood.class); }
    public IRepairKit fastRepair() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException
    { return (IRepairKit) fastUseFirst(IRepairKit.class); }

    public abstract void printStats();

    @Override
    public String toString()
    {
        return String.format(Locale.US,
                "%-20s %-20s %s:%-10d %s:%-10d %s:%-10f %s:%-10f (%s)",
                "[ " + this.getClass().getSimpleName() + " ]",
                this.name,
                Constants.CHARACTER_LIFE_STAT_STRING.toUpperCase(),
                this.life,
                Constants.CHARACTER_STAM_STAT_STRING.toUpperCase(),
                this.stamina,
                Constants.CHARACTER_PROTECTION_STAT_STRING.toUpperCase(),
                this.computeProtection(),
                Constants.CHARACTER_BUFFS_STAT_STRING.toUpperCase(),
                this.computeBuff(),
                ((isAlive()) ? "ALIVE" : "DEAD"));
    }
}
