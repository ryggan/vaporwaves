package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.CPUPlayer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.ClonerUtility;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The character select screen view. There's quite the collection of different elements,
 * but at its core it's still pretty straight forward.
 */
public class CharacterSelectView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;
    private List<Map<Integer, Point>> characterSelectorPositionList;
    private Set<Player> playerSet;
    private int[] selectedCharacter;

    private static final Point CHARACTERS_POSITION = new Point(405, 95);
    private SpriteID[][] selectedCharacterSprite;

    private SpriteID[] playerOneSprite;
    private SpriteID[] playerOneSpriteName;

    private int lastSelected;

    public CharacterSelectView(Group root) {
        super(root);
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_CHARACTERSELECT));

        this.playerSet = new HashSet<>();

        this.getBackgroundGC().setFont(Container.getFont(FileID.FONT_BAUHAUS_18));
        this.getBackgroundGC().setFill(javafx.scene.paint.Paint.valueOf("#FFFFFF"));

        this.playerSet = new HashSet<>();

        this.selectedCharacter = new int[Constants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < this.selectedCharacter.length; i++) {
            this.selectedCharacter[i] = -1;
        }

        // First character selected sprites
        this.playerOneSprite = new SpriteID[4];
        this.playerOneSprite[0] = SpriteID.MENU_CHARACTERSELECT_MEI;
        this.playerOneSprite[1] = SpriteID.MENU_CHARACTERSELECT_ALYSSA;
        this.playerOneSprite[2] = SpriteID.MENU_CHARACTERSELECT_ZYPHER;
        this.playerOneSprite[3] = SpriteID.MENU_CHARACTERSELECT_CHARLOTTE;

        double x = Constants.WINDOW_WIDTH / 28;
        double y = Constants.WINDOW_HEIGHT / 9;
        for (int i = 0; i < 4; i++) {
            Container.getSprite(playerOneSprite[i]).setPosition(x, y);
        }

        this.playerOneSpriteName = new SpriteID[4];
        this.playerOneSpriteName[0] = SpriteID.MENU_SIGN_MEI;
        this.playerOneSpriteName[1] = SpriteID.MENU_SIGN_ALYSSA;
        this.playerOneSpriteName[2] = SpriteID.MENU_SIGN_ZYPHER;
        this.playerOneSpriteName[3] = SpriteID.MENU_SIGN_CHARLOTTE;

        x = 310;
        y = Constants.WINDOW_HEIGHT - 140;
        for (int i = 0; i < 4; i++) {
            Container.getSprite(playerOneSpriteName[i]).setPosition(x, y);
        }

        // Character selection sprites
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

        // Setting up all the different positions of character selectors for four players:
        int basex = 427;
        int[] basey = new int[] {129, 65, 111, 170};
        int add1x = 626 / 4;
        int add2x = 24 + 6;

        this.characterSelectorPositionList = new ArrayList<>();
        Map<Integer, Point> characterSelectorPositions1 = new HashMap<>();
        characterSelectorPositions1.put(0, new Point(basex, basey[0]));
        characterSelectorPositions1.put(1, new Point(basex + add1x, basey[1]));
        characterSelectorPositions1.put(2, new Point(basex + 2 * add1x, basey[2]));
        characterSelectorPositions1.put(3, new Point(basex + 3 * add1x, basey[3]));

        Map<Integer, Point> characterSelectorPositions2 = new HashMap<>();
        characterSelectorPositions2.put(0, new Point(basex + add2x, basey[0]));
        characterSelectorPositions2.put(1, new Point(basex + add1x + add2x, basey[1]));
        characterSelectorPositions2.put(2, new Point(basex + 2 * add1x + add2x, basey[2]));
        characterSelectorPositions2.put(3, new Point(basex + 3 * add1x + add2x, basey[3]));

        Map<Integer, Point> characterSelectorPositions3 = new HashMap<>();
        characterSelectorPositions3.put(0, new Point(basex + 2 * add2x, basey[0]));
        characterSelectorPositions3.put(1, new Point(basex + add1x + 2 * add2x, basey[1]));
        characterSelectorPositions3.put(2, new Point(basex + 2 * add1x + 2 * add2x, basey[2]));
        characterSelectorPositions3.put(3, new Point(basex + 3 * add1x + 2 * add2x, basey[3]));

        Map<Integer, Point> characterSelectorPositions4 = new HashMap<>();
        characterSelectorPositions4.put(0, new Point(basex + 3 * add2x, basey[0]));
        characterSelectorPositions4.put(1, new Point(basex + add1x + 3 * add2x, basey[1]));
        characterSelectorPositions4.put(2, new Point(basex + 2 * add1x + 3 * add2x, basey[2]));
        characterSelectorPositions4.put(3, new Point(basex + 3 * add1x + 3 * add2x, basey[3]));

        this.characterSelectorPositionList.add(characterSelectorPositions1);
        this.characterSelectorPositionList.add(characterSelectorPositions2);
        this.characterSelectorPositionList.add(characterSelectorPositions3);
        this.characterSelectorPositionList.add(characterSelectorPositions4);

        // The good ol' usual buttons
        this.menuButtonSpriteList = new ArrayList<>();
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
        this.menuButtonSpriteList.add(null);
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)));

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).setPosition(CHARACTERS_POSITION);


    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        // Basic stuff
        Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).setPosition(Constants.WINDOW_WIDTH -
                Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).getWidth() - 4, 4);
        Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).render(getBackgroundGC(), 0);
        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).render(getBackgroundGC(), 0);

        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {
            if (this.menuButtonSpriteList.get(i) != null) {
                updateButton(this.menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        if (superSelected == 1) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).setPosition(this.characterSelectorPositionList.get(0).get(subSelected[1]));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_1).render(getBackgroundGC(), 0);
            this.lastSelected = subSelected[1];
        }

        // Rendering selector sprites
        Container.getSprite(playerOneSprite[lastSelected]).render(getBackgroundGC(), 0);
        Container.getSprite(playerOneSpriteName[lastSelected]).render(getBackgroundGC(), 0);

        if (playerSetContainsPlayerWithID(1)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).setPosition(
                    this.characterSelectorPositionList.get(1).get(Utils.calculateRemoteSelected(remoteSelected, 1, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_2).render(getBackgroundGC(), 0);
        }

        if (playerSetContainsPlayerWithID(2)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_3).setPosition(
                    this.characterSelectorPositionList.get(2).get(Utils.calculateRemoteSelected(remoteSelected, 2, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_3).render(getBackgroundGC(), 0);
        }

        if (playerSetContainsPlayerWithID(3)) {
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_4).setPosition(
                    this.characterSelectorPositionList.get(3).get(Utils.calculateRemoteSelected(remoteSelected, 3, 4)));
            Container.getSprite(SpriteID.MENU_CHARACTER_SELECTOR_4).render(getBackgroundGC(), 0);
        }

        // Rendering selected character
        for (int i = 0; i < this.selectedCharacter.length; i++) {
            if (this.selectedCharacter[i] >= 0) {
                Container.getSprite(this.selectedCharacterSprite[i][this.selectedCharacter[i]]).setPosition(CHARACTERS_POSITION);
                Container.getSprite(this.selectedCharacterSprite[i][this.selectedCharacter[i]]).render(getBackgroundGC(), 0);
            }

        }

        // Character stats!
        int offsetx = -5;
        int offsety = 390;

        this.getBackgroundGC().fillText("" + Container.getCharacterSpeed(CharacterID.MEI) + " - " +
                (int)Container.getCharacterHealth(CharacterID.MEI) + " - " +
                Container.getCharacterBombRange(CharacterID.MEI) + " - " +
                Container.getCharacterBombCount(CharacterID.MEI),
                this.characterSelectorPositionList.get(0).get(0).x + offsetx,
                this.characterSelectorPositionList.get(0).get(0).y + offsety);

        this.getBackgroundGC().fillText("" + Container.getCharacterSpeed(CharacterID.ALYSSA) + " - " +
                (int)Container.getCharacterHealth(CharacterID.ALYSSA) + " - " +
                Container.getCharacterBombRange(CharacterID.ALYSSA) + " - " +
                Container.getCharacterBombCount(CharacterID.ALYSSA),
                this.characterSelectorPositionList.get(0).get(1).x + offsetx,
                this.characterSelectorPositionList.get(0).get(1).y + offsety - 5);

        this.getBackgroundGC().fillText("" + Container.getCharacterSpeed(CharacterID.ZYPHER) + " - " +
                (int)Container.getCharacterHealth(CharacterID.ZYPHER) + " - " +
                Container.getCharacterBombRange(CharacterID.ZYPHER) + " - " +
                Container.getCharacterBombCount(CharacterID.ZYPHER),
                this.characterSelectorPositionList.get(0).get(2).x + offsetx,
                this.characterSelectorPositionList.get(0).get(2).y + offsety);

        this.getBackgroundGC().fillText("" + Container.getCharacterSpeed(CharacterID.CHARLOTTE) + " - " +
                (int)Container.getCharacterHealth(CharacterID.CHARLOTTE) + " - " +
                Container.getCharacterBombRange(CharacterID.CHARLOTTE) + " - " +
                Container.getCharacterBombCount(CharacterID.CHARLOTTE),
                this.characterSelectorPositionList.get(0).get(3).x + offsetx,
                this.characterSelectorPositionList.get(0).get(3).y + offsety);

        super.setActive();
    }

    public void setSelectedCharacters(int[] selectedCharacter) {
        this.selectedCharacter = ClonerUtility.intArrayCloner(selectedCharacter);
    }

    public void setPlayers(Set<Player> playerSet) {
        this.playerSet = playerSet;
    }

    // Simply checks if a player with that id is present
    public boolean playerSetContainsPlayerWithID(int id) {
        for (Player player : this.playerSet) {
            if(player.getPlayerID() == id && !player.getClass().equals(CPUPlayer.class)) {
                return true;
            }
        }
        return false;
    }
}
