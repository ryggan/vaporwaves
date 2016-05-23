package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CharacterSelectView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;
    private List<Map<Integer, Point>> characterSelectorPositionList;
    private Set<Player> playerSet;
    private int[] selectedCharacter;

    private static final Point CHARACTERS_POSITION = new Point(550, 160);
    private SpriteID[][] selectedCharacterSprite;

    private SpriteID[] playerOneSprite;

    public CharacterSelectView(Group root) {
        super(root);

        this.playerSet = new HashSet<>();

        this.selectedCharacter = new int[Constants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < this.selectedCharacter.length; i++) {
            this.selectedCharacter[i] = -1;
        }

        this.playerOneSprite = new SpriteID[4];
        this.playerOneSprite[0]=SpriteID.MENU_RESULTS_MEI;
        this.playerOneSprite[1]=SpriteID.MENU_RESULTS_ALYSSA;
        this.playerOneSprite[2]=SpriteID.MENU_RESULTS_ZYPHER;
        this.playerOneSprite[3]=SpriteID.MENU_RESULTS_CHARLOTTE;

        this.selectedCharacterSprite = new SpriteID[4][4];
        this.selectedCharacterSprite[0][0] = SpriteID.MENU_CHARACTER_MEI_1;
        this.selectedCharacterSprite[0][1] = SpriteID.MENU_CHARACTER_MEI_2;
        this.selectedCharacterSprite[0][2] = SpriteID.MENU_CHARACTER_MEI_3;
        this.selectedCharacterSprite[0][3] = SpriteID.MENU_CHARACTER_MEI_4;
        this.selectedCharacterSprite[1][0] = SpriteID.MENU_CHARACTER_ALYSSA_1;
        this.selectedCharacterSprite[1][1] = SpriteID.MENU_CHARACTER_ALYSSA_2;
        this.selectedCharacterSprite[1][2] = SpriteID.MENU_CHARACTER_ALYSSA_3;
        this.selectedCharacterSprite[1][3] = SpriteID.MENU_CHARACTER_ALYSSA_4;
        this.selectedCharacterSprite[2][0] = SpriteID.MENU_CHARACTER_ZYPHER_1;
        this.selectedCharacterSprite[2][1] = SpriteID.MENU_CHARACTER_ZYPHER_2;
        this.selectedCharacterSprite[2][2] = SpriteID.MENU_CHARACTER_ZYPHER_3;
        this.selectedCharacterSprite[2][3] = SpriteID.MENU_CHARACTER_ZYPHER_4;
        this.selectedCharacterSprite[3][0] = SpriteID.MENU_CHARACTER_CHARLOTTE_1;
        this.selectedCharacterSprite[3][1] = SpriteID.MENU_CHARACTER_CHARLOTTE_2;
        this.selectedCharacterSprite[3][2] = SpriteID.MENU_CHARACTER_CHARLOTTE_3;
        this.selectedCharacterSprite[3][3] = SpriteID.MENU_CHARACTER_CHARLOTTE_4;

        this.characterSelectorPositionList = new ArrayList<>();
        Map<Integer, Point> characterSelectorPositions1 = new HashMap<>();
        characterSelectorPositions1.put(0, new Point(550, 180));
        characterSelectorPositions1.put(1, new Point(640, 140));
        characterSelectorPositions1.put(2, new Point(800, 140));
        characterSelectorPositions1.put(3, new Point(920, 190));

        Map<Integer, Point> characterSelectorPositions2 = new HashMap<>();
        characterSelectorPositions2.put(0, new Point(570, 180));
        characterSelectorPositions2.put(1, new Point(660, 135));
        characterSelectorPositions2.put(2, new Point(820, 145));
        characterSelectorPositions2.put(3, new Point(940, 185));

        Map<Integer, Point> characterSelectorPositions3 = new HashMap<>();
        characterSelectorPositions3.put(0, new Point(590, 180));
        characterSelectorPositions3.put(1, new Point(680, 135));
        characterSelectorPositions3.put(2, new Point(840, 145));
        characterSelectorPositions3.put(3, new Point(960, 185));

        Map<Integer, Point> characterSelectorPositions4 = new HashMap<>();
        characterSelectorPositions4.put(0, new Point(610, 180));
        characterSelectorPositions4.put(1, new Point(700, 135));
        characterSelectorPositions4.put(2, new Point(860, 145));
        characterSelectorPositions4.put(3, new Point(980, 185));

        this.characterSelectorPositionList.add(characterSelectorPositions1);
        this.characterSelectorPositionList.add(characterSelectorPositions2);
        this.characterSelectorPositionList.add(characterSelectorPositions3);
        this.characterSelectorPositionList.add(characterSelectorPositions4);

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_BACK, new Point(40, 20)));
        menuButtonSpriteList.add(null);
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME, new Point(740, 580)));

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).setPosition(CHARACTERS_POSITION);


    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).render(getBackgroundGC(), 0);

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            if (menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        if (superSelected == 1) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(this.characterSelectorPositionList.get(0).get(subSelected[1]));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).render(getBackgroundGC(), 0);
            Container.getSprite(playerOneSprite[subSelected[1]]).setPosition(Constants.WINDOW_WIDTH/28,Constants.WINDOW_HEIGHT/9);
            Container.getSprite(playerOneSprite[subSelected[1]]).setScale(1);
            Container.getSprite(playerOneSprite[subSelected[1]]).render(getBackgroundGC(), 0);
        }

        if (playerSetContainsPlayerWithID(1)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).setPosition(this.characterSelectorPositionList.get(1).get(Utils.calculateRemoteSelected(remoteSelected, 1, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).render(getBackgroundGC(), 0);
        }

        if (playerSetContainsPlayerWithID(2)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_3).setPosition(this.characterSelectorPositionList.get(2).get(Utils.calculateRemoteSelected(remoteSelected, 2, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_3).render(getBackgroundGC(), 0);
        }

        if (playerSetContainsPlayerWithID(3)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_4).setPosition(this.characterSelectorPositionList.get(3).get(Utils.calculateRemoteSelected(remoteSelected, 3, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_4).render(getBackgroundGC(), 0);
        }

        for (int i = 0; i < this.selectedCharacter.length; i++) {
            if (this.selectedCharacter[i] >= 0) {
                Container.getSprite(this.selectedCharacterSprite[i][this.selectedCharacter[i]]).setPosition(CHARACTERS_POSITION);
                Container.getSprite(this.selectedCharacterSprite[i][this.selectedCharacter[i]]).render(getBackgroundGC(), 0);
            }

        }

        super.setActive();
    }

    public void setSelectedCharacters(int[] selectedCharacter) {
        this.selectedCharacter = ArrayCloner.intArrayCloner(selectedCharacter);
    }

    public void setPlayers(Set<Player> playerSet) {
        System.out.println("Setting players");
        this.playerSet = playerSet;
    }

    public boolean playerSetContainsPlayerWithID(int id) {
        for (Player player : this.playerSet) {
            if(player.getPlayerID() == id) {
                return true;
            }
        }
        return false;
    }
}
