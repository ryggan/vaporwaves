package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Blast;

import java.awt.*;

/**
 * Created by andreascarlsson on 2016-04-22.
 */
public class BlastEvent {

    private Blast blast;

    public BlastEvent(Blast blast) {
        this.blast = blast;
    }

    public Blast getBlast() {
        return this.blast;
    }

    public Point getPosition() {
        return this.blast.getPosition();
    }


}
