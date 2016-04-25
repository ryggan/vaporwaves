package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;

import java.awt.*;

public class StupidAI implements AI {
    @Override
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles) {

        return Direction.RIGHT;
    }
}
