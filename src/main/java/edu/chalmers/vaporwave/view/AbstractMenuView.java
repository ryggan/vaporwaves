package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonState;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.AbstractMenu;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A template view for all menu screens to come. The update-method is central.
 */
public abstract class AbstractMenuView {

    private Group root;

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private Image backgroundImage;

    private List<MenuButtonSprite[]> allButtons;

    public AbstractMenuView(Group root, AbstractMenu menu) {
        this.root = root;
        backgroundCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        backgroundGC = backgroundCanvas.getGraphicsContext2D();
        backgroundImage = Container.getImage(ImageID.MENU_BACKGROUND_1);

        initiateAllButtons(menu);
    }

    private void initiateAllButtons(AbstractMenu menu) {
        this.allButtons = new ArrayList<>();
        for (boolean[] row : menu.getMenuItems()) {
            MenuButtonSprite[] spriteRow = new MenuButtonSprite[row.length];
            for (int i = 0; i < row.length; i++) {
                spriteRow[i] = null;
            }
            this.allButtons.add(spriteRow);
        }
    }

    // Updates the main menu view, with all selected and unselected, etc.
    public abstract void updateView(List<boolean[]> menuItems, int superSelected, int[] subSelected,
                                    int[] remoteSelected, Player player, boolean pressedDown);

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

    // Re-renders the image of a button
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

    public void setButton(MenuButtonSprite button, int superPosition, int subPosition) {
        this.allButtons.get(superPosition)[subPosition] = button;
    }

    public MenuButtonSprite getButton(int superPosition, int subPosition) {
        return this.allButtons.get(superPosition)[subPosition];
    }
}
