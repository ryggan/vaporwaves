package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.model.PowerUpProperties;
import edu.chalmers.vaporwave.model.PowerUpSpriteProperties;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.PowerUpLoader;
import edu.chalmers.vaporwave.util.PowerUpState;
import edu.chalmers.vaporwave.util.XMLReader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


import java.awt.*;
import java.util.ArrayList;

public class HUDView {

    private Group root;

    private Image healthBarEmpty;
    private int width;
    private int height;
    private int firstFrameX;
    private Point position;
    Label stats = new Label();


    public HUDView(Group root){
        this.root=root;
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
