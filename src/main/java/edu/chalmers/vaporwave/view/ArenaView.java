package edu.chalmers.vaporwave.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaView {

//    private Canvas canvas;
    private GraphicsContext gc;

    public ArenaView(Group root) {

        // Setting up area to draw graphics

        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();


        // TEST DRAWING

        gc.clearRect(0, 0, 500, 500);
        Sprite s = new Sprite("Images/Alyssa-fwd_walk-01.png");
        s.render(gc);
//        gc.drawImage(new Image("Images/Alyssa-fwd_walk-01.png"), 0, 0);
    }

    public void updateView() {

    }
}
