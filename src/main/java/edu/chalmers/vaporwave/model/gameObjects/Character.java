package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import javafx.scene.input.KeyEvent;

import java.awt.*;


/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Character extends DynamicTile {
    private Point currentPosition;
    private Point previousPosition;
    private Player player;
    private int playerId;


    public Character(Point position) {
        this.currentPosition = position;
    }

    public Character(Point position, Player player) {
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

    public void setPosition(Point p) {
        this.currentPosition = p;
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
