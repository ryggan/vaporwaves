package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;

/**
 * This event is posted to the eventbus by ArenaView when a Movables spawn animation is finished.
 */
public class SpawnEvent {

    private Movable movable;

    public SpawnEvent(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof SpawnEvent){
            SpawnEvent other = (SpawnEvent) o;
            return this.movable.equals(other.movable);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.movable.hashCode() * 7;
    }
}
