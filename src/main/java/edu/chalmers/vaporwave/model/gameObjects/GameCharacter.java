package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.util.Utils;

public class GameCharacter extends Movable {

    private double maxHealth;
    private double health;
    private int bombRange;
    private int maxBombCount;
    private int currentBombCount;

    public GameCharacter(String name) {
        super(name, Utils.gridToCanvasPosition(6), Utils.gridToCanvasPosition(5), 1);

        this.bombRange = 1;
        this.maxBombCount = 1;
        this.currentBombCount = 1;
        this.maxHealth = 100.0;
        this.health = 100.0;
    }

    public void placeBomb() {
        if(this.currentBombCount > 0) {
            GameEventBus.getInstance().post(new PlaceBombEvent(
                            Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()
                            ), bombRange)
                    );
        }
    }


    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return this.health;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombRange() {
        return this.bombRange;
    }

    public void setCurrentBombCount(int bombCount) {
        this.currentBombCount = bombCount;
    }

    public int getCurrentBombCount() {
        return this.currentBombCount;
    }

    public void setMaxBombCount(int bombCount) {
        this.maxBombCount = bombCount;
    }

    public int getMaxBombCount() {
        return this.maxBombCount;
    }
}
