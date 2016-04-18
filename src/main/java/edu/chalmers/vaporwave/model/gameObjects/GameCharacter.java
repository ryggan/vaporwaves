package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.Image;


/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class GameCharacter extends DynamicTile {
    private Point currentPosition;
    private Point previousPosition;
    private Player player;
    private int playerId;


    public GameCharacter(Point position) {
        this.currentPosition = position;


        javafx.scene.image.Image img = new javafx.scene.image.Image("Images/spritesheet-alyssa-walkidleflinch-48x32.png");
        Sprite testSprite = new AnimatedSprite(img, new Dimension(48, 32), 8, 0.1, new int[] {0, 0});
        testSprite.setVelocity(0, 5);
        testSprite.setScale(2);
        setSprite(testSprite);

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
