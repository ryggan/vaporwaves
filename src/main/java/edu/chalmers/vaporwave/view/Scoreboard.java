package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Scoreboard {
    private Sprite scoreboardBackground;
    private Canvas scoreboard;
    private GraphicsContext scoreboardGC;
    private List<Player> playerList;
    private Group root;
    private AnchorPane scoreboardPane;
    private GridPane gridPane;
    private Label[][] playerLabels;

    private static final double ELEMENT_SIZE = 100;
    private static final double GAP = ELEMENT_SIZE / 10;

    private static double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE)  + 2*Constants.DEFAULT_TILE_WIDTH;

            //Math.floor((Constants.WINDOW_WIDTH - (Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE) / 2));
    private static double yoffset = 8*Constants.DEFAULT_TILE_HEIGHT;

            //Math.floor((Constants.WINDOW_HEIGHT - (Constants.DEFAULT_TILE_HEIGHT * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE) / 2));

    //For testing purposes
    public Scoreboard(Group root, List<Player> playerList) {
        this.root = root;
        this.playerList = playerList;
        playerLabels = new Label[playerList.size()][5];

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

    public void showScoreboard() {
        updateScoreboard();
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
        gridPane = new GridPane();
        gridPane.setPrefSize(512,448);
        gridPane.setHgap(GAP + 20);
        gridPane.setVgap(GAP + 30);
        gridPane.setLayoutX(xoffset);
        gridPane.setLayoutY(yoffset);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        gridPane.setAlignment(Pos.TOP_CENTER);
        //tilePane.setAlignment();
        scoreboardPane.getChildren().add(gridPane);
        createElements();
    }

    private void createElements() {
        gridPane.getChildren().clear();
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
        gridPane.add(name, 0, 0);
        gridPane.add(kills, 1, 0);
        gridPane.add(deaths, 2 , 0);
        gridPane.add(creeps, 3, 0);
        gridPane.add(score, 4, 0);


        for(int i = 0; i < playerList.size(); i++) {
            for(int j = 0; j < 5; j++) {
                playerLabels[i][j] = new Label(playerList.get(i).getPlayerInfo()[j] + "");
                playerLabels[i][j].setStyle("-fx-text-fill: white;  -fx-font-size: 16;");
                gridPane.add(playerLabels[i][j], j, i+1);
            }
        }

 /*       for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 1; j++) {
                Label label = new Label(str[i]);
                label.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
                tilePane.getChildren().add(label);
            }
        }*/
    }


    public void updateScoreboard() {
        for(int i = 0; i < playerList.size(); i++) {
            for(int j = 0; j < 5; j++) {
                playerLabels[i][j].setText(playerList.get(i).getPlayerInfo()[j] + "");
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
