package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Movable;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;

public class AnimationFinishedEvent {

    private Movable movable;
    private StaticTile tile;

    public AnimationFinishedEvent() {}

    public AnimationFinishedEvent(Movable movable) {
        this.movable = movable;
    }

    public AnimationFinishedEvent(StaticTile tile) {
        this.tile = tile;
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

    public void setTile(StaticTile tile) {
        this.tile = tile;
    }

    public StaticTile getTile() {
        return this.tile;
    }

}
