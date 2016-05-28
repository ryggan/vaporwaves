package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.Set;

/**
 * This interface makes sure that every AI has a "next move"-method, which is the sole
 * purpose of our AI:s.
 */
public interface AI {
    Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies);
}
