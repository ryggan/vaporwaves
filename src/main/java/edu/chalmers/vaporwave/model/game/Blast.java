package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;

import java.awt.*;

public class Blast extends StaticTile {

    private int range;
    private Point position;
    private double damage;
    private double time;

    public Blast(Explosive explosive) {

        this.range = explosive.getRange();
        this.position = explosive.getPosition();
        this.damage = explosive.getDamage();
        this.time = 0;

        GameEventBus.getInstance().post(new BlastEvent(this));
    }

    public Point getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public double getDamage() {
        return this.damage;
    }
}
