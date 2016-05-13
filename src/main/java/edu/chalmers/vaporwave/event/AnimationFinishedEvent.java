package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;
import edu.chalmers.vaporwave.model.game.StaticTile;
import edu.chalmers.vaporwave.assetcontainer.AnimatedSprite;

public class AnimationFinishedEvent {

    private Movable movable;

    public AnimationFinishedEvent() {}

    public AnimationFinishedEvent(Movable movable) {
        this.movable = movable;
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable() {
        return this.movable;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof AnimationFinishedEvent){
            AnimationFinishedEvent other = (AnimationFinishedEvent) o;
            if (this.movable == other.movable){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 23;
        hash += movable.hashCode()*123;
        return hash;

    }

}
