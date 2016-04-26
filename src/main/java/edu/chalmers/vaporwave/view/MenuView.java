package edu.chalmers.vaporwave.view;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.model.menu.MenuCategory;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private MenuCategory activeMenuCategory;
    private int activeMenuButton;

    private Map<MenuCategory, List<MenuButtonView>> menuCategoryMap;

    private Group root;

    public MenuView(Group root) {
        this.root = root;
        backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        backgroundGC = backgroundCanvas.getGraphicsContext2D();
        Image backgroundImage = new Image("images/menu-background.jpg");

        this.root.getChildren().add(new ImageView(backgroundImage));
        this.root.getChildren().add(backgroundCanvas);

        this.activeMenuCategory = MenuCategory.START;
        this.activeMenuButton = 0;

        menuCategoryMap = new HashMap<>();

        List<MenuButtonView> menuButtonViewList = new ArrayList<>();
        switch (this.activeMenuCategory) {
            case START:
                menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_start_menu_draft.png"), 365, 67, 0, new Point(640, 200)));
                menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_start_menu_draft.png"), 365, 67, 1, new Point(640, 280)));
                this.menuCategoryMap.put(activeMenuCategory, menuButtonViewList);
                break;
        }

    }

    public void updateView(int selected) {
        System.out.println(selected);
        this.activeMenuButton = selected;

        for (int i = 0; i < menuCategoryMap.get(activeMenuCategory).size(); i++) {
            if (activeMenuButton == i) {
                menuCategoryMap.get(activeMenuCategory).get(i).render(backgroundGC, MenuButtonState.SELECTED);
            } else {
                menuCategoryMap.get(activeMenuCategory).get(i).render(backgroundGC, MenuButtonState.UNSELECTED);
            }
        }

    }
}
