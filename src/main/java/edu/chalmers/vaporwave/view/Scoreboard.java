package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by bob on 2016-04-19.
 */
public class Scoreboard {
    private Player localPlayer;
    private Sprite scoreboardBackground;
    private Canvas scoreboard;
    private GraphicsContext scoreboardGC;

    //For testing purposes
    public Scoreboard(Group root) {
        //fix filepath after creating image
        scoreboardBackground = new Sprite("images/scoreboardBackground.png");
        scoreboard = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        scoreboardGC = scoreboard.getGraphicsContext2D();

        double xoffset = Math.floor((Constants.WINDOW_WIDTH - Constants.GAME_WIDTH) / 2);
        double yoffset = Math.floor((Constants.WINDOW_HEIGHT - Constants.GAME_HEIGHT) / 2);
        scoreboard.setLayoutX(xoffset);
        scoreboard.setLayoutY(yoffset);

        scoreboardBackground.setPosition(0, 0);
        scoreboardBackground.setScale(Constants.GAME_SCALE);
        scoreboardBackground.render(scoreboardGC, -1);

        root.getChildren().add(scoreboard);
        scoreboard.setVisible(false);

    }

    public Scoreboard(Group root, Player localPlayer) {
        this.localPlayer = localPlayer;

    }

    public void showScoreboard() {
        scoreboard.setVisible(true);
    }

    public void hideScoreboard() {
        scoreboard.setVisible(false);
    }
}
