package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


import static java.awt.event.KeyEvent.*;

/**
 * Created by bob on 2016-04-15.
 */
public class MenuView {

    private Canvas backgroundCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext tileGC;
    private Label lol;
    private ImageView start;

    private MenuState menuState;


    public MenuView(Group root) {

        // Setting up area to draw graphics

        start = new ImageView("images/startgame.png");



        start.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                System.out.print("sup");

            }
        });

        root.getChildren().add(start);
        root.setOnKeyPressed(event -> System.out.print("sup"));


       // backgroundCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        //root.getChildren().add(backgroundCanvas);
        //tileCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        //root.getChildren().add(tileCanvas);
        ;

        //double xoffset = Math.floor((Constants.WINDOW_WIDTH - Constants.GAME_WIDTH) / 2);
       // double yoffset = Math.floor((Constants.WINDOW_HEIGHT - Constants.GAME_HEIGHT) / 2);
        //tileCanvas.setLayoutX(xoffset);
        //tileCanvas.setLayoutY(yoffset);
        //backgroundCanvas.setLayoutX(xoffset);
        //backgroundCanvas.setLayoutY(yoffset);

        //tileGC = tileCanvas.getGraphicsContext2D();
       // backgroundGC = backgroundCanvas.getGraphicsContext2D();
        //createBackground(backgroundGC);


       // menuState = new MenuState();

        // TEST DRAWING

//        Image img = new Image("images/spritesheet-alyssa-walkidleflinch-48x32.png");
//        testSprite = new AnimatedSprite(img, new Dimension(48, 32), 8, 0.1, new int[] {0, 0});
//        testSprite.setVelocity(0, 5);
//        testSprite.setScale(2);
//        Image img2 = new Image("images/spritesheet-alyssa-death-56x56.png");
//        testSprite2 = new AnimatedSprite(img2, new Dimension(56, 56), 28, 0.1);
//        testSprite2.setPosition(50, 50);
//        testSprite2.setScale(2);
//

    }

    private void createBackground(GraphicsContext backgroundGC) {

        Sprite arenaBackgroundSprite = new Sprite("images/sprite-arenabackground-01.png");
        arenaBackgroundSprite.setPosition(0, 0);
        arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        arenaBackgroundSprite.render(backgroundGC, -1);

    }

    private void createStartGameButton() {

        start = new ImageView("images/startgame.png");
        start.setLayoutX(50);

        //arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        //arenaBackgroundSprite.render(backgroundGC, -1);

    }
}
