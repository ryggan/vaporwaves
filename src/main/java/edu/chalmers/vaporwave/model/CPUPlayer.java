package edu.chalmers.vaporwave.model;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.Enemy;
import edu.chalmers.vaporwave.model.game.PlayerAI;
import edu.chalmers.vaporwave.model.game.StaticTile;

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

    public boolean equals(Object otherObject) {
        return super.equals(otherObject);
    }

    public int hashCode() {
        return super.hashCode();
    }

}
