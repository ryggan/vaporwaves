package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.game.GameCharacter;

import java.awt.*;

public class PlaceBombEvent {

    private GameCharacter character;
    private Point position;
    private int range;
    private double damage;
   // private int playerId;

    public PlaceBombEvent(GameCharacter character, Point position, int range, double damage) {
        this.character = character;
        this.position = position;
        this.range = range;
        this.damage = damage;
       // this.playerId = character.getPlayerId();
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
