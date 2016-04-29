package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.game.StaticTile;

import java.awt.*;
import java.util.Random;

public class StupidPlayerAI implements PlayerAI {

    private Direction previousDirection;

    public StupidPlayerAI() {
        Random random = new Random();
        int nextRandom = random.nextInt(4);

        previousDirection = Utils.getDirectionsAsArray()[nextRandom];
    }

    @Override
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles) {

        Random random = new Random();
        int nextRandom = random.nextInt(10);

        if (nextRandom < 8) {
            previousDirection = Utils.getDirectionsAsArray()[nextRandom / 2];
        }

        return previousDirection;
    }

    @Override
    public boolean shouldPutBomb(){
        Random random = new Random();
        int nextRandom = random.nextInt(1000);
        if(nextRandom==1){
            return true;
        } else {
            return false;
        }
    }
}
