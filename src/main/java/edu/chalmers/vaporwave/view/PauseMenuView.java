package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.AIHeuristics;
import edu.chalmers.vaporwave.model.game.Blast;
import edu.chalmers.vaporwave.model.game.Explosive;
import edu.chalmers.vaporwave.model.game.Wall;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PauseMenuView {

    private Canvas pauseCanvas;
    private GraphicsContext pauseGC;

    private Sprite pauseMenuSprite;
    private Group root;
    private Label[][] heuristicLabels;
    private GridPane heuristicPane;
    private static final double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
    private static final double yoffset = 0;

    // private Sprite pauseMenuBackground;
    //private ArrayList<Label> labels;
    //private AnchorPane pauseMenuPane;
    //private Label options;
   // private Label resumeGame;
   // private Label quitGame;
    //private GridPane gridPane;


    public PauseMenuView(Group root, List<Label> labels) {
        this.root = root;

        heuristicLabels = new Label[21][15];
        this.pauseMenuSprite = Container.getSprite(SpriteID.MENU_PAUSE);
        this.pauseMenuSprite.setScale(1);

        this.pauseCanvas = new Canvas(pauseMenuSprite.getWidth(),pauseMenuSprite.getHeight());
        this.root.getChildren().add(pauseCanvas);
        pauseCanvas.setLayoutX(Math.round(Constants.WINDOW_WIDTH / 2.0 - this.pauseMenuSprite.getWidth() / 2.0));
        pauseCanvas.setLayoutY(Math.round(Constants.GAME_HEIGHT/2.0 + (Constants.WINDOW_HEIGHT-Constants.GAME_HEIGHT)/2
                - this.pauseMenuSprite.getHeight() / 2.0));
        pauseCanvas.setVisible(false);

        heuristicPane = new GridPane();
        heuristicPane.setLayoutX(xoffset);
        heuristicPane.setLayoutY(yoffset);
        heuristicPane.setPrefSize(21*Constants.DEFAULT_TILE_WIDTH, 15*Constants.DEFAULT_TILE_HEIGHT);
        heuristicPane.setPadding(new Insets(Constants.DEFAULT_TILE_HEIGHT/2, Constants.DEFAULT_TILE_WIDTH/2, Constants.DEFAULT_TILE_HEIGHT/2, Constants.DEFAULT_TILE_WIDTH/2));
        heuristicPane.setVisible(false);
        this.root.getChildren().add(heuristicPane);



        this.pauseGC = pauseCanvas.getGraphicsContext2D();
        pauseMenuSprite.render(pauseGC,-1);

/*        pauseMenuBackground.setPosition(0, 0);
        pauseMenuBackground.setScale(Constants.GAME_SCALE);
        pauseMenuBackground.render(pauseMenuGC, -1);*/

        //pauseMenuPane = new AnchorPane();
        //old size was 512 448
        //pauseMenuPane.setPrefSize(400, 100);
        //Get proper values for middle of screen
        //pauseMenuPane.setLayoutX(340);
        //pauseMenuPane.setLayoutY(250);
        //root.getChildren().add(pauseMenuPane);
        //pauseMenuPane.setStyle("-fx-background-color: #00ffc3;");
        //pauseMenuPane.setVisible(false);


       // root.getChildren().add(pauseCanvas);
       // pauseMenu.setVisible(true);

        //gridPane = new GridPane();
 /*       gridPane.set
        gridPane.setPrefColumns(1);
        gridPane.setPrefRows(3);*/
        //gridPane.setLayoutX(pauseMenuPane.getWidth()/2);
        //gridPane.setVgap(50);
        //old size was 512 448
        //gridPane.setPrefSize(400, 100);
        //gridPane.setAlignment(Pos.TOP_CENTER);
        //gridPane.setPadding(new Insets(40, 0, 40, 0));


        //pauseMenuPane.getChildren().add(gridPane);
        //resumeGame = labels.get(0);
        //options = labels.get(0);
       // quitGame = labels.get(2);


        //options.setStyle("-fx-font-size: 30; -fx-text-fill: WHITE;");

        //Consider adding these options later, but for now only let one be there
        //gridPane.add(resumeGame, 0, 0);
        //gridPane.add(options, 0, 0);
        //gridPane.add(quitGame, 0, 2);
        //gridPane.setVisible(true);
        //gridPane.toFront();
    }

    public void showHeuristicValues() {
        if(heuristicLabels[0][0] == null) {
            for (int i = 0; i < AIHeuristics.getSimpleHeuristics().length; i++) {
                for (int j = 0; j < AIHeuristics.getSimpleHeuristics()[0].length; j++) {
                    heuristicLabels[i][j] = new Label("" + AIHeuristics.getSimpleHeuristics()[i][j]);
                    this.heuristicPane.add(heuristicLabels[i][j], i, j);
                    heuristicLabels[i][j].setLayoutX(Utils.gridToCanvasPositionX(i));
                    heuristicLabels[i][j].setLayoutY(Utils.gridToCanvasPositionX(j));
                    heuristicLabels[i][j].setPrefWidth(Constants.DEFAULT_TILE_WIDTH);
                    heuristicLabels[i][j].setPrefHeight(Constants.DEFAULT_TILE_HEIGHT);
                }
            }
        } else {
            for (int i = 0; i < AIHeuristics.getSimpleHeuristics().length; i++) {
                for (int j = 0; j < AIHeuristics.getSimpleHeuristics()[0].length; j++) {
                    heuristicLabels[i][j].setText("" + AIHeuristics.getSimpleHeuristics()[i][j]);
                }
            }
        }
        heuristicPane.setVisible(true);
    }

    public void show() {
        showHeuristicValues();
        pauseCanvas.setVisible(true);
    }

    public void hide() {
        heuristicPane.setVisible(false);
        pauseCanvas.setVisible(false);
    }
}