package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.CharacterSpriteProperties;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.Image;
import org.w3c.dom.NodeList;

import java.util.List;

public class GameCharacter extends Movable {

    private String name;

    private Player player;
    private int playerId;

    private Directions direction;
    private CharacterState characterState;
    private double previousGridPositionX;
    private double previousGridPositionY;

    private double maxHealth;
    private double health;
    private double speed;
    private int bombRange;
    private int bombCount;

    public GameCharacter(String name) {

        this.name = name;

        // Test settings setup:
//        setGeneralPosition(5, 5);
        setCanvasPosition(Utils.gridToCanvasPosition(5), Utils.gridToCanvasPosition(5));
        characterState = CharacterState.IDLE;
        direction = Directions.DOWN;
        speed = 0.6;
    }

    public void placeBomb() {
        GameEventBus.getInstance().post(new PlaceBombEvent(Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY())));
    }

    public void move(String key) {
        if (moveAllowed(key)) {
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
            if (getVelocityY() != 0 || getVelocityX() != 0)
                characterState = CharacterState.WALK;
        }
    }

    private boolean moveAllowed(String key) {
//        int closestTilePositionX = (int)Math.round(getCanvasPositionX() / Constants.DEFAULT_TILE_WIDTH);
//        int closestTilePositionY = (int)Math.round(getCanvasPositionY() / Constants.DEFAULT_TILE_HEIGHT);
        int closestTilePositionX = Utils.canvasToGridPosition(getCanvasPositionX());
        int closestTilePositionY = Utils.canvasToGridPosition(getCanvasPositionY());

//        return (characterState != CharacterState.WALK || (closestTilePositionX == getGridPositionX() && closestTilePositionY == getGridPositionY())) || oppositeDirection(key);
        return characterState != CharacterState.WALK || oppositeDirection(key);
    }

    private boolean oppositeDirection(String key) {
        return (key.equals("UP") && direction == Directions.DOWN)
                || (key.equals("DOWN") && direction == Directions.UP)
                || (key.equals("RIGHT") && direction == Directions.LEFT)
                || (key.equals("LEFT") && direction == Directions.RIGHT);
    }

    public void moveUp() {
        direction = Directions.UP;
//        if (getGridPositionY() > 0)
        if (Utils.canvasToGridPosition(getCanvasPositionY()) > 0)
            setVelocity(0, -this.speed);
    }
    public void moveDown() {
        direction = Directions.DOWN;
//        if (getGridPositionY() < Constants.DEFAULT_GRID_HEIGHT-1)
        if (Utils.canvasToGridPosition(getCanvasPositionY()) < Constants.DEFAULT_GRID_HEIGHT-1)
            setVelocity(0, this.speed);
    }
    public void moveLeft() {
        direction = Directions.LEFT;
//        if (getGridPositionX() > 0)
        if (Utils.canvasToGridPosition(getCanvasPositionX()) > 0)
            setVelocity(-this.speed, 0);
    }
    public void moveRight() {
        direction = Directions.RIGHT;
//        if (getGridPositionX() < Constants.DEFAULT_GRID_WIDTH-1)
        if (Utils.canvasToGridPosition(getCanvasPositionX()) < Constants.DEFAULT_GRID_WIDTH-1)
            setVelocity(this.speed, 0);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        if (characterState == CharacterState.WALK)
            stopOnTileIfNeeded();
    }

    private void stopOnTileIfNeeded() {
//        int closestTilePositionX = (int)Math.round(getCanvasPositionX() / Constants.DEFAULT_TILE_WIDTH);
//        int closestTilePositionY = (int)Math.round(getCanvasPositionY() / Constants.DEFAULT_TILE_HEIGHT);

        int closestTilePositionX = Utils.canvasToGridPosition(getCanvasPositionX());
        int closestTilePositionY = Utils.canvasToGridPosition(getCanvasPositionY());

        boolean closeToPosition =
                (Math.abs(Utils.gridToCanvasPosition(closestTilePositionX) - getCanvasPositionX()) <= this.speed)
                && (Math.abs(Utils.gridToCanvasPosition(closestTilePositionY) - getCanvasPositionY()) <= this.speed)
//                && (Utils.gridToCanvasPosition(closestTilePositionX) != getCanvasPositionX()
//                || Utils.gridToCanvasPosition(closestTilePositionY) != getCanvasPositionY());
                && (closestTilePositionX != previousGridPositionX || closestTilePositionY != previousGridPositionY);

        if(closeToPosition) {
            stop(closestTilePositionX, closestTilePositionY);
        }
    }

    private void stop(int newGridPositionX, int newGridPositionY) {

        characterState = CharacterState.IDLE;
        setVelocity(0, 0);
//        setGeneralPosition(newGridPositionX, newGridPositionY);
        setCanvasPosition(Utils.gridToCanvasPosition(newGridPositionX), Utils.gridToCanvasPosition(newGridPositionY));
        previousGridPositionX = newGridPositionX;
        previousGridPositionY = newGridPositionY;

        List<String> input = ListenerController.getInstance().getInput();
        if (input.size() > 0 && !input.contains("UP") && !input.contains("DOWN") && !input.contains("LEFT") && !input.contains("RIGHT")) {
            System.out.println("continous move!");
            move(input.get(input.size()-1));
        }
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

    // GETS AND SETS:

    public CharacterState getState() {
        return this.characterState;
    }

    public String getName() {
        return this.name;
    }

    public Directions getDirection() {
        return direction;
    }
}
