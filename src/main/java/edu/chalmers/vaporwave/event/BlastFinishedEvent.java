package edu.chalmers.vaporwave.event;

import java.awt.*;
import java.util.Set;

/**
 * Created by andreascarlsson on 2016-04-22.
 */
public class BlastFinishedEvent {
    private Set<Point> destroyedWalls;

    public BlastFinishedEvent(Set<Point> destroyedWalls) {
        this.destroyedWalls = destroyedWalls;
    }

    public Set<Point> getDestroyedWalls() {
        return this.destroyedWalls;
    }

}
