package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.gameObjects.Tile;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaView {

    private Canvas backgroundCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext tileGC;

//    private Sprite testSprite;
//    private Sprite testSprite2;

    public ArenaView(Group root) {

        // Setting up area to draw graphics

        backgroundCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        root.getChildren().add(backgroundCanvas);
        tileCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        root.getChildren().add(tileCanvas);

        double xoffset = Math.floor((Constants.WINDOW_WIDTH - Constants.GAME_WIDTH) / 2);
        double yoffset = Math.floor((Constants.WINDOW_HEIGHT - Constants.GAME_HEIGHT) / 2);
        tileCanvas.setLayoutX(xoffset);
        tileCanvas.setLayoutY(yoffset);
        backgroundCanvas.setLayoutX(xoffset);
        backgroundCanvas.setLayoutY(yoffset);

        tileGC = tileCanvas.getGraphicsContext2D();
        backgroundGC = backgroundCanvas.getGraphicsContext2D();

        // TEST DRAWING

//        Image img = new Image("imgs/spritesheet-alyssa-walkidleflinch-48x32.png");
//        testSprite = new AnimatedSprite(img, new Dimension(48, 32), 8, 0.1, new int[] {0, 0});
//        testSprite.setVelocity(0, 5);
//        testSprite.setScale(2);
//        Image img2 = new Image("imgs/spritesheet-alyssa-death-56x56.png");
//        testSprite2 = new AnimatedSprite(img2, new Dimension(56, 56), 28, 0.1);
//        testSprite2.setPosition(50, 50);
//        testSprite2.setScale(2);
//
//        Image img3 = new Image("imgs/sprite-arenabackground-01.png");
//        Sprite testSprite3 = new Sprite(img3);
//        testSprite3.setPosition(0, 0);
//        testSprite3.setScale(2);
//
//        testSprite3.render(backgroundGC, -1);
    }

    public void updateView(ArrayList<Tile>[][] arena, double timeSinceStart, double timeSinceLastCall) {

        // TESTING
//
//        testSprite.update(timeSinceLastCall);
//        testSprite.render(tileGC, timeSinceStart);
////
//        testSprite2.render(tileGC, timeSinceStart);

//        System.out.println(arena[5][5]);

        // Actual rendering:

        tileGC.clearRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        for (int i = 0; i < arena.length; i++) {
            for (int j = 0; j < arena[0].length; j++) {
                for (Tile t : arena[i][j]) {
                    t.getSprite().render(tileGC, timeSinceStart);
                }
            }
        }
    }
}
