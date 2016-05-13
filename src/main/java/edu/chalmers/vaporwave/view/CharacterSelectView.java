package edu.chalmers.vaporwave.view;


import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.assetcontainer.SpriteContainer;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSelectView extends AbstractMenuView {

    private List<MenuButtonView> menuButtonViewList;
    private List<Map<Integer, Point>> characterSelectorPositionList;

    public CharacterSelectView(Group root) {
        super(root);

        this.characterSelectorPositionList = new ArrayList<>();
        Map<Integer, Point> characterSelectorPositions1 = new HashMap<>();
        characterSelectorPositions1.put(0, new Point(560, 180));
        characterSelectorPositions1.put(1, new Point(640, 140));
        characterSelectorPositions1.put(2, new Point(800, 140));
        characterSelectorPositions1.put(3, new Point(920, 190));

        this.characterSelectorPositionList.add(characterSelectorPositions1);

        menuButtonViewList = new ArrayList<>();
        menuButtonViewList.add(new MenuButtonView(new Image("images/menu/spritesheet_menu_draft.png"), 365, 67, 2, new Point(40, 20)));
        menuButtonViewList.add(null);
        menuButtonViewList.add(new MenuButtonView(new Image("images/menu/spritesheet_menu_draft.png"), 365, 67, 3, new Point(740, 580)));

        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_ALL).setPosition(new Point(550,160));
        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_ALL).setScale(1);

        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(new Point(550,160));
        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setScale(1);
    }

    @Override
    public void updateView(int superSelected, int[] subSelected) {
        clearView();

        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_ALL).render(getBackgroundGC(), 0);



        for (int i = 0; i < menuButtonViewList.size(); i++) {
            if (menuButtonViewList.get(i) != null) {
                if (superSelected == i) {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.SELECTED);
                } else {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
                }
            }
        }


        System.out.println(subSelected[1]);
        System.out.println("subSelected[1]: " + ((subSelected[1] * 90) + 550));
        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(this.characterSelectorPositionList.get(0).get(subSelected[1]));
        SpriteContainer.getInstance().getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).render(getBackgroundGC(), 0);

        setActive();

    }

    public void updateView(int superSelected, int[] subSelected, boolean isPressed) {

    }





}
