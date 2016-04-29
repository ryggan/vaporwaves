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

    public Explosive(GameCharacter owner, int range, double damage) {
        this.owner = owner;
        this.range = range;
        this.position = owner.getGridPosition();
        this.damage = damage;
        this.explosionSound=new SoundPlayer("explosion.wav");
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

    public void explode() {
        explosionSound.stopSound();
        explosionSound.playSound();

        GameEventBus.getInstance().post(new BlastEvent(this));

    }

    public Point getPosition() {
        return this.position;
    }
}
