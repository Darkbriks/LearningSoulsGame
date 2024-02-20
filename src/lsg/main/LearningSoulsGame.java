package lsg.main;

import lsg.armor.BlackWitchVeil;
import lsg.armor.RingedKnightArmor;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.rings.RingOfSwords;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Lycanthrope;
import lsg.characters.Monster;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.weapons.Claw;
import lsg.weapons.Sword;
import lsg_api.ConsoleAPI;

import java.util.Scanner;

/**
 * Class principale du jeu
 */
@Deprecated
public class LearningSoulsGame
{
    /**
     * Hero du jeu (Joueur) (Hero) (public)
     */
    public Hero hero;
    /**
     * Monstre du jeu (Monster) (public)
     */
    public Monster monster;
    /**
     * Scanner pour lire les entrées clavier (Scanner) (public)
     */
    public Scanner scanner = new Scanner(System.in);

    /**
     * Méthode principale du jeu
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args)
    {
        ConsoleAPI.warn("LearningSoulsGame is deprecated, use lsg.LearningSoulsGameApplication instead");
        LearningSoulsGame lsg = new LearningSoulsGame();
        lsg.play_v3();
        //lsg.testExceptions();
    }

    /**
     * Méthode pour afficher le titre du jeu
     */
    private void title()
    {
        System.out.println("###############################");
        System.out.println("#   THE LEARNING SOULS GAME   #");
        System.out.println("###############################");
    }

    /**
     * Méthode pour afficher le hero et le monstre
     */
    private void refresh(int turn)
    {
        System.out.println("\nTour n°" + turn + " :");
        hero.printStats();
        System.out.println(monster);
    }

    /**
     * Méthode pour faire combattre 2 personnages
     * @param c1 (Character) : attaquant
     * @param c2 (Character) : défenseur
     * @param action (int) : action à effectuer (0 = choisir avec scanner | 1 = attaquer | 2 = consommer)
     */
    private void fight1v1(Character c1, Character c2, int action)
    {
        if (action == 0) // Choose action with scanner
        {
            System.out.println(c1.getName() + " action's for next move : (1) attack | (2) consume");
            action = scanner.nextInt();
        }

        if (action == 1) // Attack
        {
            try
            {
                int dmg = c1.attack();
                System.out.println(c1.getName() + " attack " + c2.getName() +
                        " with " + c1.getWeapon().getName() + " (" + dmg +
                        ") -> Effective DMG: " + c2.getHitWith(dmg) + " PV");
            }
            catch (WeaponNullException | WeaponBrokenException | StaminaEmptyException e) {
                System.out.println("WARNING: " + c1.getName() + " can't attack (" + e.getMessage() + ")");
                return;
            }

        }

        else if (action == 2) // Use consumable
        {
            try {
                c1.consume();
            } catch (ConsumeNullException | ConsumeEmptyException e) {
                System.out.println("WARNING: " + c1.getName() + " can't consume (" + e.getMessage() + ")");
            } catch (ConsumeRepairNullWeaponException e) {
                System.out.println("WARNING: " + c1.getName() + " can't repair (" + e.getMessage() + ")");
            }
        }

        else { fight1v1(c1, c2, 0); }
    }

    private int checkWinner()
    {
        if (!hero.isAlive())
        {
            System.out.println(monster.getName() + " wins !");
            return 1;
        }
        else if (!monster.isAlive())
        {
            System.out.println(hero.getName() + " wins !");
            return -1;
        }
        return 0;
    }

    private void init()
    {
        title();
        hero = new Hero("Darkbriks");
        hero.setArmorItem(new BlackWitchVeil(), 1);
        hero.setArmorItem(new RingedKnightArmor(), 3);
        hero.setBuffItem(new RingOfSwords(), 1);
        hero.setBuffItem(new RingOfDeath(), 2);
        hero.setConsumable(new Coffee());
        hero.setWeapon(new Sword());
        monster = new Monster();
        monster.setWeapon(new Claw());
    }

    private void play_v1()
    {
        init();
        int win = 0;
        while (win == 0)
        {
            fight1v1(hero, monster, 0);
            win = checkWinner();
            if (win != 0) break;
            fight1v1(monster, hero, 1);
            win = checkWinner();
        }
    }

    private void play_v2()
    {
        init();
        hero.setArmorItem(new BlackWitchVeil(), 1);
        hero.setArmorItem(new RingedKnightArmor(), 3);
        int win = 0;
        while (win == 0)
        {
            fight1v1(hero, monster, 0);
            win = checkWinner();
            if (win != 0) break;
            fight1v1(monster, hero, 1);
            win = checkWinner();
        }
    }

    private void play_v3()
    {
        init();
        monster = new Lycanthrope();
        int win = 0, turn = 1;
        while (win == 0)
        {
            refresh(turn++);
            fight1v1(hero, monster, 0);
            win = checkWinner();
            if (win != 0) break;
            fight1v1(monster, hero, 1);
            win = checkWinner();}
    }

    private void testExceptions()
    {
        init();
        hero.setWeapon(null);
        hero.setConsumable(new RepairKit());
        fight1v1(hero, monster, 2);
    }
}