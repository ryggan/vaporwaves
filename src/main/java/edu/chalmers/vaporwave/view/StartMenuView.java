package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartMenuView extends AbstractMenuView {

    private List<MenuButtonView> menuButtonViewList;

    public StartMenuView(Group root) {
        super(root);


        menuButtonViewList = new ArrayList<>();
        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_singleplayer.png"), 308, 66, 0, new Point(640, 200)));
        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_exit.png"), 308, 66, 0, new Point(640, 280)));

    }

    public void updateView(int superSelected, int[] subSelected) {
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




}
