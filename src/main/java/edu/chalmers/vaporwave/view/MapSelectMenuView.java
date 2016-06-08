package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Displayes the rooster buttons and changes them depending on which item is selected.
 * Nothing over-exciting.
 */
public class MapSelectMenuView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;

    public MapSelectMenuView(Group root) {
        super(root);

        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_MAPSELECT));

        // The good ol' usual buttons
        this.menuButtonSpriteList = new ArrayList<>();
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
        this.menuButtonSpriteList.add(null);
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)));
    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {
            if (this.menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        setActive();
    }
}
