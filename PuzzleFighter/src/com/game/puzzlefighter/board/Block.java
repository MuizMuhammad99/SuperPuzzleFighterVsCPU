package com.game.puzzlefighter.board;

import com.game.animation.Animation;

import java.util.ArrayList;

/**
 * Defines a block gem.
 */
public class Block {

    /**
     * Constructor
     *
     * @param gems     the gems forming the block
     * @param startRow start row of block
     * @param startCol start column of block
     * @param endRow   end row of block
     * @param endCol   end column of block
     */
    public Block(ArrayList<Gem> gems, int startRow, int startCol, int endRow, int endCol) {

        // set type of gems to BLOCK & set animation state same for all block gems
        Animation animation = gems.get(0).getBlockAnimation();
        for (Gem gem : gems) {
            gem.setBlock(this);
            gem.getBlockAnimation().copyState(animation);
        }
    }

    //getters

}
