package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Blast;

import java.awt.*;

public class BlastEvent {

    private Blast blast;

    public BlastEvent(Blast blast) {
        this.blast = blast;
    }

    public Blast getBlast() {
        return this.blast;
    }


}
