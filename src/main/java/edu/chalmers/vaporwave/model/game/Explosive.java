package edu.chalmers.vaporwave.model.game;

import java.awt.*;

public abstract class Explosive extends StaticTile {

    private GameCharacter owner;
    private int range;
    private Point position;
    private double damage;

    public Explosive(GameCharacter owner, int range, double damage) {
        this.owner = owner;
        this.range = range;
        this.position = owner.getGridPosition();
        this.damage = damage;
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
    }

    public Point getPosition() {
        return this.position;
    }
}
