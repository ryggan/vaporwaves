package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Blast extends StaticTile {

    private int range;
    private Point position;
    private double time;

    public Blast(Explosive explosive) {

        this.range = explosive.getRange();
        this.position = explosive.getPosition();
        this.time = 0;

        GameEventBus.getInstance().post(new BlastEvent(this));
    }

    public Point getPosition() {
        return this.position;
    }

//    public void render(GraphicsContext gc, double time) {
//
//        // First one center explosion sprite
//        explosionCenterSprite.setPosition(canvasXPosition, canvasYPosition);
//        explosionCenterSprite.render(gc, time);
//
//        // Then four explosion beam sprites, for every range
//        int end = 0;
//        for (int j = 0; j < this.range; j++) {
//            explosionBeamSprite.setPosition(canvasXPosition - j * 16, canvasYPosition);
//            explosionBeamSprite.render(gc, time);
//            explosionBeamSprite.setPosition(canvasXPosition, canvasYPosition - j * 16);
//            explosionBeamSprite.render(gc, time);
//            explosionBeamSprite.setPosition(canvasXPosition + j * 16, canvasYPosition);
//            explosionBeamSprite.render(gc, time);
//            explosionBeamSprite.setPosition(canvasXPosition, canvasYPosition + j * 16);
//            explosionBeamSprite.render(gc, time);
//            end = j + 1;
//        }
//
//        // Lastly four end explosion sprites, at the tip of each explosion beam
//        explosionEndSprite.setPosition(canvasXPosition - end * 16, canvasYPosition);
//        explosionEndSprite.render(gc, time);
//        explosionEndSprite.setPosition(canvasXPosition, canvasYPosition - end * 16);
//        explosionEndSprite.render(gc, time);
//        explosionEndSprite.setPosition(canvasXPosition + end * 16, canvasYPosition);
//        explosionEndSprite.render(gc, time);
//        explosionEndSprite.setPosition(canvasXPosition, canvasYPosition + end * 16);
//        explosionEndSprite.render(gc, time);
//
//        // TODO: 1. Fix rotation of explosion beam sprites and end sprites OR solve it in spritesheet
//        //          tip: http://stackoverflow.com/questions/18260421/how-to-draw-image-rotated-on-javafx-canvas
//        // TODO: 2. Make sure the explosion starts at frame = 0 and then destroys itself at frame = last
//    }
}
