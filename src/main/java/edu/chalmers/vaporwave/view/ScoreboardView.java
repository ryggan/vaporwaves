package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScoreboardView {
    private Sprite scoreboardBackground;
    private Canvas scoreboardCanvas;
    private GraphicsContext scoreboardGC;
    private List<Player> playerList;
    private AnchorPane scoreboardPane;
    private GridPane gridPane;
    private Label[][] playerLabels;
    private boolean isShowing;

    private static final double ELEMENT_SIZE = 100;
//    private static final double GAP = ELEMENT_SIZE / 10;

    private static double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE)  + 2*Constants.DEFAULT_TILE_WIDTH;

            //Math.floor((Constants.WINDOW_WIDTH - (Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE) / 2));
    private static double yoffset = 8 * Constants.DEFAULT_TILE_HEIGHT;

            //Math.floor((Constants.WINDOW_HEIGHT - (Constants.DEFAULT_TILE_HEIGHT * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE) / 2));

    //For testing purposes
    public ScoreboardView(Group root, Set<Player> players) {
        //this.root = root;
        this.playerList = new ArrayList<>(players.size());
        this.playerLabels = new Label[players.size()][5];

        for (Player player : players) {
            this.playerList.add(player.getPlayerID(), player);
            System.out.println("Player id: "+player.getPlayerID());
        }


        //fix filepath after creating image
        scoreboardCanvas = new Canvas(Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE,
                Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE);
        scoreboardGC = scoreboardCanvas.getGraphicsContext2D();

        scoreboardBackground = Container.getSprite(SpriteID.SCOREBOARD_BACKGROUND);
        scoreboardCanvas.setLayoutX(xoffset);
        scoreboardCanvas.setLayoutY(yoffset-10);

        scoreboardBackground.setPosition(0, 0);
        scoreboardBackground.render(scoreboardGC, -1);

        scoreboardPane = new AnchorPane();
        scoreboardPane.setPrefSize(512, 448);

        //Get proper values for middle of screen
        scoreboardPane.setLayoutX(0);
        scoreboardPane.setLayoutY(0);
        root.getChildren().add(scoreboardPane);
        scoreboardPane.setVisible(false);

        this.isShowing = false;

        root.getChildren().add(scoreboardCanvas);
        scoreboardCanvas.setVisible(false);

        addPlayersToScoreboard();

    }

    public void showScoreboard() {
        updateScoreboard();
        scoreboardCanvas.setVisible(true);
        scoreboardPane.setVisible(true);
        scoreboardPane.toFront();
        isShowing = true;
    }

    public void hideScoreboard() {
        scoreboardCanvas.setVisible(false);
        scoreboardPane.setVisible(false);
        isShowing = false;
    }

    //should have "ArrayList<Player> players" as argument, made dummy list in method for testing purposes
    public void addPlayersToScoreboard(/*ArrayList<Player> players*/) {
        gridPane = new GridPane();
//        gridPane.setPrefSize(672,480);
        gridPane.setPrefSize(scoreboardCanvas.getWidth(), scoreboardBackground.getHeight());
//        gridPane.setHgap(120);
//        gridPane.setVgap(40);
        gridPane.setLayoutX(104);
        gridPane.setLayoutY(176);
        ///gridPane.setPadding(new Insets(50, 50, 50, 50));

        gridPane.setAlignment(Pos.TOP_CENTER);
        //tilePane.setAlignment();
        scoreboardPane.getChildren().add(gridPane);
        createElements();
    }

    private void createElements() {
        gridPane.getChildren().clear();

        for(int i = 0; i < playerList.size(); i++) {
            for(int j = 0; j < 5; j++) {
                playerLabels[i][j] = new Label(playerList.get(i).getPlayerInfo()[j] + "");
                playerLabels[i][j].setStyle("-fx-font-family: 'Lucida Console'; -fx-text-fill: black;  -fx-font-size: 16;");
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

    public boolean isShowing() {
        return this.isShowing;
    }
}
