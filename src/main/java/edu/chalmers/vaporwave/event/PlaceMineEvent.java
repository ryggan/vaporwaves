package edu.chalmers.vaporwave.event;

import java.awt.*;

public class PlaceMineEvent {

    private Point position;
    private double damage;

    public PlaceMineEvent(Point position, double damage) {
        this.position = position;
        this.damage = damage;
    }

    public Point getGridPosition() {
        return this.position;
    }

    public double getDamage() {
        return this.damage;
    }
}
