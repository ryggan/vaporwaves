package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.event.GameEventBus;

import java.awt.*;

/**
 * An explosive item is put in the arena by its owner and when it explodes, it transfers
 * its damage to a Blast, which in turn deals damage to Movables.
 *
 * In the beginning, not only Bombs but also Mines where thought of, which is why the Bomb
 * class inherits this, and is not the top class itself. We leave it like this, to be able
 * to extend it later, after the assignment is finished.
 */
public abstract class Explosive extends StaticTile {

    private GameCharacter owner;
    private int range;
    private Point position;
    private double damage;

    private double delay;
    private double timeStamp;

    public Explosive(GameCharacter owner, int range, double delay, double timeStamp, double damage) {
        this.owner = owner;
        this.range = range;
        this.position = owner.getGridPosition();
        this.damage = damage;

        this.delay = delay;
        this.timeStamp = timeStamp;
    }

    public GameCharacter getOwner() {
        return this.owner;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }

    public void updateTimer(double timeSinceStart) {
        if (timeSinceStart - this.timeStamp > this.delay) {
            explode();
        }
    }

    public void explode() {
        Container.playSound(SoundID.EXPLOSION);

        GameEventBus.getInstance().post(new BlastEvent(this));
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setDelay(double delay, double timeStamp) {
        this.delay = delay;
        setTimeStamp(timeStamp);
    }

    public Point getPosition() {
        return this.position;
    }

    public String toString() {
        return super.toString() + ": Explosive [ damage:"+this.damage+", delay:"+this.delay+"]";
    }
}
