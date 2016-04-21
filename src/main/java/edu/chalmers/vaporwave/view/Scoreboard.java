package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Created by bob on 2016-04-19.
 */
public class Scoreboard {
    private Player localPlayer;
    private Sprite scoreboardBackground;
    private Canvas scoreboard;
    private GraphicsContext scoreboardGC;
    private ArrayList<Label> labels;
    private Group root;
    private AnchorPane scoreboardPane;

    //For testing purposes
    public Scoreboard(Group root) {
        this.root = root;
        //fix filepath after creating image
        scoreboardBackground = new Sprite("images/scoreboardBackground.png");
        scoreboard = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        scoreboardGC = scoreboard.getGraphicsContext2D();

        double xoffset = Math.floor((Constants.WINDOW_WIDTH - Constants.GAME_WIDTH) / 2);
        double yoffset = Math.floor((Constants.WINDOW_HEIGHT - Constants.GAME_HEIGHT) / 2);
        scoreboard.setLayoutX(xoffset);
        scoreboard.setLayoutY(yoffset);

        scoreboardPane = new AnchorPane();
        scoreboardPane.setPrefSize(512, 448);
        //Get proper values for middle of screen
        scoreboardPane.setLayoutX(0);
        scoreboardPane.setLayoutY(0);
        root.getChildren().add(scoreboardPane);
        scoreboardPane.setVisible(false);

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
        scoreboardPane.setVisible(true);
    }

    public void hideScoreboard() {
        scoreboard.setVisible(false);
        scoreboardPane.setVisible(false);
    }

    //should have "ArrayList<Player> players" as argument, made dummy list in method for testing purposes
    public void addPlayersToScoreboard(ArrayList<Player> players) {
        labels = new ArrayList<Label>();
        for(int i = 0; i < players.size(); i++) {
            labels.add(i, new Label("Player"));
            //Some logic for setting it dynamicly, got a good idea
            scoreboardPane.getChildren().add(labels.get(i));
            labels.get(i).setLayoutX(0);
            labels.get(i).setLayoutY(0);
            labels.get(i).setVisible(true);
        }
    }
}
