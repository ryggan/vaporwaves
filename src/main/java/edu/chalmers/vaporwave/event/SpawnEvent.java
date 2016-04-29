package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;

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
            if(this.movable==other.movable);
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 1246;
        hash=hash+movable.hashCode()*75;
        return hash;
    }

}
