package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.Player;

import java.awt.*;
import java.util.Set;

public class CPUPlayer extends Player {

    private PlayerAI playerAI;

    public CPUPlayer(int playerId, String playerName) {
        super(playerId, playerName);
        this.playerAI = new PlayerAI() {
            @Override
            public boolean shouldPutBomb() {
                return false;
            }

            @Override
            public Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies) {
                return null;
            }
        };
    }

    public void setPlayerAI(PlayerAI playerAI) {
        this.playerAI = playerAI;
    }

    public PlayerAI getPlayerAI() {
        return this.playerAI;
    }

}
