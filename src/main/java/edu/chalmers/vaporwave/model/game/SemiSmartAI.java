package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * An AI class meant to be not smart but not entirely dumb either, when comparing
 * with human users.
 * The core of it is the method getNextMove(), and all the rest is all just to
 * cater for that method.
 */
public class SemiSmartAI implements AI {
    private int[][] heuristicMatrix;
    private Set<GameCharacter> gameCharacterSet;
    private Random random;
    private List<NextDirection> directionList;
    private Direction previousDirection;
    private Point enemyPosition;
    private NextDirection upDirection;
    private NextDirection rightDirection;
    private NextDirection leftDirection;
    private NextDirection downDirection;

    public SemiSmartAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
        this.directionList = new ArrayList<>();

        this.random = new Random();
        this.previousDirection = Direction.UP;

        this.upDirection = new NextDirection(Direction.UP, 0);
        this.leftDirection = new NextDirection(Direction.LEFT, 0);
        this.rightDirection = new NextDirection(Direction.RIGHT, 0);
        this.downDirection = new NextDirection(Direction.DOWN, 0);

        this.directionList.add(this.upDirection);
        this.directionList.add(this.leftDirection);
        this.directionList.add(this.rightDirection);
        this.directionList.add(this.downDirection);
    }

    static class NextDirection {

        public NextDirection(Direction dir, int value) {
            this.direction = dir;
            this.value = value;
        }
        public Direction direction;
        public int value;
    }

    public void removeLeastGoodAlternative(List<NextDirection> directionList) {
        for(int i = 0; i < directionList.size() - 1; i++) {
            if(directionList.get(i).value < directionList.get(i+1).value) {
                directionList.remove(i);
                removeLeastGoodAlternative(directionList);
            } else if(directionList.get(i).value > directionList.get(i+1).value) {
                directionList.remove(i+1);
                removeLeastGoodAlternative(directionList);
            } else if(directionList.get(i).value == directionList.get(i+1).value) {
                boolean temp = random.nextBoolean();
                if(temp) {
                    directionList.remove(i);
                } else {
                    directionList.remove(i+1);
                }
                removeLeastGoodAlternative(directionList);
            }
        }
    }

    public Direction getNextMove(Point enemyPosition, StaticTile[][] arenaTiles, Set<Enemy> enemies) {

        this.enemyPosition = enemyPosition;
        this.heuristicMatrix = AIHeuristics.getAIHeuristics(arenaTiles, this.gameCharacterSet, enemies);

        switch(previousDirection) {
            case UP:
                if(enemyPosition.y < 14 && enemyPosition.x < 21) {
                    heuristicMatrix[enemyPosition.x][enemyPosition.y + 1] = 1;
                }
                break;
            case DOWN:
                if(enemyPosition.y > 0) {
                    heuristicMatrix[enemyPosition.x][enemyPosition.y - 1] = 1;
                }
                break;
            case LEFT:
                if(enemyPosition.x < Constants.GAME_WIDTH - 1) {
                    heuristicMatrix[enemyPosition.x + 1][enemyPosition.y] = 1;
                }
                break;
            case RIGHT:
                if(enemyPosition.x > 0 && enemyPosition.y > -1 && enemyPosition.x < 21 && enemyPosition.y < 15) {
                    heuristicMatrix[enemyPosition.x - 1][enemyPosition.y] = 1;
                }
                break;
            default:
                break;
        }

        this.upDirection.value = checkValueUp(enemyPosition);
        this.leftDirection.value = checkValueLeft(enemyPosition);
        this.rightDirection.value = checkValueRight(enemyPosition);
        this.downDirection.value = checkValueDown(enemyPosition);

        List<NextDirection> directions = new ArrayList<>();
        directions.addAll(this.directionList);

        removeLeastGoodAlternative(directions);

        int temp = this.random.nextInt(6);
        if(temp == 0) {
            return takeARandomStep();
        }

        this.previousDirection = directions.get(0).direction;
        return directions.get(0).direction;

    }


    public Direction takeARandomStep() {
        int temp = this.random.nextInt(4);
        if(temp == 0) {
            return Direction.UP;
        } else if(temp == 1) {
            return Direction.RIGHT;
        } else if(temp == 2) {
            return Direction.DOWN;
        } else {
            return Direction.LEFT;
        }
    }


    public int checkValueUp(Point enemyPosition) {
        if (enemyPosition.y > 0) {
            return this.heuristicMatrix[enemyPosition.x][enemyPosition.y - 1];
        } else {
            return -1;
        }
    }

    public int checkValueDown(Point enemyPosition) {
        if (enemyPosition.y < 14) {
            return this.heuristicMatrix[enemyPosition.x][enemyPosition.y + 1];
        } else {
            return -1;
        }
    }

    public int checkValueLeft(Point enemyPosition) {
        if (enemyPosition.x > 0) {
            return this.heuristicMatrix[enemyPosition.x - 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueRight(Point enemyPosition) {
        if (enemyPosition.x < 20) {
            return this.heuristicMatrix[enemyPosition.x + 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueCurrent(Point enemyPosition) {
        if(enemyPosition != null) {
            return this.heuristicMatrix[enemyPosition.x][enemyPosition.y];
        } return 0;
    }

    public Point getCurrentPosition() {
        return this.enemyPosition;
    }
}
