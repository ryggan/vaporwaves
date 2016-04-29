package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Blast;

import java.awt.*;
import java.util.Set;

public class BlastFinishedEvent {

    private Blast blast;
    private Point gridPosition;

    public BlastFinishedEvent(Blast blast, Point gridPosition) {
        this.blast = blast;
        this.gridPosition = gridPosition;
    }

    public Blast getBlast() {
        return this.blast;
    }

    public Point getGridPosition() {
        return gridPosition;
    }

}
