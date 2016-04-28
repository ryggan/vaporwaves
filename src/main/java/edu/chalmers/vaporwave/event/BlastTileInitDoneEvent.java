package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Blast;

import java.awt.*;

public class BlastTileInitDoneEvent {
    private Point position;
    private int range;
    private double damage;

    public BlastTileInitDoneEvent(Point position, int range, double damage) {
        this.position = position;
        this.range = range;
        this.damage = damage;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }
}


