package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Utils;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class StupidAI implements AI {
    private Direction previousDirection;

    public StupidAI() {
        Random random = new Random();
        int nextRandom = random.nextInt(4);

        previousDirection = Utils.getDirectionsAsArray()[nextRandom];
    }

    @Override
    public Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies) {

        Random random = new Random();
        int nextRandom = random.nextInt(10);

        if (nextRandom < 8) {
            previousDirection = Utils.getDirectionsAsArray()[nextRandom / 2];
        }

        return previousDirection;
    }
}
