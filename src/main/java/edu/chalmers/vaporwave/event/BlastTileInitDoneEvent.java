package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.gameObjects.Blast;

import java.awt.*;

public class BlastTileInitDoneEvent {
    private Point position;
    private int range;
    private double damage;

    public BlastTileInitDoneEvent(Point position, int range, double damage) {
        this.position = position;
        this.range = range;
        this.damage = damage;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  BlastTileInitDoneEvent){
            BlastTileInitDoneEvent other = (BlastTileInitDoneEvent) o;
            if(this.position==other.position&&this.range==other.range&&this.damage==other.damage){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash=2467;
        hash=hash+position.hashCode()*91;
        hash=hash+range*263;
        hash=hash+(int)damage*52;
        return hash;
    }
}


