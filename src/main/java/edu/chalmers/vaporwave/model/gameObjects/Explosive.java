package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

public abstract class Explosive extends StaticTile {

    private GameCharacter owner;
    private int range;

    public Explosive(GameCharacter owner, int range) {
        this.owner = owner;
        this.range = range;
    }

    public GameCharacter getOwner() {
        return this.owner;
    }

    public int getRange() {
        return this.range;
    }

    public void explode() {
        new Blast(this);
        GameEventBus.getInstance().post(new BlastEvent());
        System.out.println("Pang");
    }
}
