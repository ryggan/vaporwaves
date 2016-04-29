package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.game.StaticTile;

import java.awt.*;
import java.util.Set;

public class SemiStupidAI implements AI {

    private Set<GameCharacter> gameCharacterSet;

    public SemiStupidAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
    }

    @Override
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles) {
        AIHeuristics aiHeuristics = new AIHeuristics(arenaTiles, this.gameCharacterSet);

        class NextDirectionClass {
            public int value;
            public Direction direction;
        }

        NextDirectionClass nextDirection = new NextDirectionClass();
        nextDirection.value = 0;

//        // Print the board
//        for (int i = 0; i < aiHeuristics.getAIHeuristics().length; i++) {
//            for (int j = 0; j < aiHeuristics.getAIHeuristics()[0].length; j++) {
//                if (aiHeuristics.getAIHeuristics()[i][j] == 0) {
//                    System.out.print("0   ");
//                } else if (aiHeuristics.getAIHeuristics()[i][j] < 100){
//                    System.out.print(aiHeuristics.getAIHeuristics()[i][j] + "  ");
//                } else {
//                    System.out.print(aiHeuristics.getAIHeuristics()[i][j] + " ");
//                }
//
//            }
//            System.out.println();
//        }

        for (int i = 0; i < 4; i++) {
            Point positionToCheck = Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]);
            int heuristicValue = aiHeuristics.getAIHeuristics()[positionToCheck.x][positionToCheck.y];
            aiHeuristics.setHeuristicValue(enemyPosition, 1);
            if (heuristicValue > nextDirection.value) {
                nextDirection.direction = Utils.getDirectionsAsArray()[i];
                nextDirection.value = aiHeuristics.getAIHeuristics()[positionToCheck.x][positionToCheck.y];
            }

        }


        return nextDirection.direction;

    }

}
