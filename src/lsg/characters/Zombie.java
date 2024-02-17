package lsg.characters;

import lsg.weapons.Weapon;

public class Zombie extends Monster
{
    public Zombie ()
    {
        super("Zombie");
        this.setWeapon(new Weapon("Zombie's hands", 5, 20, 1, 1000));
        this.setMaxLife(10);
        this.setMaxStamina(10);
        this.setSkinThickness(10);
    }
}
