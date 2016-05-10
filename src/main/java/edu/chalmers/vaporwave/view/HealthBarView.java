package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.text.*;

import java.awt.*;
import java.awt.Canvas;
import java.awt.Label;

/**
 * Created by Lina on 5/9/2016.
 */
public class HealthBarView {


    private int width;
    private int height;
    private int firstFrameX;
    private Point position;
    private javafx.scene.control.Label stats;
    private javafx.scene.control.Label healthPercentage;


    private ImageView healthBarEmpty;
    private ImageView healthBarFilled;;


    private ImageView emptyBar;

    private PixelReader reader;
    private WritableImage newImage;

    private double healthBarSpeed;
    private int lastHealth;

    private javafx.scene.text.Font bauhaus10;

    public HealthBarView(Group root) {


        this.healthBarEmpty = new ImageView(new Image("images/healthbarempty.png"));
        Image filled = new Image("images/healthbarfilled.png");
        reader = filled.getPixelReader();
        newImage = new WritableImage(reader, 0, 0, 300, 17);
        this.healthBarFilled = new ImageView(newImage);

        this.healthBarEmpty.setLayoutX(140);
        this.healthBarEmpty.setLayoutY(-10);
        this.healthBarFilled.setLayoutX(184);
        this.healthBarFilled.setLayoutY(38);

        root.getChildren().add(healthBarEmpty);
        root.getChildren().add(healthBarFilled);

    }

    public void updateStats(double health) {
        int newHealth = (int) health;
        updateHealthBar((int) ((health / 100) * 300));
        lastHealth = (int) health;

    }

    public void updateHealthBar(int health) {

            newImage = new WritableImage(reader, 0, 0, health, 17);
            ImageView healthBarFilledNew = new ImageView(newImage);
            healthBarFilledNew.setLayoutX(184);
            healthBarFilledNew.setLayoutY(38);

            this.healthBarFilled = healthBarFilledNew;
    }

}
