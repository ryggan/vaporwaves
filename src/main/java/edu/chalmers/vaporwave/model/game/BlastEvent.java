package edu.chalmers.vaporwave.model.game;

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
        return explosive.hashCode();
    }

}
