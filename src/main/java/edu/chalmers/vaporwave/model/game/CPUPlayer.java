package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.model.Player;

public class CPUPlayer extends Player {

    private PlayerAI playerAI;

    public CPUPlayer(int playerId, String playerName, PlayerAI playerAI) {
        super(playerId, playerName);
        this.playerAI = playerAI;
    }

    public PlayerAI getPlayerAI() {
        return this.playerAI;
    }

}
