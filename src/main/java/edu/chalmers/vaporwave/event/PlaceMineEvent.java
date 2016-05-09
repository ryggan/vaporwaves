package edu.chalmers.vaporwave.event;

import java.awt.*;

public class PlaceMineEvent {

    private Point position;
    private double damage;

    public PlaceMineEvent(Point position, double damage) {
        this.position = position;
        this.damage = damage;
    }

    public Point getGridPosition() {
        return this.position;
    }

    public double getDamage() {
        return this.damage;
    }

    public boolean equals(Object o){
        if(o instanceof PlaceMineEvent){
            PlaceMineEvent other = (PlaceMineEvent) o;
            return this.damage == other.damage &&
                    this.position.equals(other.position);
        }
        return false;
    }

    public int hashCode(){
        return position.hashCode() + ((int)damage);
    }
}
