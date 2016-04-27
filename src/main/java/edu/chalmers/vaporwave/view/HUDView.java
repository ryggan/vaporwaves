package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.model.PowerUpProperties;
import edu.chalmers.vaporwave.model.PowerUpSpriteProperties;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.PowerUpLoader;
import edu.chalmers.vaporwave.util.PowerUpState;
import edu.chalmers.vaporwave.util.XMLReader;
import javafx.scene.Group;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;


import java.awt.*;

import java.util.ArrayList;

public class HUDView {


    private int width;
    private int height;
    private int firstFrameX;
    private Point position;
    Label stats = new Label();
    private MenuButtonView healthbarSprite;

    private ImageView healthBarEmpty;
    private Group root;
    private Canvas hudCanvas;
    private GraphicsContext hudGC;




    public HUDView(Group root){


        this.root=root;
        healthBarEmpty = new ImageView(new Image("images/healthbarempty.png"));
        healthBarEmpty.setLayoutX(140);
        healthBarEmpty.setLayoutY(-10);

        hudCanvas= new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE), ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
        root.getChildren().add(hudCanvas);
        root.getChildren().add(healthBarEmpty);

    }

    public void updateStats(double health, double speed, int range, int bombCount) {
        int printHealth = (int)health;
        int printSpeed = (int)(speed * 100);

        stats.setText("Health: " + printHealth + "\nBombs: " + bombCount + "\nSpeed: " + printSpeed + "\nRange: " + range);
        stats.setLayoutX(920);
        stats.setLayoutY(152);
        this.root.getChildren().remove(stats);
        this.root.getChildren().add(stats);
    }


    public void updateView(ArrayList<Movable> arenaMovables, StaticTile[][] arenaTiles, double timeSinceStart, double timeSinceLastCall) {
    }



}
