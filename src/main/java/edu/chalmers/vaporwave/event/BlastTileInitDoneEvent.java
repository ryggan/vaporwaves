package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Blast;

import java.awt.*;

public class BlastTileInitDoneEvent {
    private Point position;
    private int range;

    public BlastTileInitDoneEvent(Point position, int range) {
        this.position = position;
        this.range = range;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }
}


