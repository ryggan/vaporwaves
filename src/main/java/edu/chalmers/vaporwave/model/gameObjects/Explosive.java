package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class Explosive extends StaticTile {

    private int range;

    //animation when exploding
    //might only need one sprite


    protected Explosive() {

    }

    public Explosive(Sprite sprite, int range) {
        this.range = range;
        super.setSprite(sprite);
    }

    public int getRange() {
        return this.range;
    }

    public void explode() {
        new Blast(this);
    }
}
