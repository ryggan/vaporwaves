package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
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
    private int[] selectedCharacter;

    private static final Point CHARACTERS_POSITION = new Point(550, 160);
    private SpriteID[][] selectedCharacterSprite;

    public CharacterSelectView(Group root) {
        super(root);

        this.selectedCharacter = new int[]{-1, -1, -1, -1};

        this.selectedCharacterSprite = new SpriteID[4][2];
        this.selectedCharacterSprite[0][0] = SpriteID.MENU_CHARACTER_MEI_1;
        this.selectedCharacterSprite[0][1] = SpriteID.MENU_CHARACTER_MEI_2;
        this.selectedCharacterSprite[1][0] = SpriteID.MENU_CHARACTER_ALYSSA_1;
        this.selectedCharacterSprite[1][1] = SpriteID.MENU_CHARACTER_ALYSSA_2;
        this.selectedCharacterSprite[2][0] = SpriteID.MENU_CHARACTER_ZYPHER_1;
        this.selectedCharacterSprite[2][1] = SpriteID.MENU_CHARACTER_ZYPHER_2;
        this.selectedCharacterSprite[3][0] = SpriteID.MENU_CHARACTER_CHARLOTTE_1;
        this.selectedCharacterSprite[3][1] = SpriteID.MENU_CHARACTER_CHARLOTTE_2;

        this.characterSelectorPositionList = new ArrayList<>();
        Map<Integer, Point> characterSelectorPositions1 = new HashMap<>();
        characterSelectorPositions1.put(0, new Point(550, 180));
        characterSelectorPositions1.put(1, new Point(640, 140));
        characterSelectorPositions1.put(2, new Point(800, 140));
        characterSelectorPositions1.put(3, new Point(920, 190));

        Map<Integer, Point> characterSelectorPositions2 = new HashMap<>();
        characterSelectorPositions2.put(0, new Point(580, 180));
        characterSelectorPositions2.put(1, new Point(670, 135));
        characterSelectorPositions2.put(2, new Point(830, 145));
        characterSelectorPositions2.put(3, new Point(950, 185));

        this.characterSelectorPositionList.add(characterSelectorPositions1);
        this.characterSelectorPositionList.add(characterSelectorPositions2);

        menuButtonViewList = new ArrayList<>();
        menuButtonViewList.add(Container.getButton(MenuButtonID.BUTTON_BACK, new Point(40, 20)));
//        menuButtonViewList.add(new MenuButtonView(new Image("images/menu/spritesheet_menu_draft.png"), 365, 67, new Point(0, 0), new Point(40, 20)));
        menuButtonViewList.add(null);
        menuButtonViewList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME, new Point(740, 580)));
//        menuButtonViewList.add(new MenuButtonView(new Image("images/menu/spritesheet_menu_draft.png"), 365, 67, new Point(0, 0), new Point(740, 580)));

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).setPosition(CHARACTERS_POSITION);
        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).setScale(1);

        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(new Point(550,160));
        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setScale(1);

        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).setPosition(new Point(580,160));
        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).setScale(1);
    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player) {
        clearView();

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).render(getBackgroundGC(), 0);

        for (int i = 0; i < menuButtonViewList.size(); i++) {
            if (menuButtonViewList.get(i) != null) {
                if (superSelected == i) {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.SELECTED);
                } else {
                    menuButtonViewList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
                }
            }
        }

        if (superSelected == 1) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(this.characterSelectorPositionList.get(0).get(subSelected[1]));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).render(getBackgroundGC(), 0);
        }

        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).setPosition(this.characterSelectorPositionList.get(1).get(Utils.calculateRemoteSelected(remoteSelected, 1, 4)));
        Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).render(getBackgroundGC(), 0);

        for (int i = 0; i < selectedCharacter.length; i++) {
            if (selectedCharacter[i] >= 0) {
                Container.getSprite(selectedCharacterSprite[i][selectedCharacter[i]]).setPosition(CHARACTERS_POSITION);
                Container.getSprite(selectedCharacterSprite[i][selectedCharacter[i]]).setScale(1);
                Container.getSprite(selectedCharacterSprite[i][selectedCharacter[i]]).render(getBackgroundGC(), 0);
            }
        }


        setActive();
    }

    public void setSelectedCharacters(int[] selectedCharacter) {
        this.selectedCharacter = ArrayCloner.intArrayCloner(selectedCharacter);
    }

    // todo: what is dis
    public void updateView(int superSelected, int[] subSelected, boolean isPressed) {

    }
}
