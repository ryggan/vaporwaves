package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Movable;

public class DeathEvent {

    private Movable movable;

    public DeathEvent(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

}
