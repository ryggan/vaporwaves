package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoosterMenuView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;

    public RoosterMenuView(Group root) {
        super(root);

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_BACK, new Point(40, 20)));
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT, new Point(740, 580)));
    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            if (menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        setActive();
    }
}
