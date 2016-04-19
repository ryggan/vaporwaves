package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class Explosive extends StaticTile {
    private double power;

    protected Explosive() {

    }

    public Explosive(Sprite s, Point cPos, Point gPos, double power) {
        super.setCanvasPosition(cPos);
        super.setGridPosition(gPos);
        this.power = power;
        super.setSprite(s);
    }

    public double getPower() {
        return this.power;
    }

    public void explode() {
        Blast b = new Blast(this);
    }
}
