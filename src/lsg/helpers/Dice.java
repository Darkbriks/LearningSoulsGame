package lsg.helpers;

import java.util.Random;

public class Dice
{
    private final int faces;
    private Random random;

    public Dice(int faces)
    {
        this.faces = faces;
        random = new Random();
        random.setSeed(55555);
    }

    public int roll() { return random.nextInt(faces); }
}
