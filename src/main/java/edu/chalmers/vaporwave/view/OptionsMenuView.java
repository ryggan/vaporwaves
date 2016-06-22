package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;

/**
 * Created by Lina on 2016-06-22.
 */
public class OptionsMenuView extends AbstractMenuView{
    private java.util.List<MenuButtonSprite> menuButtonSpriteList;
    
    public OptionsMenuView(Group root) {
        super(root);
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_START));

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEW_GAME, new Point(640, 200)));
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_OPTIONS, new Point(640, 280)));
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_EXIT_GAME, new Point(640, 360)));
    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();
        Container.getSprite(SpriteID.MENU_TITLE).setPosition(70,70);
        Container.getSprite(SpriteID.MENU_TITLE).render(getBackgroundGC(),0);

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();
    }
}
