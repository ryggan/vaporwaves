package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.util.BlastState;

import java.awt.*;

public class Blast extends StaticTile {

    private double damage;
    private Direction direction;
    private BlastState state;
    private double timeStamp;

    public Blast(Explosive explosive, BlastState state, Direction direction, double timeStamp) {
        this.damage = explosive.getDamage();
        this.direction = direction;
        this.state = state;
        this.timeStamp = timeStamp;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public BlastState getState() {
        return this.state;
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }

    public double getDamage() {
        return this.damage;
    }

    public String toString() {
        return "Blast[ Damage:"+damage+", Direction:"+direction+", State:"+state+" ]";
    }
}
