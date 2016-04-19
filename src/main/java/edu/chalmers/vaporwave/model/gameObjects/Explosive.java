package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class Explosive extends StaticTile {

    private double range;

    protected Explosive() {

    }

    public Explosive(Sprite s, Point cPos, Point gPos, double range) {
        super.setCanvasPosition(cPos);
        super.setGridPosition(gPos);
        this.range = range;
        super.setSprite(s);
    }

    public double getRange() {
        return this.range;
    }

    public void explode() {
        Blast b = new Blast(this);
    }
}
