package edu.chalmers.vaporwave.view;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.model.menu.MenuCategory;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMenuView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;

    private Map<MenuCategory, List<MenuButtonView>> menuCategoryMap;

    private Group root;

    public AbstractMenuView(Group root) {
        this.root = root;
        backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        backgroundGC = backgroundCanvas.getGraphicsContext2D();


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
        Image backgroundImage = new Image("images/menu-background-2.jpg");

        this.root.getChildren().add(new ImageView(backgroundImage));
        this.root.getChildren().add(backgroundCanvas);
    }

}
