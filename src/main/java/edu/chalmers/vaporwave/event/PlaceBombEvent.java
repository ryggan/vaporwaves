package edu.chalmers.vaporwave.event;

import java.awt.*;

public class PlaceBombEvent {

    private Point position;
    private int range;
    private double damage;

    public PlaceBombEvent(Point position, int range, double damage) {
        this.position = position;
        this.range = range;
        this.damage = damage;
    }

    public Point getGridPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }
}
