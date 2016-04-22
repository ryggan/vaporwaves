package edu.chalmers.vaporwave.event;

import java.awt.*;

public class PlaceBombEvent {

    private Point position;

    public PlaceBombEvent(Point position) {
        this.position = position;
    }

    public Point getGridPosition() {
        return this.position;
    }
}
