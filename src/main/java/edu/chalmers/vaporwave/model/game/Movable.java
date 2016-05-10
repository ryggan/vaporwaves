package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.BoundingBox;

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
        this.moving = false;

        this.speed = speed;
        this.name = name;
        this.damage = 0;
        this.health = Constants.DEFAULT_START_HEALTH;

        this.flinchDelay = 20;
        this.flinchInvincible = false;
        this.invincibleDelay = 60;

        this.direction = Direction.DOWN;
        this.movableState= MovableState.IDLE;
        this.previousState = this.movableState;
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

            case IDLE:
                if (invincibleTimer > 0) {
                    invincibleTimer--;
                } else {
                    flinchInvincible = false;
                }
                break;

            case FLINCH:
                if (flinchTimer > 0) {
                    flinchTimer--;
                } else {
                    setState(MovableState.IDLE);
                    if (Utils.gridToCanvasPositionX(getPreviousGridPositionX()) != getCanvasPositionX()
                            || Utils.gridToCanvasPositionY(getPreviousGridPositionY()) != getCanvasPositionY()) {
                        move(direction, latestArenaTiles);
                    }
                }
                break;
        }

        this.lastMove = null;
    }

    private void stopAtTile(int newGridPositionX, int newGridPositionY) {

        stop();
        setState(MovableState.IDLE);
        setCanvasPosition(Utils.gridToCanvasPositionX(newGridPositionX), Utils.gridToCanvasPositionY(newGridPositionY));
        setPreviousGridPositionX(newGridPositionX);
        setPreviousGridPositionY(newGridPositionY);

        if (this.lastMove != null) {
            move(lastMove, this.latestArenaTiles);
        }
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

        if(closeToPosition) {
            stopAtTile(closestTilePositionX, closestTilePositionY);
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
//        System.out.println("Flinch! "+getName());
    }

    public void death() {
        stop();
        setState(MovableState.DEATH);
        this.health=0;
//        System.out.println("Death! "+getName());
    }

    public void spawn(Point spawningPoint) {
        this.lastMove = null;
        this.direction = Direction.DOWN;
        stopAtTile((int)spawningPoint.getX(), (int)spawningPoint.getY());
        setComingState(MovableState.SPAWN);
        this.health = Constants.DEFAULT_START_HEALTH;
    }

    public void move(Direction direction, StaticTile[][] arenaTiles) {
        this.lastMove = direction;
        this.latestArenaTiles = arenaTiles;
        if (direction != null && this.comingState != MovableState.SPAWN &&
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
            }
            if (getVelocityY() != 0 || getVelocityX() != 0) {
                setState(MovableState.WALK);
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
        if (allowMove(0, 1) && getCanvasPositionY() < Utils.gridToCanvasPositionX(Constants.DEFAULT_GRID_HEIGHT-1)) {
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
        if (allowMove(1, 0) && getCanvasPositionX() < Utils.gridToCanvasPositionY(Constants.DEFAULT_GRID_WIDTH-1)) {
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

    public boolean getMoving() {
        return this.moving;
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

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return this.health;
    }

    public void setFlinchDelay(int delay) {
        this.flinchDelay = delay;
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
//        System.out.println("has changed? "+(movableState != previousState)+" cur state: "+movableState+", prev state: "+previousState);
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
                (damage * 19) +
                (previousGridPositionX * 23) +
                (previousGridPositionY * 29) +
                ((int) health * 31) +
                (flinchTimer * 37) +
                (flinchDelay * 41) +
                (name.hashCode() + 43);

    }
}