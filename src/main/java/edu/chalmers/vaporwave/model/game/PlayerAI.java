package edu.chalmers.vaporwave.model.game;

/**
 * This interface makes sure that every PlayerAI has a method for placing bombs,
 * which is the only addition to AI.
 */
public interface PlayerAI extends AI {
    boolean shouldPutBomb();
}
