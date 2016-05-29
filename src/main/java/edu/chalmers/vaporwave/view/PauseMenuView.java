package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * A view for pausing in game.
 */
public class PauseMenuView {

    private Canvas pauseCanvas;
    private GraphicsContext pauseGC;

    private Sprite pauseMenuSprite;
    private Group root;

    public PauseMenuView(Group root) {
        this.root = root;

        this.pauseMenuSprite = Container.getSprite(SpriteID.MENU_PAUSE);

        this.pauseCanvas = new Canvas(this.pauseMenuSprite.getWidth(), this.pauseMenuSprite.getHeight());
        this.pauseCanvas.setLayoutX(Math.round(Constants.WINDOW_WIDTH / 2.0 - this.pauseMenuSprite.getWidth() / 2.0));
        this.pauseCanvas.setLayoutY(Math.round(Constants.GAME_HEIGHT / 2.0 + (Constants.WINDOW_HEIGHT-Constants.GAME_HEIGHT) / 2
                - this.pauseMenuSprite.getHeight() / 2.0));
        this.pauseCanvas.setVisible(false);

        this.root.getChildren().add(this.pauseCanvas);

        this.pauseGC = this.pauseCanvas.getGraphicsContext2D();
        this.pauseMenuSprite.render(this.pauseGC,-1);
    }


    public void show() {
        pauseCanvas.setVisible(true);
    }

    public void hide() {
        pauseCanvas.setVisible(false);
    }
}