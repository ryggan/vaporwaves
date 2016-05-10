package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadingScreenView  {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private Image backgroundImage;
    private Group root;

    public LoadingScreenView(Group root) {
        this.root = root;
        this.backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        this.backgroundImage = new Image("images/menu-background.jpg");

//        this.root.getChildren().clear();
        this.root.getChildren().add(new ImageView(this.backgroundImage));
        this.root.getChildren().add(backgroundCanvas);
    }

    public void updateView() {

    }
}
