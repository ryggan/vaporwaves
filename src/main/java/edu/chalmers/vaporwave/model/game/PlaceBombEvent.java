package edu.chalmers.vaporwave.model.game;

import java.awt.*;

public class PlaceBombEvent {

    private GameCharacter character;
    private Point position;
    private int range;
    private double damage;

    public PlaceBombEvent(GameCharacter character, Point position, int range, double damage) {
        this.character = character;
        this.position = position;
        this.range = range;
        this.damage = damage;
    }

    public GameCharacter getCharacter() {
        return this.character;
    }

    public Point getGridPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }
}
