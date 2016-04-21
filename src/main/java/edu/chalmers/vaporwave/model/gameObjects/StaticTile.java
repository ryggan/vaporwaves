package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class StaticTile {

    private Sprite sprite;

    public StaticTile(){
    }

    public StaticTile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(GraphicsContext gc, double time) {
        getSprite().render(gc, time);
    }

    // GET N SETS

    public void setSprite(Sprite s) {
        this.sprite = s;
    }
    public Sprite getSprite() {
        return this.sprite;
    }
}
