package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMenuView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private Image backgroundImage;

    private Map<MenuState, List<MenuButtonView>> menuCategoryMap;

    private Group root;

    public AbstractMenuView(Group root) {
        this.root = root;
        backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        backgroundGC = backgroundCanvas.getGraphicsContext2D();
        backgroundImage = ImageContainer.getInstance().getImage(ImageID.MENU_BACKGROUND_1);

        menuCategoryMap = new HashMap<>();

    }

    public abstract void updateView(int superSelected, int[] subSelected);

    public GraphicsContext getBackgroundGC() {
        return this.backgroundGC;
    }

    public void clearView() {
        this.backgroundGC.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    public void setActive() {
        this.root.getChildren().clear();
//        Image backgroundImage = new Image("images/menu-background-2.bmp");
        this.root.getChildren().remove(backgroundImage);
        this.root.getChildren().add(new ImageView(backgroundImage));
        this.root.getChildren().add(backgroundCanvas);
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;

    }

}
