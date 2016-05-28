package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.game.EmptyAI;
import edu.chalmers.vaporwave.model.game.PlayerAI;

/**
 * This class extends Player, and it's only purpose is to hold an AI. The reason to this
 * is that when playing with AI-controlled GameCharacters, the only difference should be
 * that the input comes from AI instead of human user.
 */
public class CPUPlayer extends Player {

    private PlayerAI playerAI;

    public CPUPlayer(int playerId, String playerName) {
        super(playerId, playerName);

        this.playerAI = new EmptyAI();
    }

    public void setPlayerAI(PlayerAI playerAI) {
        this.playerAI = playerAI;
    }

    public PlayerAI getPlayerAI() {
        return this.playerAI;
    }

    public boolean equals(Object otherObject) {
        return super.equals(otherObject);
    }

    public int hashCode() {
        return super.hashCode();
    }

}
