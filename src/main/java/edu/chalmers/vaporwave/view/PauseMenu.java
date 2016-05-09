package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.text.Position;
import java.util.ArrayList;

public class PauseMenu {
    private Player localPlayer;
    private Sprite pauseMenuBackground;
    private Canvas pauseMenu;
    private GraphicsContext pauseMenuGC;
    private ArrayList<Label> labels;
    private Group root;
    private AnchorPane pauseMenuPane;
    private Label options;
    private Label resumeGame;
    private Label quitGame;
    private GridPane gridPane;

    private static double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE) + 2 * Constants.DEFAULT_TILE_WIDTH;
    private static double yoffset = 8 * Constants.DEFAULT_TILE_HEIGHT;

    //For testing purposes
    public PauseMenu(Group root) {
        this.root = root;

        //pauseMenuBackground = new Sprite("pausemenu");
        pauseMenu = new Canvas(Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_WIDTH * Constants.GAME_SCALE,
                Constants.DEFAULT_TILE_WIDTH * Constants.DEFAULT_GRID_HEIGHT * Constants.GAME_SCALE);
        pauseMenuGC = pauseMenu.getGraphicsContext2D();
        pauseMenu.setLayoutX(xoffset);
        pauseMenu.setLayoutY(yoffset);

/*        pauseMenuBackground.setPosition(0, 0);
        pauseMenuBackground.setScale(Constants.GAME_SCALE);
        pauseMenuBackground.render(pauseMenuGC, -1);*/

        pauseMenuPane = new AnchorPane();
        pauseMenuPane.setPrefSize(512, 448);
        //Get proper values for middle of screen
        pauseMenuPane.setLayoutX(280);
        pauseMenuPane.setLayoutY(150);
        root.getChildren().add(pauseMenuPane);
        pauseMenuPane.setStyle("-fx-background-color: #00ffc3;");
        pauseMenuPane.setVisible(false);


        root.getChildren().add(pauseMenu);
       // pauseMenu.setVisible(true);

        gridPane = new GridPane();
 /*       gridPane.set
        gridPane.setPrefColumns(1);
        gridPane.setPrefRows(3);*/
        gridPane.setLayoutX(pauseMenuPane.getWidth()/2);
        gridPane.setVgap(50);
        gridPane.setPrefSize(512, 448);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(40, 0, 40, 0));


        pauseMenuPane.getChildren().add(gridPane);
        resumeGame = new Label("Resume");
        options = new Label("Options");
        quitGame = new Label("Quit");

        //resumeGame.getStyleClass().add("labels");
        resumeGame.setStyle("-fx-font-size: 30; -fx-text-fill: WHITE;");
        quitGame.setStyle("-fx-font-size: 30; -fx-text-fill: WHITE;");
        options.setStyle("-fx-font-size: 30; -fx-text-fill: WHITE;");

        gridPane.add(resumeGame, 0, 0);
        gridPane.add(options, 0, 1);
        gridPane.add(quitGame, 0, 2);
        gridPane.setVisible(true);
        gridPane.toFront();
    }

    public void show() {
       // pauseMenu.setVisible(true);
        pauseMenuPane.setVisible(true);
        pauseMenuPane.toFront();
    }

    public void hide() {
        //pauseMenu.setVisible(false);
        pauseMenuPane.setVisible(false);
    }
}