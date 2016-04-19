package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.io.File;

public class GameCharacter extends DynamicTile {
    private Point currentPosition;
    private Point previousPosition;
    private Player player;
    private int playerId;

    private Sprite spawnSprite;
    private Sprite[] idleSprite = new Sprite[4];
    private Sprite[] walkSprite = new Sprite[4];
    private Sprite[] flinchSprite = new Sprite[4];
    private Sprite deathSprite;

    public GameCharacter(Point position) {
        this.currentPosition = position;

        Image spriteSheet0 = new Image("Images/spritesheet-alyssa-spawn-48x128.png");
        Image spriteSheet1 = new Image("Images/spritesheet-alyssa-walkidleflinch-48x32.png");
        Image spriteSheet2 = new Image("Images/spritesheet-alyssa-death-56x56.png");

        spawnSprite = new AnimatedSprite(spriteSheet0, new Dimension(48, 128), 27, 0.1, new int[] {0, 0});
        spawnSprite.setScale(Constants.GAME_SCALE);
        for (int i = 0; i < 4; i++) {
            idleSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 32), 1, 0.1, new int[] {i, 4});
            idleSprite[i].setScale(Constants.GAME_SCALE);

            walkSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 32), 8, 0.1, new int[] {0, i});
            walkSprite[i].setScale(Constants.GAME_SCALE);

            flinchSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 32), 1, 0.1, new int[] {4+i, 4});
            flinchSprite[i].setScale(Constants.GAME_SCALE);
        }
        deathSprite = new AnimatedSprite(spriteSheet2, new Dimension(56, 56), 28, 0.1, new int[] {0, 0});
        deathSprite.setScale(Constants.GAME_SCALE);

        setSprite(walkSprite[2]);
    }

    public GameCharacter(Point position, Player player) {
        this.currentPosition = position;
        this.player = player;
        this.playerId = player.getId();
    }

    public void move(KeyEvent e) {
        if(e.getCode().equals(65)) {
            this.moveLeft();
        }
        else if(e.getCode().equals(68)) {
            this.moveRight();
        }
        else if(e.getCode().equals(83)) {
            this.moveDown();
        }
        else if(e.getCode().equals(87)) {
            this.moveUp();
        }
    }

    public void moveUp() {
        previousPosition = currentPosition;
        currentPosition.setLocation(previousPosition.getX(), previousPosition.getY() + 1);
    }
    public void moveDown() {
        previousPosition = currentPosition;
        currentPosition.setLocation(previousPosition.getX(), previousPosition.getY() - 1);
    }
    public void moveLeft() {
        previousPosition = currentPosition;
        currentPosition.setLocation(previousPosition.getX() - 1, previousPosition.getY());
    }
    public void moveRight() {
        previousPosition = currentPosition;
        currentPosition.setLocation(previousPosition.getX() + 1, previousPosition.getY());
    }

    public Point getPosition() {
        return currentPosition;
    }
    public double getXPosition() {
        return currentPosition.getX();
    }
    public double getYPosition() {
        return currentPosition.getY();
    }
}
