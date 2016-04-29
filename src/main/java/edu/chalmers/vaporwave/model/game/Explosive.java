package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.SoundPlayer;
import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.util.BlastState;

import java.awt.*;

public abstract class Explosive extends StaticTile {

    private GameCharacter owner;
    private int range;
    private Point position;
    private double damage;
    private SoundPlayer explosionSound;

    private double delay;
    private double timeStamp;

    public Explosive(GameCharacter owner, int range, double delay, double timeStamp, double damage) {
        this.owner = owner;
        this.range = range;
        this.position = owner.getGridPosition();
        this.damage = damage;
        
        this.explosionSound=new SoundPlayer("explosion.wav");

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
        if (timeSinceStart - this.timeStamp > delay) {
            explode();
        }
    }

    public void explode() {
        explosionSound.stopSound();
        explosionSound.playSound();

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
}
