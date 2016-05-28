package edu.chalmers.vaporwave.model.game;

/**
 * This wall type is destructible. That means that a bomb can blow it up, which in turn
 * means that it has to be animated and have a timestamp.
 */
public class DestructibleWall extends Wall implements AnimatedTile {

    private boolean destroyed;
    private double timeStamp;

    public DestructibleWall() {
        this.destroyed = false;
    }

    public void destroy(double timeStamp) {
        this.destroyed = true;
        this.timeStamp = timeStamp;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }

    public String toString() {
        return super.toString() + ": DestructibleWall [ destroyed: "+this.destroyed+", time: "+this.timeStamp+" ]";
    }
}
