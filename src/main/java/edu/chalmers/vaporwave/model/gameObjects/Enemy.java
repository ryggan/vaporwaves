package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by bob on 2016-04-15.
 */
public class Enemy extends Movable {

    private Point currentGridPosition;
    private Point previousGridPosition;

    public void moveUp() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() + 1);
    }
    public void moveDown() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() - 1);
    }
    public void moveLeft() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX() - 1, previousGridPosition.getY());
    }
    public void moveRight() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX() + 1, previousGridPosition.getY());
    }

    public Point getPosition() {
        return currentGridPosition;
    }
    public double getXPosition() {
        return currentGridPosition.getX();
    }
    public double getYPosition() {
        return currentGridPosition.getY();
    }

}
