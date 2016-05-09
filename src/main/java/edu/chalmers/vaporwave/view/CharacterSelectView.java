package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelectView extends AbstractMenuView {

    private List<MenuButtonView> menuButtonViewList;

    private Image spriteSheet;
    private Point position;


    public CharacterSelectView(Group root) {
        super(root);

        menuButtonViewList = new ArrayList<>();
        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_menu_draft.png"), 365, 67, 2, new Point(40, 20)));
        menuButtonViewList.add(null);
        menuButtonViewList.add(new MenuButtonView(new Image("images/spritesheet_menu_draft.png"), 365, 67, 3, new Point(740, 580)));

//        this.spriteSheet = new Image("images/spritesheet_character_menu_draft.png");
        this.spriteSheet = ImageContainer.getInstance().getImage(ImageID.MENU_CHARACTER_SELECT);
        this.position = new Point(520, 170);
    }

    public void updateView(int superSelected, int[] subSelected) {
        clearView();

        for (int i = 0; i < menuButtonViewList.size(); i++) {
            if (menuButtonViewList.get(i) != null) {
                if (superSelected == i) {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.SELECTED);
                } else {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
                }
            }
        }
        System.out.println("subSelected[1]: " + subSelected[1]);
        this.getBackgroundGC().drawImage(spriteSheet, subSelected[1] * 500, 0, 500, 406, position.x, position.y, 500, 406);
//        return getBackgroundGC();
        setActive();
    }





}
