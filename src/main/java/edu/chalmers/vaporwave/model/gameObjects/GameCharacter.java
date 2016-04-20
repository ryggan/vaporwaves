package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.CharacterSpriteProperties;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.Image;
import org.w3c.dom.NodeList;

import java.awt.*;

public class GameCharacter extends DynamicTile {

    // needed or not?
    private Point currentGridPosition;
    private Point previousGridPosition;
    private Player player;
    private int playerId;

    private Directions direction;
    private String characterState;

    private double maxHealth;
    private double health;
    private double speed;
    private int bombRange;
    private int bombCount;

    private Sprite[] spawnSprite = new Sprite[1];
    private Sprite[] idleSprite = new Sprite[4];
    private Sprite[] walkSprite = new Sprite[4];
    private Sprite[] flinchSprite = new Sprite[4];
    private Sprite[] deathSprite = new Sprite[1];

    public GameCharacter(String name) {

        // Trying out the XML loader
        XMLReader reader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        NodeList nl = reader.read();
        System.out.println(nl);
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), name);

        Sprite currentSpriteCreation;
        System.out.println(characterProperties.getSpriteProperties());

        for (CharacterState characterState : Constants.CHARACTER_CHARACTER_STATE) {

            CharacterSpriteProperties characterSpriteProperties = characterProperties.getSpriteProperties(characterState);

            currentSpriteCreation = new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                    new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                    characterSpriteProperties.getFrames(),
                    characterSpriteProperties.getDuration(),
                    characterSpriteProperties.getFirstFrame(),
                    characterSpriteProperties.getOffset());

            switch(characterState) {
                case SPAWN:
                    spawnSprite[0] = currentSpriteCreation;
                    break;
                case DEATH:
                    deathSprite[0] = currentSpriteCreation;
                    break;
            }
        }




        Image spriteSheet1 = new Image("images/spritesheet-alyssa-walkidleflinch-48x48.png");

        for (int i = 0; i < 4; i++) {
            idleSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 1, 0.1, new int[] {i, 4}, new double[] {16, 27});
            walkSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 8, 0.1, new int[] {0, i}, new double[] {16, 27});
            flinchSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 1, 0.1, new int[] {4+i, 4}, new double[] {16, 27});
        }

        // Test settings setup:

        setGeneralPosition(5, 5);
        characterState = "IDLE";
        direction = Directions.DOWN;
        speed = 0.8;
        updateSprite();
    }


    /**
     * Helper method for creating an AnimatedSprite object from a CharacterSpriteProperties object.
     *
     * @param characterSpriteProperties
     * @return An AnimatedSprite object
     */
    private AnimatedSprite createSpriteFromProperties(CharacterSpriteProperties characterSpriteProperties) {
        return new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                characterSpriteProperties.getFrames(),
                characterSpriteProperties.getDuration(),
                characterSpriteProperties.getFirstFrame(),
                characterSpriteProperties.getOffset());
    }

    public void move(String key) {
//        if (!characterState.equals("WALK") || oppositeDirection(key)) {
            characterState = "WALK";
            if (key.equals("UP")) {
                moveUp();
            } else if (key.equals("LEFT")) {
                moveLeft();
            } else if (key.equals("DOWN")) {
                moveDown();
            } else if (key.equals("RIGHT")) {
                moveRight();
            }
            updateSprite();
//        }
    }

    private boolean oppositeDirection(String key) {
        return (key.equals("UP") && direction == Directions.DOWN)
                || (key.equals("DOWN") && direction == Directions.UP)
                || (key.equals("RIGHT") && direction == Directions.LEFT)
                || (key.equals("LEFT") && direction == Directions.RIGHT);
    }

    private void updateSprite() {
        Sprite[] currentSprite = idleSprite; // Always idle if no other state is active
        if (characterState.equals("WALK")) {
            currentSprite = walkSprite;
        } else if (characterState.equals("FLINCH")) {
            currentSprite = flinchSprite;
        } else if (characterState.equals("SPAWN")) {
            currentSprite = spawnSprite;
        } else if (characterState.equals("DEATH")) {
            currentSprite = deathSprite;
        }

        int spriteIndex;
        if (direction == Directions.LEFT) {
            spriteIndex = 1;
        } else if (direction == Directions.RIGHT) {
            spriteIndex = 2;
        } else if (direction == Directions.UP) {
            spriteIndex = 3;
        } else {
            spriteIndex = 0; // Happens when direction = DOWN, or when characterState = SPAWN/DEATH
        }
        setSprite(currentSprite[spriteIndex]);
    }

    public void moveUp() {
        direction = Directions.UP;
        setVelocity(0, -this.speed);
//        previousGridPosition = currentGridPosition;
//        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() + 1);
//        setSprite(walkSprite[3]);
    }
    public void moveDown() {
        direction = Directions.DOWN;
        setVelocity(0, this.speed);
//        previousGridPosition = currentGridPosition;
//        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() - 1);
//        setSprite(walkSprite[0]);
    }
    public void moveLeft() {
        direction = Directions.LEFT;
        setVelocity(-this.speed, 0);
//        previousGridPosition = currentGridPosition;
//        currentGridPosition.setLocation(previousGridPosition.getX() - 1, previousGridPosition.getY());
//        setSprite(walkSprite[1]);
    }
    public void moveRight() {
        direction = Directions.RIGHT;
        setVelocity(this.speed, 0);
//        previousGridPosition = currentGridPosition;
//        currentGridPosition.setLocation(previousGridPosition.getX() + 1, previousGridPosition.getY());
//        setSprite(walkSprite[2]);
    }

//    public void setGridPosition(Point gridPosition) {
//        this.currentGridPosition = gridPosition;
//        this.previousGridPosition = gridPosition;
//    }
//
//    public Point getGridPosition() {
//        return currentGridPosition;
//    }
//    public double getXPosition() {
//        return currentGridPosition.getX();
//    }
//    public double getYPosition() {
//        return currentGridPosition.getY();
//    }
}
