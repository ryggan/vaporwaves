package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.model.Player;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Lina on 2016-06-22.
 */
public class ControlsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;


    public ControlsMenuView(Group root) {
        super(root);
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_OPTIONS));

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_BACK, new Point(240, 200)));

    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();

    }
}
