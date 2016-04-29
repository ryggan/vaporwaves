package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.SoundPlayer;

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
        new Blast(this);
        explosionSound.stopSound();
        explosionSound.playSound();
    }

    public Point getPosition() {
        return this.position;
    }
}
