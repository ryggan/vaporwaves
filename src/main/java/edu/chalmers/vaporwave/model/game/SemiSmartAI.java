package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.List;

public class SemiSmartAI implements AI {
    private int[][] heuristicMatrix;
    private Set<GameCharacter> gameCharacterSet;
    private Random random = new Random();
    private List<NextDirection> directionList;
    private Direction previousDirection = Direction.UP;
    private Point enemyPosition;
    private NextDirection upDirection;
    private NextDirection rightDirection;
    private NextDirection leftDirection;
    private NextDirection downDirection;

    public SemiSmartAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
        directionList = new ArrayList<>();

        upDirection = new NextDirection(Direction.UP, 0);
        leftDirection = new NextDirection(Direction.LEFT, 0);
        rightDirection = new NextDirection(Direction.RIGHT, 0);
        downDirection = new NextDirection(Direction.DOWN, 0);
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

        directionList.clear();
        this.enemyPosition = enemyPosition;

        heuristicMatrix = AIHeuristics.getAIHeuristics(arenaTiles, gameCharacterSet, enemies);

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

        upDirection.direction = Direction.UP;
        upDirection.value = checkValueUp(enemyPosition);
        leftDirection.direction = Direction.LEFT;
        leftDirection.value = checkValueLeft(enemyPosition);
        rightDirection.direction = Direction.RIGHT;
        rightDirection.value = checkValueRight(enemyPosition);
        downDirection.direction = Direction.DOWN;
        downDirection.value = checkValueDown(enemyPosition);

        directionList.add(upDirection);
        directionList.add(leftDirection);
        directionList.add(rightDirection);
        directionList.add(downDirection);

        removeLeastGoodAlternative(directionList);

        int temp = random.nextInt(6);
        if(temp == 0) {
            return takeARandomStep();
        }

        previousDirection = directionList.get(0).direction;
        return directionList.get(0).direction;

    }


    public Direction takeARandomStep() {
        int temp = random.nextInt(4);
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
            return heuristicMatrix[enemyPosition.x][enemyPosition.y - 1];
        } else {
            return -1;
        }
    }

    public int checkValueDown(Point enemyPosition) {
        if (enemyPosition.y < 14) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y + 1];
        } else {
            return -1;
        }
    }

    public int checkValueLeft(Point enemyPosition) {
        if (enemyPosition.x > 0) {
            return heuristicMatrix[enemyPosition.x - 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueRight(Point enemyPosition) {
        if (enemyPosition.x < 20) {
            return heuristicMatrix[enemyPosition.x + 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueCurrent(Point enemyPosition) {
        if(enemyPosition != null) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y];
        } return 0;
    }

    public Point getCurrentPosition() {
        return this.enemyPosition;
    }
}
