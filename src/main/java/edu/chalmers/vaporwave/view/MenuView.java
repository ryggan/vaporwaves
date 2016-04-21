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


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView startSelected;

    private List<ImageView[]> items;

    private MenuState menuState;


    public MenuView(Group root) {

        // Setting up area to draw graphics
        items=new ArrayList<ImageView[]>();

        start = new ImageView("images/startgame.png");
        startSelected = new ImageView("images/startgame.png");

        items.add(new ImageView[]{start, startSelected});


        root.getChildren().add(start);
        root.setOnKeyPressed(event -> System.out.print("sup"));
//

    }

    private void createBackground(GraphicsContext backgroundGC) {

        Sprite arenaBackgroundSprite = new Sprite("images/sprite-arenabackground-01.png");
        arenaBackgroundSprite.setPosition(0, 0);
        arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        arenaBackgroundSprite.render(backgroundGC, -1);

    }

    public void changeSelected(String key){
        if (key.equals("UP")){

        }

    }

    private void createStartGameButton() {

        start = new ImageView("images/startgame.png");
        start.setLayoutX(50);

        //arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        //arenaBackgroundSprite.render(backgroundGC, -1);

    }
}
