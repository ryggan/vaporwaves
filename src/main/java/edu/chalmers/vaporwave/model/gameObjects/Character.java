package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        if(e.getKeyCode() == 65) {
            this.moveLeft();
        }
        else if(e.getKeyCode() == 68) {
            this.moveRight();
        }
        else if(e.getKeyCode() == 83) {
            this.moveDown();
        }
        else if(e.getKeyCode() == 87) {
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
