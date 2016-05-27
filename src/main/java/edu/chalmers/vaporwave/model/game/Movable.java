package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.BoundingBox;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Movable {

    private String name;

    private double canvasPositionX;
    private double canvasPositionY;
    private double velocityX;
    private double velocityY;
    private double speed;
    private boolean moving;
    private double damage;
    private List<Direction> lastMove;
    private Direction direction;
    private MovableState comingState;
    private MovableState movableState;
    private MovableState previousState;

    private int previousGridPositionX;
    private int previousGridPositionY;
    private StaticTile[][] latestArenaTiles;

    private double health;
    private int flinchTimer;
    private int flinchDelay;
    private boolean flinchInvincible;
    private int invincibleTimer;
    private int invincibleDelay;

    private BoundingBox boundingBox;

    public Movable(String name, double canvasPositionX, double canvasPositionY, double speed) {

        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.previousGridPositionX = Utils.canvasToGridPositionX(getCanvasPositionX());
        this.previousGridPositionY = Utils.canvasToGridPositionY(getCanvasPositionY());

        this.boundingBox = new BoundingBox(1, 1, Constants.DEFAULT_TILE_WIDTH - 1, Constants.DEFAULT_TILE_HEIGHT - 1);

        this.velocityX = 0;
        this.velocityY = 0;

        this.speed = speed;
        this.name = name;
        this.damage = 0;
        this.health = Constants.DEFAULT_START_HEALTH;

        this.flinchDelay = 20;
        this.invincibleDelay = 60;

        this.lastMove = new ArrayList<>();

        reset(MovableState.IDLE);
    }

    public void reset() {
        reset(MovableState.IDLE);
    }

    public void reset(MovableState state) {
        this.movableState = state;
        this.previousState = this.movableState;
        this.lastMove.clear();
        this.direction = Direction.DOWN;
        this.moving = false;

        this.health = Constants.DEFAULT_START_HEALTH;
        this.flinchTimer = 0;
        this.invincibleTimer = 0;
        this.flinchInvincible = false;
    }

    public void updatePosition() {
        this.previousState = this.movableState;

        if (this.comingState != null) {
            this.movableState = this.comingState;
            this.comingState = null;
        }

        this.canvasPositionX += this.velocityX;
        this.canvasPositionY += this.velocityY;

        moving = (getVelocityX() != 0 || getVelocityY() != 0);

        switch (movableState) {

            case WALK:
                stopOnTileIfNeeded();
                updateInvincibleTimer();
                break;

            case IDLE:
                updateInvincibleTimer();
                break;

            case FLINCH:
                if (flinchTimer > 0) {
                    flinchTimer--;
                } else {
                    setState(MovableState.IDLE);
                    if (Math.round(Utils.gridToCanvasPositionX(getPreviousGridPositionX())) != Math.round(getCanvasPositionX())
                            || Math.round(Utils.gridToCanvasPositionY(getPreviousGridPositionY())) != Math.round(getCanvasPositionY())) {
                        move(direction, latestArenaTiles);
                    }
                }
                break;
            default:
        }

        this.lastMove.clear();
    }

    public void updateInvincibleTimer() {
        if (invincibleTimer > 0) {
            invincibleTimer--;
        } else {
            flinchInvincible = false;
        }
    }

    private void stopAtTile(int newGridPositionX, int newGridPositionY) {

        stop();
        setState(MovableState.IDLE);
        setCanvasPosition(Utils.gridToCanvasPositionX(newGridPositionX), Utils.gridToCanvasPositionY(newGridPositionY));
        setPreviousGridPositionX(newGridPositionX);
        setPreviousGridPositionY(newGridPositionY);
    }

    private void stop() {
        setVelocity(0, 0);
        this.setMoving(false);
    }

    private void stopOnTileIfNeeded() {
        int closestTilePositionX = Utils.canvasToGridPositionX(getCanvasPositionX());
        int closestTilePositionY = Utils.canvasToGridPositionY(getCanvasPositionY());

        double compareX = Math.abs(Utils.gridToCanvasPositionX(closestTilePositionX) - getCanvasPositionX());
        double compareY = Math.abs(Utils.gridToCanvasPositionY(closestTilePositionY) - getCanvasPositionY());

        boolean closeToPosition = (compareX <= this.getSpeed() / 2) && (compareY <= this.getSpeed() / 2)
                && (moving || (closestTilePositionX != getPreviousGridPositionX() || closestTilePositionY != getPreviousGridPositionY()));

        // Stop when in middle of tile
        if(closeToPosition) {
            stopAtTile(closestTilePositionX, closestTilePositionY);

            // Following makes movable keep on moving, to not stop flow
            Direction foundNewDirection = null;
            for(Direction direction : this.lastMove) {
                if (Utils.isOrtogonalDirections(this.direction, direction)) {
                    if (allowMove(direction)) {
                        foundNewDirection = direction;
                        break;
                    } else if (!allowMove(direction) && allowMove(this.direction)) {
                        foundNewDirection = this.direction;
                        break;
                    }
                }
            }
            if (foundNewDirection != null) {
                move(foundNewDirection, this.latestArenaTiles);

                // If no flow needs to be kept, keep on moving in recently pressed direction
            } else {
                for (Direction direction : this.lastMove) {
                    if (allowMove(direction) && !oppositeDirection(direction)) {
                        move(direction, this.latestArenaTiles);
                        break;
                    }
                }
            }
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

    public void idle() {
        setState(MovableState.IDLE);
    }

    public void flinch() {
        stop();
        flinchTimer = flinchDelay;
        invincibleTimer = invincibleDelay;
        flinchInvincible = true;
        setState(MovableState.FLINCH);
    }

    public void death() {
        stop();
        setState(MovableState.DEATH);
        this.health=0;
    }

    public void spawn(Point spawningPoint) {
        this.lastMove.clear();
        this.direction = Direction.DOWN;
        stopAtTile((int)spawningPoint.getX(), (int)spawningPoint.getY());
        setComingState(MovableState.SPAWN);
        this.health = Constants.DEFAULT_START_HEALTH;
    }

    public void move(Direction direction, StaticTile[][] arenaTiles) {
        if (direction != null && !this.lastMove.contains(Utils.getOppositeDirection(direction))) {
            this.lastMove.add(direction);
            this.latestArenaTiles = staticTileMatrixClone(arenaTiles);
            if (this.comingState != MovableState.SPAWN &&
                    (movableState == MovableState.IDLE || (movableState == MovableState.WALK && oppositeDirection(direction)))) {
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
                    default:
                }
                if (getVelocityY() != 0 || getVelocityX() != 0) {
                    setState(MovableState.WALK);
                }
            }
        }
    }

    private StaticTile[][] staticTileMatrixClone(StaticTile[][] matrix) {
        StaticTile[][] newMatrix = new StaticTile[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    public void moveUp() {
        if (allowMove(0, -1) && getCanvasPositionY() > 0) {
            setDirection(Direction.UP);
            setVelocity(0, -this.getSpeed());

        } else if (getState() == MovableState.IDLE) {
            setDirection(Direction.UP);
        }
    }
    public void moveDown() {
        if (allowMove(0, 1) && getCanvasPositionY() < Utils.gridToCanvasPositionX(Constants.DEFAULT_GRID_HEIGHT-1)) {
            setDirection(Direction.DOWN);
            setVelocity(0, this.getSpeed());

        } else if (getState() == MovableState.IDLE) {
            setDirection(Direction.DOWN);
        }
    }
    public void moveLeft() {
        if (allowMove(-1, 0) && getCanvasPositionX() > 0) {
            setDirection(Direction.LEFT);
            setVelocity(-this.getSpeed(), 0);

        } else if (getState() == MovableState.IDLE) {
            setDirection(Direction.LEFT);
        }
    }
    public void moveRight() {
        if (allowMove(1, 0) && getCanvasPositionX() < Utils.gridToCanvasPositionY(Constants.DEFAULT_GRID_WIDTH-1)) {
            setDirection(Direction.RIGHT);
            setVelocity(this.getSpeed(), 0);

        } else if (getState() == MovableState.IDLE) {
            setDirection(Direction.RIGHT);
        }
    }

    private boolean allowMove(int gridDirectionX, int gridDirectionY) {

        // Calculates nearest gridposition
        int nextGridPositionX = Utils.canvasToGridPositionX(getCanvasPositionX());
        int nextGridPositionY = Utils.canvasToGridPositionX(getCanvasPositionY());

        // If no move in horizontal direction, get previous x position as next
        if (gridDirectionX == 0) {
            nextGridPositionX = getPreviousGridPositionX();

        // Only add grid-direction paramater if calculated gridposition above isn't in front of you
        } else if (((int)Utils.gridToCanvasPositionX(nextGridPositionX) < (int)getCanvasPositionX() && gridDirectionX > 0)
                || ((int)Utils.gridToCanvasPositionX(nextGridPositionX) > (int)getCanvasPositionX() && gridDirectionX < 0)
                || (int)Utils.gridToCanvasPositionX(nextGridPositionX) == (int)getCanvasPositionX()) {
            nextGridPositionX += gridDirectionX;
        }
        // Same same with Y as with X
        if (gridDirectionY == 0) {
            nextGridPositionY = getPreviousGridPositionY();
        } else if (((int)Utils.gridToCanvasPositionY(nextGridPositionY) < (int)getCanvasPositionY() && gridDirectionY > 0)
                || ((int)Utils.gridToCanvasPositionY(nextGridPositionY) > (int)getCanvasPositionY() && gridDirectionY < 0)
                || (int)Utils.gridToCanvasPositionY(nextGridPositionY) == (int)getCanvasPositionY()) {
            nextGridPositionY += gridDirectionY;
        }

        // When correct next gridposition has been calculated, return false if outside screen
        if (nextGridPositionX < 0 || nextGridPositionX > Constants.DEFAULT_GRID_WIDTH-1
                || nextGridPositionY < 0 || nextGridPositionY > Constants.DEFAULT_GRID_HEIGHT-1) {
            return false;
        }

        // At last, check if gridposition in arena is walkable, and return true if so
        StaticTile nextTile = this.latestArenaTiles[nextGridPositionX][nextGridPositionY];
        return !(nextTile instanceof Wall) && !(nextTile instanceof Bomb) && (!(nextTile instanceof DoubleTile)
                || (!((DoubleTile) nextTile).contains(Wall.class)) && !((DoubleTile) nextTile).contains(Bomb.class));
    }

    private boolean allowMove(double gridDirectionX, double gridDirectionY) {
        return this.allowMove((int)gridDirectionX, (int)gridDirectionY);
    }

    private boolean allowMove(Direction direction) {
        Point directionToPoint = Utils.getRelativePoint(new Point(0, 0), 1, direction);
        return allowMove(directionToPoint.getX(), directionToPoint.getY());
    }

    private boolean oppositeDirection(Direction direction) {
        return (((direction.equals(Direction.UP) && getDirection() == Direction.DOWN)
                || (direction.equals(Direction.DOWN) && getDirection() == Direction.UP)
                || (direction.equals(Direction.RIGHT) && getDirection() == Direction.LEFT)
                || (direction.equals(Direction.LEFT) && getDirection() == Direction.RIGHT))
                && !(this.latestArenaTiles[getPreviousGridPositionX()][getPreviousGridPositionY()] instanceof Bomb));
    }

    public void dealDamage(double damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = Constants.DEFAULT_START_HEALTH;
            death();
        } else {
            flinch();
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setState(MovableState state) {
        this.movableState = state;
    }

    public void setComingState(MovableState state) {
        this.comingState = state;
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

    public double getDamage() {
        return this.damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return this.health;
    }

    public boolean isInvincible() {
        return this.flinchInvincible;
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(this.boundingBox.getMinX() + this.getCanvasPositionX(),
                this.boundingBox.getMinY() + this.getCanvasPositionY(), this.boundingBox.getWidth(), this.boundingBox.getHeight());
    }

    public boolean intersects(Movable movable) {
        return this.getBoundingBox().intersects(movable.getBoundingBox());
    }

    public boolean hasChangedState() {
        return (this.movableState != this.previousState);
    }

    @Override
    public String toString() {
        return "Movable[ Name="+getName()+"; State="+getState()+"; Velocity=["+getVelocityX()+", "+getVelocityY()+"] ]";
    }

    @Override
    public int hashCode(){
        return ((int) canvasPositionX * 5) +
                ((int) canvasPositionY * 7) +
                ((int) velocityX * 11) +
                ((int) velocityY * 13) +
                ((int) speed * 17) +
                ((int)damage * 19) +
                (previousGridPositionX * 23) +
                (previousGridPositionY * 29) +
                ((int) health * 31) +
                (flinchTimer * 37) +
                (flinchDelay * 41) +
                (name.hashCode() + 43);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Movable){
            Movable other = (Movable) o;
            if (this.name.equals(other.name)) {
                return super.equals(o);
            }
        }
        return false;
    }

}