package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.Set;

public class EmptyAI implements PlayerAI {
    @Override
    public boolean shouldPutBomb() {
        return false;
    }

    @Override
    public Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies) {
        return null;
    }
}
