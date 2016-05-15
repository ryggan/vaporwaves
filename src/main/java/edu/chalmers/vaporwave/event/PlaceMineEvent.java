package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.GameCharacter;

import java.awt.*;

public class PlaceMineEvent extends PlaceBombEvent {

    public PlaceMineEvent(GameCharacter character, Point position, int range, double damage) {
        super(character, position, range, damage);
    }

//    public boolean equals(Object o){
//        if(o instanceof PlaceMineEvent){
//            PlaceMineEvent other = (PlaceMineEvent) o;
//            return this.damage == other.damage &&
//                    this.position.equals(other.position);
//        }
//        return false;
//    }
//
//    public int hashCode(){
//        return position.hashCode() + ((int)damage);
//    }
}
