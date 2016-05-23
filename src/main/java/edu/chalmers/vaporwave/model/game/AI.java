package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.game.StaticTile;

import java.awt.*;
import java.util.Set;

public interface AI {
    public Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies);
}
