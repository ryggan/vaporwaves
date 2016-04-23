package edu.chalmers.vaporwave.event;

import java.awt.*;

public class PlaceBombEvent {

    private Point position;
    private int range;

    public PlaceBombEvent(Point position, int range) {
        this.position = position;
        this.range = range;
    }

    public Point getGridPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }
}
