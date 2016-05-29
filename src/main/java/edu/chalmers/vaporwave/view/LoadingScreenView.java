package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates a loading backdrop and a loadingbar, and updates the loadingbar every frame
 * with the loading progress percentage.
 */
public class LoadingScreenView  {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private Image backgroundImage;
    private Group root;

    private Image loadingBarEmpty;
    private ImageView loadingBarEmptyIV;
    private Image loadingBarFilled;

    private double offsetX = 44;
    private double offsetY = 48;

    public LoadingScreenView(Group root) throws Exception {
        this.root = root;
        this.backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();

        this.backgroundImage = new Image("images/menu/menu-background-loading.png");
        this.loadingBarEmpty = new Image("images/hud/healthbarempty.png");
        this.loadingBarFilled = new Image("images/hud/healthbarfilled.png");

        double positionX = Constants.WINDOW_WIDTH / 2 - this.loadingBarEmpty.getWidth() / 2;
        double positionY = Constants.WINDOW_HEIGHT / 2 - this.loadingBarEmpty.getHeight() / 2;
        this.loadingBarEmptyIV = new ImageView(this.loadingBarEmpty);
        this.loadingBarEmptyIV.setX(positionX);
        this.loadingBarEmptyIV.setY(positionY);

        this.root.getChildren().add(new ImageView(this.backgroundImage));
        this.root.getChildren().add(this.loadingBarEmptyIV);
        this.root.getChildren().add(backgroundCanvas);
    }

    public void updateView(double percentageLoaded) {
        this.backgroundGC.drawImage(this.loadingBarFilled, 0, 0,
                this.loadingBarFilled.getWidth(), this.loadingBarFilled.getHeight(),
                this.loadingBarEmptyIV.getX() + offsetX, this.loadingBarEmptyIV.getY() + offsetY,
                this.loadingBarFilled.getWidth() * percentageLoaded, this.loadingBarFilled.getHeight());
    }
}
