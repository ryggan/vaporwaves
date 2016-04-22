package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

public abstract class Explosive extends StaticTile {

    private GameCharacter owner;
    private int range;
    private Point position;

    public Explosive(GameCharacter owner, int range) {
        this.owner = owner;
        this.range = range;
        this.position = owner.getGridPosition();
    }

    public GameCharacter getOwner() {
        return this.owner;
    }

    public int getRange() {
        return this.range;
    }

    public void explode() {
        new Blast(this);
        System.out.println("Pang");
    }

    public Point getPosition() {
        return this.position;
    }
}
