package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;

/**
 * This event is posted to the eventbus by AnimatedSprite when it's animation has run it's course
 * (if the "do not loop" setting is on)
 */
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
        hash += this.movable.hashCode()*123;
        return hash;

    }

}
