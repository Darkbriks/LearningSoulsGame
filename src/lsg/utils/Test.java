package lsg.utils;

import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.bags.MediumBag;
import lsg.characters.Hero;
import lsg.characters.Monster;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.food.Hamburger;
import lsg.consumables.menu.MenuBestOfV4;
import lsg.consumables.repair.RepairKit;
import lsg.weapons.ShotGun;

public class Test
{
    public static void launchTest1()
    {
        // Testing Hero and Monster classes
        //Hero h1 = new Hero();
        //System.out.println(h1.getHitWith(150));
        Hero h2 = new Hero("Darkbriks");

        //System.out.println(h1);
        System.out.println(h2);

        Monster m1 = new Monster();
        //Monster m2 = new Monster("M");
        //Monster m3 = new Monster();

        System.out.println(m1);
        //System.out.println(m2);
        //System.out.println(m3);

        // Testing Dice class
        /*Dice d = new Dice(50);
        int min=50, max=0;
        for (int i = 0; i<500; i++)
        {
            int r = d.roll();
            min = Math.min(min, r);
            max = Math.max(max, r);
            System.out.print(r + " ");
        }
        System.out.println("\nMin = " + min);
        System.out.println("Max = " + max);*/

        // Testing Weapon class
        /*Weapon w1 = new Weapon();
        Weapon w2 = new Weapon("Sword", 10, 20, 50, 100);

        System.out.println(w1);
        System.out.println(w2);

        Sword s1 = new Sword();

        System.out.println(s1);*/

        // Testing attackWith method
        // Note : The visibility of attackWith has changed, do not use following lines
        /*for (int i = 0; i<5; i++)
        {
            System.out.println(h2);
            System.out.println("Attack with " + s1 + " : " + h2.attackWith(s1););
        }
        for (int i = 0; i<5; i++)
        {
            System.out.println(m1);
            System.out.println("Attack with " + s1 + " : " + m1.attackWith(s1););
        }*/
        // Question 6.3 :
        // We can see that the durability of the sword is shared between the hero and the monster.
        // This is due to the fact that there is only one instance of sword shared for both characters

        // Testing attack method
        /*for (int i = 0; i<5; i++)
        {
            System.out.println(h2);
            System.out.println("Attack with " + s1 + " : " + h2.attack());
        }
        for (int i = 0; i<5; i++)
        {
            System.out.println(m1);
            System.out.println("Attack with " + s1 + " : " + m1.attack());
        }*/

        // Testing getHitWith and ShotGun
        /*ShotGun shotGun = new ShotGun();
        h2.setWeapon(shotGun);
        for (int i = 0; i < 3; i++)
        {
            int dmg = h2.attack();
            System.out.println(h2.getName() + " attack " + m1.getName() + " with " + h2.getWeapon().getName() + " (" + dmg +
                    ") -> Effective DMG: " + m1.getHitWith(dmg) + " PV");
            System.out.println(h2);
            System.out.println(m1);
        }*/
    }

    /*private void createExhaustedHero()
    {
        hero = new Hero("Exhausted Hero");
        hero.getHitWith(99);
        hero.setStamina(1);
        hero.getWeapon().use();
    }*/

    /*private void aTable()
    {
        MenuBestOfV4 m = new MenuBestOfV4();
        // Le hero consomme tous les items du menu
        for (Consumable c : m)
        {
            hero.use(c);
            System.out.println(hero);
        }
        System.out.println(hero.getWeapon());
    }*

    /*private void testBag()
    {
        init();
        createExhaustedHero();

        // Ajoût d'items dans le sac
        DragonSlayerLeggings dsl = new DragonSlayerLeggings();
        hero.pickUp(dsl);
        hero.pickUp(new RingedKnightArmor());
        ShotGun sg = new ShotGun();
        hero.pickUp(sg);

        // Affichage du contenu du sac
        System.out.println("\n" + hero.getBag() + "\n");

        // Changement de sac
        hero.setBag(new MediumBag());

        // Affichage du contenu du sac
        System.out.println("\n" + hero.getBag() + "\n");

        // Ajoût d'items dans le sac
        hero.pickUp(new Coffee());
        hero.pickUp(new Hamburger());
        hero.pickUp(new Whisky());
        hero.pickUp(new RepairKit());
        hero.pickUp(new RepairKit());

        // Affichage du contenu du sac
        System.out.println("\n" + hero.getBag() + "\n");

        // Avant
        System.out.println("\n--- Avant ---\n");

        // Affichage du héros
        hero.printStats();

        // Actions
        System.out.println("\n--- Actions ---\n");

        // Boit vite
        hero.fastDrink();

        // Mange vite
        hero.fastEat();

        // Equipe le ShotGun
        hero.equip(sg);

        // Equipe le DragonSlayerLeggings
        hero.equip(dsl, 1);

        // Repare le ShotGun
        hero.fastRepair();

        // Apres
        System.out.println("\n--- Apres ---\n");

        // Affichage du héros
        hero.printStats();
    }*/
}
