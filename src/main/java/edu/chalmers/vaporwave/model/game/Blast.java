package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.util.BlastState;

/**
 * The Blast is a simple object with timestamp and direction; almost the only thing needed
 * to render it on screen. Created by GameController when a BlastEvent occurs.
 */
public class Blast extends StaticTile implements AnimatedTile {

    private double damage;
    private Direction direction;
    private BlastState state;
    private double timeStamp;
    private double dangerousTime;
    private int playerId;

    public Blast(Explosive explosive, BlastState state, Direction direction, double timeStamp) {
        this.damage = explosive.getDamage();
        this.direction = direction;
        this.state = state;
        this.timeStamp = timeStamp;
        this.dangerousTime = 0.6;
        this.playerId = explosive.getOwner().getPlayerID();
    }

    public boolean isDangerous(double timeSinceStart) {
        double timeDifference = timeSinceStart - this.timeStamp;
        return (timeDifference < dangerousTime);
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

    public int getPlayerID() {
        return this.playerId;
    }

    public String toString() {
        return "Blast[ Damage:"+damage+", Direction:"+direction+", State:"+state+" ]";
    }
}
