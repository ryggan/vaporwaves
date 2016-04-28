package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Movable;

public class SpawnEvent {

    private Movable movable;

    public SpawnEvent(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

}
