package edu.chalmers.vaporwave.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaView {

//    private Canvas canvas;
    private GraphicsContext gc;

    private AnimatedSprite testSprite;

    public ArenaView(Group root) {

        // Setting up area to draw graphics

        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();


        // TEST DRAWING

//        gc.clearRect(0, 0, 500, 500);
//        Sprite s = new Sprite("Images/Alyssa-fwd_walk-01.png");
//        s.render(gc);
//        gc.drawImage(new Image("Images/Alyssa-fwd_walk-01.png"), 0, 0);

        Image img = new Image("Images/spritesheet-alyssa-walkidleflinch-48x32.png");
        testSprite = new AnimatedSprite(img, new Dimension(48, 32), 8, 0.1, new int[] {0, 0});
        testSprite.setVelocity(4.5, 10.0);
    }

    public void updateView(double timeSinceStart, double timeSinceLastCall) {
        gc.clearRect(0, 0, 500, 500);
        testSprite.update(timeSinceLastCall);
        testSprite.render(gc, timeSinceStart);
    }
}
