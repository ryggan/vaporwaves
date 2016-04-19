package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Blast {

    int range;
    private Sprite[] explosionSprites = new Sprite[4];

    public Blast(Explosive b) {
            //add sprites
        this.range = b.getRange();
    }
}
