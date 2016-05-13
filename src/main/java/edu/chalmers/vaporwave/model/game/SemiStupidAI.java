package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Utils;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class SemiStupidAI implements AI {

    private Set<GameCharacter> gameCharacterSet;

    private Random random;
    //for testing
    int i = 0;

    public SemiStupidAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
        random = new Random();
    }

    public Direction takeARandomStep() {
        int x = random.nextInt(4);
        switch(x) {
            case 0:
                return Direction.DOWN;
            case 1:
                return Direction.UP;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
        }
        return null;
    }

    private static class NextDirectionClass {
        public int value;
        public Direction direction;
    }

    @Override
    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles) {

        //for testing
        i++;

        NextDirectionClass nextDirection = new NextDirectionClass();
        nextDirection.value = 0;
/*        if(random.nextInt(3) == 1) {
            return takeARandomStep();
        }*/

//        if(i == 2) {
//            // Print the board
//            System.out.println("BOAAAAAAARD");
//            for (int i = 0; i < aiHeuristics.getAIHeuristics().length; i++) {
//                for (int j = 0; j < aiHeuristics.getAIHeuristics()[0].length; j++) {
//                    if (aiHeuristics.getAIHeuristics()[i][j] == 0) {
//                        System.out.print("0   ");
//                    } else if (aiHeuristics.getAIHeuristics()[i][j] < 100) {
//                        System.out.print(aiHeuristics.getAIHeuristics()[i][j] + "  ");
//                    } else {
//                        System.out.print(aiHeuristics.getAIHeuristics()[i][j] + " ");
//                    }
//
//                }
//                System.out.println();
//            }
//        }

        for (int i = 0; i < 4; i++) {
            if(Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]).x >= 0 && Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]).y >= 0
                    && Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]).x <= 20 && Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]).y <= 14) {
                Point positionToCheck = Utils.getRelativePoint(enemyPosition, 1, Utils.getDirectionsAsArray()[i]);
                int heuristicValue = AIHeuristics.getAIHeuristics(arenaTiles, this.gameCharacterSet, enemyPosition)[positionToCheck.x][positionToCheck.y];
                //AIHeuristics.setHeuristicValue(enemyPosition, 1);
                if (heuristicValue > nextDirection.value) {
                    nextDirection.direction = Utils.getDirectionsAsArray()[i];
                    nextDirection.value = AIHeuristics.getAIHeuristics(arenaTiles, this.gameCharacterSet, enemyPosition)[positionToCheck.x][positionToCheck.y];
                }
            } else {
                int x = random.nextInt(4);
                switch(x) {
                    case 0:
                        nextDirection.direction = Direction.DOWN;
                        break;
                    case 1:
                        nextDirection.direction = Direction.UP;
                        break;
                    case 2:
                        nextDirection.direction = Direction.LEFT;
                        break;
                    case 3:
                        nextDirection.direction = Direction.RIGHT;
                        break;
                    default:
                }
            }

        }

/*        System.out.println("Bot does this");
        for (int j = 0; j < 4 ; j++) {
            System.out.print(AIHeuristics.getSpecificHeuristics(enemyPosition)[j] + " ");
        }
        System.out.println("bot did that");*/
        return nextDirection.direction;

    }

}
