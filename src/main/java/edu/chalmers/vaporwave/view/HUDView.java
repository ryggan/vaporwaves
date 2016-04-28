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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.text.*;
import javafx.scene.text.Font;


import java.awt.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HUDView {

    private int width;
    private int height;
    private int firstFrameX;
    private Point position;
    private Label stats;
    private MenuButtonView healthbarSprite;

    private ImageView healthBarEmpty;
    private ImageView healthBarFilled;
    private ImageView scoreBarFilled;
    private ImageView scoreBarEmpty;

    private ImageView emptyBar;
    private Group root;
    private Canvas hudCanvas;
    private GraphicsContext hudGC;

    private PixelReader reader;
    private WritableImage newImage;

    private Font bauhaus10;

    public HUDView(Group root){


        this.root = root;
        this.healthBarEmpty = new ImageView(new Image("images/healthbarempty.png"));
        this.scoreBarFilled = new ImageView(new Image("images/scorebarfilled.png"));
        this.scoreBarEmpty = new ImageView(new Image("images/barempty.png"));


        Image filled=new Image("images/healthbarfilled.png");
        reader = filled.getPixelReader();
        newImage = new WritableImage(reader, 0, 0, 300,17 );
        this.healthBarFilled = new ImageView(newImage);

        this.healthBarEmpty.setLayoutX(140);
        this.healthBarEmpty.setLayoutY(-10);
        this.healthBarFilled.setLayoutX(184);
        this.healthBarFilled.setLayoutY(38);
        this.scoreBarEmpty.setLayoutX(590);
        this.scoreBarEmpty.setLayoutY(34);
        this.scoreBarFilled.setLayoutX(594);
        this.scoreBarFilled.setLayoutY(38);

        this.stats = new Label();

        hudCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE), ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
        this.root.getChildren().add(hudCanvas);
        this.root.getChildren().add(healthBarEmpty);
        this.root.getChildren().add(healthBarFilled);
        this.root.getChildren().add(scoreBarEmpty);
        this.root.getChildren().add(scoreBarFilled);
    }

    public void updateStats(double health, double speed, int range, int bombCount) {
        int printHealth = (int)health;
        int printSpeed = (int)(speed * 100);
        System.out.println((health/100) * 300);
        updateHealthBar((int) ((health/100) * 300));
        stats.setText("Health: " + printHealth + "\nBombs: " + bombCount + "\nSpeed: " + printSpeed + "\nRange: " + range);
        stats.setLayoutX(920);
        stats.setLayoutY(152);
        this.root.getChildren().remove(stats);
        this.root.getChildren().add(stats);
    }

    public void updateHealthBar(int i){
        newImage = new WritableImage(reader, 0, 0, i, 17);
        ImageView healthBarFilledNew = new ImageView(newImage);
        healthBarFilledNew.setLayoutX(184);
        healthBarFilledNew.setLayoutY(38);
        this.root.getChildren().remove(healthBarFilled);
        this.root.getChildren().add(healthBarFilledNew);
        this.healthBarFilled = healthBarFilledNew;
    }