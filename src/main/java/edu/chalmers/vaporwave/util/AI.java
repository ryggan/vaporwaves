package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;

import java.awt.*;

public interface AI {
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles);
    public boolean shouldPutBomb();

}
