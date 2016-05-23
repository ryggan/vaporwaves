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

    public SemiSmartAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
        directionList = new ArrayList<>();
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
                removeLeastGoodAlternative(directionList); // todo: ask christer about naming conventions
            }
        }
    }

    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles, Point enemyPreviousPosition) {

        directionList.clear();
        this.enemyPosition = enemyPosition;

        heuristicMatrix = AIHeuristics.getAIHeuristics(arenaTiles, gameCharacterSet, enemyPreviousPosition);

        switch(previousDirection) {
            case UP:
                if(enemyPosition.y < 14) {
                    heuristicMatrix[enemyPosition.x][enemyPosition.y + 1] = 1;
                }
                break;
            case DOWN:
                if(enemyPosition.y > 0) {
                    heuristicMatrix[enemyPosition.x][enemyPosition.y - 1] = 1;
                }
                break;
            case LEFT:
                if(enemyPosition.x < 20) {
                    heuristicMatrix[enemyPosition.x + 1][enemyPosition.y] = 1;
                }
                break;
            case RIGHT:
                if(enemyPosition.x > 0) {
                    heuristicMatrix[enemyPosition.x - 1][enemyPosition.y] = 1;
                }
                break;
            default:
                break;
        }

        NextDirection upDirection = new NextDirection(Direction.UP, checkValueUp(enemyPosition));
        NextDirection leftDirection = new NextDirection(Direction.LEFT, checkValueLeft(enemyPosition));
        NextDirection rightDirection = new NextDirection(Direction.RIGHT, checkValueRight(enemyPosition));
        NextDirection downDirection = new NextDirection(Direction.DOWN, checkValueDown(enemyPosition));
        directionList.add(upDirection);
        directionList.add(leftDirection);
        directionList.add(rightDirection);
        directionList.add(downDirection);

        removeLeastGoodAlternative(directionList);

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
        if(enemyPosition.y > 0) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y - 1];
        } else {
            return -1;
        }
    }

    public int checkValueDown(Point enemyPosition) {
        if(enemyPosition.y < Constants.DEFAULT_GRID_HEIGHT - 1) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y + 1];
        } else {
            return -1;
        }
    }

    public int checkValueLeft(Point enemyPosition) {
        if(enemyPosition.x > 0) {
            return heuristicMatrix[enemyPosition.x - 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueRight(Point enemyPosition) {
        if(enemyPosition.x < Constants.DEFAULT_GRID_WIDTH - 1) {
            return heuristicMatrix[enemyPosition.x + 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueCurrent(Point enemyPosition) {
        return heuristicMatrix[enemyPosition.x][enemyPosition.y];
    }

    public Point getCurrentPosition() {
        return this.enemyPosition;
    }
}
