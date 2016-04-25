package edu.chalmers.vaporwave.model.gameObjects;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.util.CharacterState;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Movable {

    private double canvasPositionX;
    private double canvasPositionY;
    private double velocityX;
    private double velocityY;
    private double speed;
    private boolean moving;
    private Direction direction;
    private CharacterState characterState;

    private int previousGridPositionX;
    private int previousGridPositionY;
    private StaticTile[][] latestArenaTiles;

    public Movable(double canvasPositionX, double canvasPositionY, double speed) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.speed = speed;
        this.moving = false;

        this.previousGridPositionX = Utils.canvasToGridPosition(getCanvasPositionX());
        this.previousGridPositionY = Utils.canvasToGridPosition(getCanvasPositionY());

        this.moving = false;

        this.characterState = CharacterState.IDLE;
    }

    public void updatePosition() {
        this.canvasPositionX += this.velocityX;
        this.canvasPositionY += this.velocityY;
        if (characterState == CharacterState.WALK) {
            stopOnTileIfNeeded();
        }
        moving = (getVelocityX() != 0 || getVelocityY() != 0);

        if (characterState == CharacterState.WALK) {
            stopOnTileIfNeeded();
        }

    }

    private void stop(int newGridPositionX, int newGridPositionY) {

        characterState = CharacterState.IDLE;
        setVelocity(0, 0);
        setCanvasPosition(Utils.gridToCanvasPosition(newGridPositionX), Utils.gridToCanvasPosition(newGridPositionY));
        setPreviousGridPositionX(newGridPositionX);
        setPreviousGridPositionY(newGridPositionY);

        List<String> input = ListenerController.getInstance().getInput();
        if (input.size() > 0 && (input.contains("UP") || input.contains("DOWN") || input.contains("LEFT") || input.contains("RIGHT"))) {
            move(input.get(input.size()-1), this.latestArenaTiles);
        }
    }

    private void stopOnTileIfNeeded() {
        int closestTilePositionX = Utils.canvasToGridPosition(getCanvasPositionX());
        int closestTilePositionY = Utils.canvasToGridPosition(getCanvasPositionY());

        double compareX = Math.abs(Utils.gridToCanvasPosition(closestTilePositionX) - getCanvasPositionX());
        double compareY = Math.abs(Utils.gridToCanvasPosition(closestTilePositionY) - getCanvasPositionY());

        boolean closeToPosition = (compareX <= this.getSpeed() / 2) && (compareY <= this.getSpeed() / 2)
                && (moving || (closestTilePositionX != getPreviousGridPositionX() || closestTilePositionY != getPreviousGridPositionY()));

        if(closeToPosition) {
            stop(closestTilePositionX, closestTilePositionY);
        }
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public double getVelocityX() {
        return this.velocityX;
    }
    public double getVelocityY() {
        return this.velocityY;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void death() {
        if (characterState == CharacterState.IDLE) {
            characterState = CharacterState.DEATH;
        }
    }

    public void spawn() {
        if (characterState == CharacterState.IDLE) {
            characterState = CharacterState.SPAWN;
        }
    }

    public void move(String key, StaticTile[][] arenaTiles) {
        this.latestArenaTiles = arenaTiles;
        if (characterState != CharacterState.WALK || oppositeDirection(key)) {
            switch (key) {
                case "UP":
                    moveUp();
                    break;
                case "LEFT":
                    moveLeft();
                    break;
                case "DOWN":
                    moveDown();
                    break;
                case "RIGHT":
                    moveRight();
                    break;
            }
            if (getVelocityY() != 0 || getVelocityX() != 0) {
                characterState = CharacterState.WALK;
            }
        }
    }

    public void moveUp() {
        setDirection(Direction.UP);
        if (allowMove(0, -1) && getCanvasPositionY() > 0) {
            setVelocity(0, -this.getSpeed());
        }
    }
    public void moveDown() {
        setDirection(Direction.DOWN);
        if (allowMove(0, 1) && getCanvasPositionY() < Utils.gridToCanvasPosition(Constants.DEFAULT_GRID_HEIGHT-1)) {
            setVelocity(0, this.getSpeed());
        }
    }
    public void moveLeft() {
        setDirection(Direction.LEFT);
        if (allowMove(-1, 0) && getCanvasPositionX() > 0) {
            setVelocity(-this.getSpeed(), 0);
        }
    }
    public void moveRight() {
        setDirection(Direction.RIGHT);
        if (allowMove(1, 0) && getCanvasPositionX() < Utils.gridToCanvasPosition(Constants.DEFAULT_GRID_WIDTH-1)) {
            setVelocity(this.getSpeed(), 0);
        }
    }

    private boolean allowMove(int gridDirectionX, int gridDirectionY) {
        int nextGridPositionX = Math.min(Math.max(getPreviousGridPositionX() + gridDirectionX, 0), Constants.DEFAULT_GRID_WIDTH-1);
        int nextGridPositionY = Math.min(Math.max(getPreviousGridPositionY() + gridDirectionY, 0), Constants.DEFAULT_GRID_HEIGHT-1);
        StaticTile nextTile = this.latestArenaTiles[nextGridPositionX][nextGridPositionY];

        return nextTile == null || !(nextTile instanceof Wall) && !(nextTile instanceof Bomb);
    }

    private boolean oppositeDirection(String key) {
        return (key.equals("UP") && getDirection() == Direction.DOWN)
                || (key.equals("DOWN") && getDirection() == Direction.UP)
                || (key.equals("RIGHT") && getDirection() == Direction.LEFT)
                || (key.equals("LEFT") && getDirection() == Direction.RIGHT);
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean getMoving() {
        return this.moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public CharacterState getState() {
        return this.characterState;
    }

    public Point getGridPosition() {
        return new Point(Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()));
    }

    public int getPreviousGridPositionX() {
        return this.previousGridPositionX;
    }

    public int getPreviousGridPositionY() {
        return this.previousGridPositionY;
    }

    public void setPreviousGridPositionX(int previousGridPositionX) {
        this.previousGridPositionX = previousGridPositionX;
    }

    public void setPreviousGridPositionY(int previousGridPositionY) {
        this.previousGridPositionY = previousGridPositionY;
    }

    public void setCanvasPosition(double canvasPositionX, double canvasPositionY) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
    }

    public double getCanvasPositionX() {
        return this.canvasPositionX;
    }
    public double getCanvasPositionY() {
        return this.canvasPositionY;
    }
}
