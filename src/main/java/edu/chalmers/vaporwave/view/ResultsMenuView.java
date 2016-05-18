package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonSprite;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonID;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonState;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;

public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;

    public ResultsMenuView(Group root){
        super(root);
        setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_1));

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT, new Point(640, 280)));
//        menuButtonSpriteList.add(new MenuButtonSprite(Container.getImage(ImageID.BUTTON_EXIT), 308, 66, new Point(0, 0), new Point(640, 280)));
    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();

    }

    public void setPressed(int superSelected){
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {

            if (superSelected == i) {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
            } else {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
            }
        }
        setActive();
    }


    //characters killed
    //score
    //deathcount
    //enemies killed
    //picture of character (sad/glad)
    //results title
    //highscore screen after?
    //if new highscore
    //winner name
    //victory/defeat
    //rank (noob, ok, pro, hacker)
    //powerups picked up
    //
}
