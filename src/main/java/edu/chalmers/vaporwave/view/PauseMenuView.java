package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class PauseMenuView {

    private Canvas pauseCanvas;
    private GraphicsContext pauseGC;

    private Sprite pauseMenuSprite;
    private Group root;

    // private Sprite pauseMenuBackground;
    //private ArrayList<Label> labels;
    //private AnchorPane pauseMenuPane;
    //private Label options;
   // private Label resumeGame;
   // private Label quitGame;
    //private GridPane gridPane;


    public PauseMenuView(Group root, List<Label> labels) {
        this.root = root;

        this.pauseMenuSprite = Container.getSprite(SpriteID.MENU_PAUSE);
        this.pauseMenuSprite.setScale(1);

        this.pauseCanvas = new Canvas(pauseMenuSprite.getWidth(),pauseMenuSprite.getHeight());
        this.root.getChildren().add(pauseCanvas);
        pauseCanvas.setLayoutX(Math.round(Constants.WINDOW_WIDTH / 2.0 - this.pauseMenuSprite.getWidth() / 2.0));
        pauseCanvas.setLayoutY(Math.round(Constants.GAME_HEIGHT/2.0 + (Constants.WINDOW_HEIGHT-Constants.GAME_HEIGHT)/2
                - this.pauseMenuSprite.getHeight() / 2.0));
        pauseCanvas.setVisible(false);



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

    public void show() {
        pauseCanvas.setVisible(true);
       // pauseMenuPane.setVisible(true);
      //  pauseMenuPane.toFront();

    }

    public void hide() {
        pauseCanvas.setVisible(false);
       // pauseMenuPane.setVisible(false);
    }
}