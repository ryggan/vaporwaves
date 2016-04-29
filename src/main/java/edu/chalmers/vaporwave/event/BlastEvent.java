package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Blast;
import edu.chalmers.vaporwave.model.game.Explosive;

public class BlastEvent {

    private Explosive explosive;

    public BlastEvent(Explosive explosive) {
        this.explosive = explosive;
    }

    public Explosive getExplosive() {
        return this.explosive;
    }

    public boolean equals(Object o){
        if(o instanceof BlastEvent){
            BlastEvent other = (BlastEvent) o;
            if(this.explosive==other.explosive){
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        int hash=2563;
        hash=hash + explosive.hashCode()*647;
        return hash;
    }

}
