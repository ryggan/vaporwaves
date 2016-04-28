package edu.chalmers.vaporwave.model.gameObjects;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Utils;

import java.awt.*;

public abstract class Movable {

    private String name;

    private double canvasPositionX;
    private double canvasPositionY;
    private double velocityX;
    private double velocityY;
    private double speed;
    private boolean moving;
    private int damage;
    private Direction lastMove;
    private Direction direction;
    private MovableState movableState;

    private int previousGridPositionX;
    private int previousGridPositionY;
    private StaticTile[][] latestArenaTiles;

    protected Movable() { }

    public Movable(String name, double canvasPositionX, double canvasPositionY, double speed) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.speed = speed;
        this.moving = false;
        this.name = name;
        this.damage = 30;

        this.previousGridPositionX = Utils.canvasToGridPosition(getCanvasPositionX());
        this.previousGridPositionY = Utils.canvasToGridPosition(getCanvasPositionY());

        this.moving = false;

        this.movableState = MovableState.IDLE;
    }

    public void updatePosition() {
        this.canvasPositionX += this.velocityX;
        this.canvasPositionY += this.velocityY;
        if (movableState == MovableState.WALK) {
            stopOnTileIfNeeded();
        }
        moving = (getVelocityX() != 0 || getVelocityY() != 0);

        if (movableState == MovableState.WALK) {
            stopOnTileIfNeeded();
        }

        this.lastMove = null;
    }

    private void stop(int newGridPositionX, int newGridPositionY) {

        movableState = MovableState.IDLE;
        setVelocity(0, 0);
        setCanvasPosition(Utils.gridToCanvasPosition(newGridPositionX), Utils.gridToCanvasPosition(newGridPositionY));
        setPreviousGridPositionX(newGridPositionX);
        setPreviousGridPositionY(newGridPositionY);

        if (this.lastMove != null) {
            move(lastMove, this.latestArenaTiles);
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
//        if (movableState == movableState.IDLE) {
        this.setMoving(false);
        movableState = movableState.DEATH;
//        }
    }

    public void spawn() {
        if (movableState == movableState.IDLE) {
            movableState = movableState.SPAWN;
        }
    }

    public void move(Direction direction, StaticTile[][] arenaTiles) {
        this.lastMove = direction;
        this.latestArenaTiles = arenaTiles;
        if (direction != null && (movableState != movableState.WALK || oppositeDirection(direction))) {
            switch (direction) {
                case UP:
                    moveUp();
                    break;
                case LEFT:
                    moveLeft();
                    break;
                case DOWN:
                    moveDown();
                    break;
                case RIGHT:
                    moveRight();
                    break;
            }
            if (getVelocityY() != 0 || getVelocityX() != 0) {
                movableState = movableState.WALK;
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

    private boolean oppositeDirection(Direction direction) {
        return (((direction.equals(Direction.UP) && getDirection() == Direction.DOWN)
                || (direction.equals(Direction.DOWN) && getDirection() == Direction.UP)
                || (direction.equals(Direction.RIGHT) && getDirection() == Direction.LEFT)
                || (direction.equals(Direction.LEFT) && getDirection() == Direction.RIGHT))
                && !(this.latestArenaTiles[getPreviousGridPositionX()][getPreviousGridPositionY()] instanceof Bomb));
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

    public MovableState getState() {
        return this.movableState;
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

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return this.damage;
    }
}
