package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.assetcontainer.MenuButtonID;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartMenuView extends AbstractMenuView {

    private List<MenuButtonView> menuButtonViewList;

    public StartMenuView(Group root) {
        super(root);

        menuButtonViewList = new ArrayList<>();
        menuButtonViewList.add(Container.getButton(MenuButtonID.BUTTON_NEW_GAME, new Point(640, 200)));
        menuButtonViewList.add(Container.getButton(MenuButtonID.BUTTON_EXIT_GAME, new Point(640, 280)));

    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player) {
        clearView();

        for (int i = 0; i < menuButtonViewList.size(); i++) {

            if (superSelected == i) {
                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.SELECTED);
//            } else if (superSelected == i && ListenerController.getInstance().getAllPressed().contains("ENTER")) {
//                menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
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





}
