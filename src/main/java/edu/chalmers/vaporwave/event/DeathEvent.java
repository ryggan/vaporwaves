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
            if(this.movable==other.movable){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 4825;
        hash = hash + movable.hashCode()*34;
        return hash;
    }

}
