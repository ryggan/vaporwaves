package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.Blast;

public class BlastEvent {

    private Blast blast;

    public BlastEvent(Blast blast) {
        this.blast = blast;
    }

    public Blast getBlast() {
        return this.blast;
    }

    public boolean equals(Object o){
        if(o instanceof BlastEvent){
            BlastEvent other = (BlastEvent) o;
            if(this.blast==other.blast){
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        int hash=2563;
        hash=hash + blast.hashCode()*647;
        return hash;
    }


}
