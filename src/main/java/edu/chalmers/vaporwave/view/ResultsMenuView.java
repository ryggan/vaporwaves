package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;

public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonView> menuButtonViewList;

    public ResultsMenuView(Group root){
        super(root);
        setBackgroundImage(ImageContainer.getInstance().getImage(ImageID.MENU_BACKGROUND_1));

        menuButtonViewList = new ArrayList<>();
//        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_singleplayer.png"), 308, 66, 0, new Point(640, 200)));
//        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_exit.png"), 308, 66, 0, new Point(640, 280)));
        menuButtonViewList.add(new MenuButtonView(ImageContainer.getInstance().getImage(ImageID.BUTTON_EXIT),
                308, 66, 0, new Point(640, 280)));
    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, int playerActionPerformed) {
        clearView();

        for (int i = 0; i < menuButtonViewList.size(); i++) {

            if (superSelected == i) {
                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.SELECTED);
            } else {
                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
            }
        }
        setActive();

    }

    public void setPressed(int superSelected){
        for (int i = 0; i < menuButtonViewList.size(); i++) {

            if (superSelected == i) {
                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
            } else {
                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
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
