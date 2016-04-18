package edu.chalmers.vaporwave.model.gameObjects;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class DynamicTile implements Tile {
    private double position;

    public void setPosition(double p) {
        this.position = p;
    }

    public double getPosition() {
        return this.position;
    }
}
