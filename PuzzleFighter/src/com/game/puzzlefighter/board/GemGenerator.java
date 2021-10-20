package com.game.puzzlefighter.board;

import java.util.Random;

/**
 * This class is used to generate random gems. It uses a certain random seeds to
 * generate random values.
 */
public class GemGenerator {

    private final Random random;

    /**
     * Constructor
     *
     * @param seed random seed
     */
    public GemGenerator(long seed) {
        random = new Random(seed);
    }

    /**
     * @return random gem
     */
    public Gem randomGem() {
        int color = random.nextInt(4) + 1;
        int type = 0;
        int chance = random.nextInt(100) + 1;

        // chance for the gem to spawn of certain type
        if (chance <= 80)
            type = Gem.NORMAL;
        else if (chance <= 95)
            type = Gem.CRASH;
        else
            type = Gem.DIAMOND;

        return new Gem(color, type);
    }
}
