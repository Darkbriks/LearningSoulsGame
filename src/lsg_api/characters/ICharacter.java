package lsg_api.characters;

import javafx.beans.property.SimpleDoubleProperty;
import lsg.exceptions.*;
import lsg_api.bags.IBag;
import lsg_api.buffs.IBuffItem;
import lsg_api.consumables.*;
import lsg_api.weapon.IWeapon;

import java.util.HashSet;

public interface ICharacter
{
    HashSet<ICharacter> characters = new HashSet<>();

    static void addCharacter(ICharacter character) { characters.add(character); }
    static void removeCharacter(ICharacter character) { characters.remove(character); }

    static ICharacter getCharacter(String name)
    {
        for (ICharacter character : characters) { if (character.getName().equals(name)) { return character; } }
        return null;
    }

    /////////////// GETTERS ///////////////
    String getName();
    int getLife();
    int getMaxLife();
    int getStamina();
    int getMaxStamina();
    SimpleDoubleProperty lifeRateProperty();
    SimpleDoubleProperty staminaRateProperty();
    IWeapon getWeapon();
    IBuffItem[] getBuffInventory();
    IBuffItem getBuffItem(int index);
    IConsumable getConsumable();
    int getBagCapacity() throws NoBagException;
    int getBagWeight() throws NoBagException;
    ICollectible[] getBagItems() throws NoBagException;
    IBag getBag() throws NoBagException;

    /////////////// SETTERS ///////////////
    void setMaxLife(int maxLife);
    void setMaxStamina(int maxStamina);
    int setStamina(int stamina);
    void setWeapon(IWeapon weapon);
    void setBuffItem(IBuffItem item, int slot);
    void setConsumable(IConsumable consumable);
    IBag setBag(IBag bag);

    /////////////// METHODS ///////////////
    boolean isAlive();
    int attack() throws WeaponNullException, WeaponBrokenException, StaminaEmptyException;
    int getHitWith(int value);
    void use(IConsumable consumable) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException;
    void consume() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException;
    void pickUp(ICollectible item) throws NoBagException, BagFullException;
    ICollectible pullOut(ICollectible item) throws NoBagException;
    void printBag();
    void equip(IWeapon weapon) throws NoBagException, BagFullException;
    public void equip(IConsumable consumable) throws NoBagException, BagFullException;
    public IDrink fastDrink() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException;
    IFood fastEat() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException;
    IRepairKit fastRepair() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException;
    void printStats();

    /////////////// OVERRIDES ///////////////
    @Override
    String toString();
}
