package edu.chalmers.vaporwave.model.gameObjects;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.*;

import java.awt.*;
import java.util.List;

public class GameCharacter extends Movable {

    private double maxHealth;
    private double health;
    private int bombRange;
    private int bombCount;

    public GameCharacter(String name) {
        super(name, Utils.gridToCanvasPosition(6), Utils.gridToCanvasPosition(5), 1);

        this.bombRange = 2;
        this.bombCount = 5;
        this.maxHealth = 100.0;
        this.health = 100.0;
    }

    public void placeBomb() {
        if(this.bombCount > 0) {
            GameEventBus.getInstance().post(new PlaceBombEvent(
                            Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()
                            ), bombRange)
                    );
            this.bombCount -= 1;
        }
    }


    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getBombRange() {
        return this.bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombCount() {
        return this.bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }
}
