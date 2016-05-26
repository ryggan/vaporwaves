package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.Enemy;
import edu.chalmers.vaporwave.model.game.PlayerAI;
import edu.chalmers.vaporwave.model.game.StaticTile;

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
