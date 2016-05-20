package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.List;
/**
 * Created by FEngelbrektsson on 13/05/16.
 */
public class FelixTestAI implements AI {
    private int[][] heuristicMatrix;
    private Set<GameCharacter> gameCharacterSet;
    Random r = new Random();
    private List<NextDirection> directionList;
    private Direction previousDirection = Direction.UP;

    public FelixTestAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
    }

    static class NextDirection {

        public NextDirection(Direction dir, int value) {
            this.direction = dir;
            this.value = value;
        }
        public Direction direction;
        public int value;
    }

    public void removeWeak(List<NextDirection> directionList) {
        for(int i = 0; i < directionList.size() - 1; i++) {
            if(directionList.get(i).value < directionList.get(i+1).value) {
                directionList.remove(i);
                removeWeak(directionList);
            } else if(directionList.get(i).value > directionList.get(i+1).value) {
                directionList.remove(i+1);
                removeWeak(directionList);
            } else if(directionList.get(i).value == directionList.get(i+1).value) {
                boolean temp = r.nextBoolean();
                if(temp) {
                    directionList.remove(i);
                } else {
                    directionList.remove(i+1);
                }
                removeWeak(directionList);
            }
        }
    }

    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles, Point enemyPreviousPosition) {

        directionList = new ArrayList<>();

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

        removeWeak(directionList);

        //System.out.println("final champion was " + directionList.get(0).value + " at direction " + directionList.get(0).direction);
        //System.out.println(directionList.get(0).direction + " is the current direction because " + directionList.get(0).value + " is the value");
        previousDirection = directionList.get(0).direction;
        return directionList.get(0).direction;
/*        int largest = 0;
        int winner = 2;
        for (int i = 0; i < checkedValues.length; i++) {
            if(checkedValues[i] > largest) {
               largest = checkedValues[i];
                winner = i;
            } else if(largest > 0 && checkedValues[i] == largest) {
                System.out.println("we are tied at " + largest);
                return takeARandomStep();
            }
        }


        if(winner == 0) {
            nextDirection = Direction.UP;
        } else if(winner == 1) {
            nextDirection = Direction.RIGHT;
        } else if(winner == 2) {
            nextDirection = Direction.DOWN;
        } else if(winner == 3) {
            nextDirection = Direction.LEFT;
        }*/

/*        System.out.println("bot is currently at position " + enemyPosition.x + "," + enemyPosition.y);
        System.out.println(nextDirection + " because " + checkedValues[winner] + " is the highest value");*/
/*        return nextDirection;*/
    }


    public Direction takeARandomStep() {
        int temp = r.nextInt(4);
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
        if(enemyPosition.y < 14) {
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
        if(enemyPosition.x < 20) {
            return heuristicMatrix[enemyPosition.x + 1][enemyPosition.y];
        } else {
            return -1;
        }
    }


}
