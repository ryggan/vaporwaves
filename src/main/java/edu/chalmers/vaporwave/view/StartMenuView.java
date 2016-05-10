package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.util.ImageID;
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
//        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_singleplayer.png"), 308, 66, 0, new Point(640, 200)));
//        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_exit.png"), 308, 66, 0, new Point(640, 280)));
        menuButtonViewList.add(new MenuButtonView(ImageContainer.getInstance().getImage(ImageID.BUTTON_SINGLEPLAYER),
                308, 66, 0, new Point(640, 200)));
        menuButtonViewList.add(new MenuButtonView(ImageContainer.getInstance().getImage(ImageID.BUTTON_EXIT),
                308, 66, 0, new Point(640, 280)));

    }

    public void updateView(int superSelected, int[] subSelected) {
        clearView();

//        setBackgroundImage(new Image("images/menu-background.jpg"));
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





}
