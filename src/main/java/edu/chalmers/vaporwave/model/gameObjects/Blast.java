package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Blast {

    int range;
    double canvasXPosition;
    double canvasYPosition;
    double time;

//    private Sprite[] explosionEndSprites = new Sprite[4];
//    private Sprite[] explosionBeamSprites = new Sprite[4];
    private Sprite explosionEndSprite;
    private Sprite explosionBeamSprite;
    private Sprite explosionCenterSprite;

    public Blast(Explosive b) {

        // Initiate sprites:
        Image spriteSheet = new Image("images/spritesheet-bombs_and_explosions-17x17.png");
        explosionEndSprite = new AnimatedSprite(spriteSheet, new Dimension(17, 17), 7, 0.1, new int[] {2, 0});
        explosionEndSprite.setScale(Constants.GAME_SCALE);
        explosionBeamSprite = new AnimatedSprite(spriteSheet, new Dimension(17, 17), 7, 0.1, new int[] {2, 1});
        explosionBeamSprite.setScale(Constants.GAME_SCALE);
        explosionCenterSprite = new AnimatedSprite(spriteSheet, new Dimension(17, 17), 7, 0.1, new int[] {2, 2});
        explosionCenterSprite.setScale(Constants.GAME_SCALE);

        this.range = b.getRange();
        this.canvasXPosition = b.getCanvasXPosition();
        this.canvasYPosition = b.getCanvasYPosition();
        this.time = 0;
    }

    public void render(GraphicsContext gc, double time) {

        // First one center explosion sprite
        explosionCenterSprite.setPosition(canvasXPosition, canvasYPosition);
        explosionCenterSprite.render(gc, time);

        // Then four explosion beam sprites, for every range
        int end = 0;
        for (int j = 0; j < this.range; j++) {
            explosionBeamSprite.setPosition(canvasXPosition - j * 16, canvasYPosition);
            explosionBeamSprite.render(gc, time);
            explosionBeamSprite.setPosition(canvasXPosition, canvasYPosition - j * 16);
            explosionBeamSprite.render(gc, time);
            explosionBeamSprite.setPosition(canvasXPosition + j * 16, canvasYPosition);
            explosionBeamSprite.render(gc, time);
            explosionBeamSprite.setPosition(canvasXPosition, canvasYPosition + j * 16);
            explosionBeamSprite.render(gc, time);
            end = j + 1;
        }

        // Lastly four end explosion sprites, at the tip of each explosion beam
        explosionEndSprite.setPosition(canvasXPosition - end * 16, canvasYPosition);
        explosionEndSprite.render(gc, time);
        explosionEndSprite.setPosition(canvasXPosition, canvasYPosition - end * 16);
        explosionEndSprite.render(gc, time);
        explosionEndSprite.setPosition(canvasXPosition + end * 16, canvasYPosition);
        explosionEndSprite.render(gc, time);
        explosionEndSprite.setPosition(canvasXPosition, canvasYPosition + end * 16);
        explosionEndSprite.render(gc, time);

        // TODO: 1. Fix rotation of explosion beam sprites and end sprites
        // TODO: 2. Make sure the explosion starts at frame = 0 and then destroys itself at frame = last
    }
}
