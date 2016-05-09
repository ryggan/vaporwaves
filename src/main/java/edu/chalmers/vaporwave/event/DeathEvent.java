package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;

public class DeathEvent {

    private Movable movable;

    public DeathEvent(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof DeathEvent){
            DeathEvent other = (DeathEvent) o;
            return this.movable.equals(other.movable);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return movable.hashCode();
    }

}
