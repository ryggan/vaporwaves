package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.game.StaticTile;

import java.awt.*;

public interface AI {
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles);
}
