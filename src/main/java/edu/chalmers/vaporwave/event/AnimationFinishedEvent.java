package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Movable;
import edu.chalmers.vaporwave.model.game.StaticTile;
import edu.chalmers.vaporwave.view.AnimatedSprite;

public class AnimationFinishedEvent {

    private Movable movable;
    private StaticTile tile;

    private AnimatedSprite activeSprite;

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

    public void setSprite(AnimatedSprite activeSprite) {
        this.activeSprite = activeSprite;
    }

    public AnimatedSprite getSprite() {
        return this.activeSprite;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof AnimationFinishedEvent){
            AnimationFinishedEvent other = (AnimationFinishedEvent) o;
            if(this.movable==other.movable&&this.tile==other.tile){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 23;
        hash = movable.hashCode()*123;
        hash = tile.hashCode()*52;
        return hash;

    }

}
