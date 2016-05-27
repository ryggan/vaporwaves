package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartMenuView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;

    public StartMenuView(Group root) {
        super(root);
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_START));

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEW_GAME, new Point(640, 200)));
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_EXIT_GAME, new Point(640, 280)));
    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();
        Container.getSprite(SpriteID.MENU_TITLE).setScale(1);
        Container.getSprite(SpriteID.MENU_TITLE).setPosition(70,70);
        Container.getSprite(SpriteID.MENU_TITLE).render(getBackgroundGC(),0);


        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();
    }
}
