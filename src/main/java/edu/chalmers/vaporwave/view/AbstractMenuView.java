package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonState;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractMenuView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private Image backgroundImage;

    private Group root;

    public AbstractMenuView(Group root) {
        this.root = root;
        backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        backgroundGC = backgroundCanvas.getGraphicsContext2D();
        backgroundImage = Container.getImage(ImageID.MENU_BACKGROUND_1);
    }

    public abstract void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown);


    public GraphicsContext getBackgroundGC() {
        return this.backgroundGC;
    }

    public void clearView() {
        this.backgroundGC.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    public void setActive() {
        this.root.getChildren().clear();
        this.root.getChildren().add(new ImageView(backgroundImage));
        this.root.getChildren().add(backgroundCanvas);
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
    }

    public void updateButton(MenuButtonSprite button, boolean selected, boolean pressedDown) {
        if (selected && !pressedDown) {
            button.render(getBackgroundGC(), MenuButtonState.SELECTED);
        } else if (selected && pressedDown) {
            button.render(getBackgroundGC(), MenuButtonState.PRESSED);
        } else {
            button.render(getBackgroundGC(), MenuButtonState.UNSELECTED);
        }
    }

    Group getRoot() {
        return this.root;
    }
}
