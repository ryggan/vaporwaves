package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class Scoreboard {
    private Player localPlayer;
    private Sprite scoreboardBackground;
    private Canvas scoreboard;
    private GraphicsContext scoreboardGC;
    private ArrayList<Label> labels;
    private Group root;
    private AnchorPane scoreboardPane;
    private TilePane tilePane;

    private static final double ELEMENT_SIZE = 100;
    private static final double GAP = ELEMENT_SIZE / 10;

    private static double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE)  + 2*Constants.DEFAULT_TILE_WIDTH;

            //Math.floor((Constants.WINDOW_WIDTH - (Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE) / 2));
    private static double yoffset = 8*Constants.DEFAULT_TILE_HEIGHT;

            //Math.floor((Constants.WINDOW_HEIGHT - (Constants.DEFAULT_TILE_HEIGHT * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE) / 2));

    //For testing purposes
    public Scoreboard(Group root) {
        this.root = root;
        //fix filepath after creating image
        scoreboardBackground = new Sprite(ImageContainer.getInstance().getImage(ImageID.SCOREBOARD_BACK));
        scoreboard = new Canvas(Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE,
                Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE);
        scoreboardGC = scoreboard.getGraphicsContext2D();
        scoreboard.setLayoutX(xoffset);
        scoreboard.setLayoutY(yoffset);

        scoreboardBackground.setPosition(0, 0);
        scoreboardBackground.setScale(Constants.GAME_SCALE);
        scoreboardBackground.render(scoreboardGC, -1);

        scoreboardPane = new AnchorPane();
        scoreboardPane.setPrefSize(512, 448);
        //Get proper values for middle of screen
        scoreboardPane.setLayoutX(0);
        scoreboardPane.setLayoutY(0);
        root.getChildren().add(scoreboardPane);
        scoreboardPane.setVisible(false);



        root.getChildren().add(scoreboard);
        scoreboard.setVisible(false);
        addPlayersToScoreboard();

    }

    public Scoreboard(Group root, Player localPlayer) {
        this.localPlayer = localPlayer;

    }

    public void showScoreboard() {
        scoreboard.setVisible(true);
        scoreboardPane.setVisible(true);
        scoreboardPane.toFront();
    }

    public void hideScoreboard() {
        scoreboard.setVisible(false);
        scoreboardPane.setVisible(false);
    }

    //should have "ArrayList<Player> players" as argument, made dummy list in method for testing purposes
    public void addPlayersToScoreboard(/*ArrayList<Player> players*/) {
        labels = new ArrayList<Label>();
        tilePane = new TilePane();
        tilePane.setPrefSize(512,448);
        tilePane.setPrefRows(/*players.size()*/ 2);
        tilePane.setPrefColumns(5);
        tilePane.setHgap(GAP);
        tilePane.setVgap(GAP + 30);
        tilePane.setLayoutX(xoffset);
        tilePane.setLayoutY(yoffset);
        tilePane.setPadding(new Insets(20, 20, 20, 80));
        //tilePane.setAlignment();

        String[] str = {"Player1", "3", "5", "10", "1000"};
        scoreboardPane.getChildren().add(tilePane);
        createElements(str);
       /* for(int i = 0; i < players.size(); i++) {
            labels.add(i, new Label("Player"));
            //Some logic for setting it dynamicly, got a good idea
            scoreboardPane.getChildren().add(labels.get(i));
            labels.get(i).setLayoutX(0);
            labels.get(i).setLayoutY(0);
            labels.get(i).setVisible(true);
        }*/
    }

    private void createElements(String[] str) {
        tilePane.getChildren().clear();
        Group labels = new Group();
        Label name = new Label("Player:", labels);
        name.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        Label kills = new Label("Kills:", labels);
        kills.setStyle("-fx-text-fill: white;  -fx-font-size: 16;");
        Label deaths = new Label("Deaths:", labels);
        deaths.setStyle("-fx-text-fill: white;  -fx-font-size: 16;");
        Label creeps = new Label("Creeps:", labels);
        creeps.setStyle("-fx-text-fill: white;  -fx-font-size: 16;");
        Label score = new Label("Score:", labels);
        score.setStyle("-fx-text-fill: white;  -fx-font-size: 16;");
        tilePane.getChildren().add(name);
        tilePane.getChildren().add(kills);
        tilePane.getChildren().add(deaths);
        tilePane.getChildren().add(creeps);
        tilePane.getChildren().add(score);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 1; j++) {
                Label label = new Label(str[i]);
                label.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
                tilePane.getChildren().add(label);
            }
        }
    }

    //private void createElement(String str) {
        //Rectangle rectangle = new Rectangle(ELEMENT_SIZE-20, ELEMENT_SIZE/3);
        //rectangle.setStroke(Color.ORANGE);
       // rectangle.setFill(Color.STEELBLUE);
          //  tilePane.getChildren().add(new Label(str));
       // return rectangle;
    //}
}
