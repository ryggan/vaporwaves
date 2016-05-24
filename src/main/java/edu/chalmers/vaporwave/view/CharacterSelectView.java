package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.CPUPlayer;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;


import java.awt.*;
import java.awt.Image;
import java.util.*;
import java.util.List;

public class CharacterSelectView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;
    private List<Map<Integer, Point>> characterSelectorPositionList;
    private Set<Player> playerSet;
    private int[] selectedCharacter;

    private static final Point CHARACTERS_POSITION = new Point(405, 95);
    private SpriteID[][] selectedCharacterSprite;

    private SpriteID[] playerOneSprite;

    private int lastSelected;

    private Label[] characterStats=new Label[4];
    private Canvas characterSelectCanvas;
    AnchorPane pane;



    public CharacterSelectView(Group root) {
        super(root);
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_CHARACTERSELECT));
        //this.setBackgroundImage(new javafx.scene.image.Image("images/charselectbuttons.png"));
        characterSelectCanvas = new javafx.scene.canvas.Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        pane = new AnchorPane();
        Label mei = new Label();
        mei.setText(Constants.MEI_STATS);
        Label alyssa = new Label();
        mei.setText(Constants.ALYSSA_STATS);
        Label zypher = new Label();
        mei.setText(Constants.ZYPHER_STATS);
        Label charlotte = new Label();
        mei.setText(Constants.CHARLOTTE_STATS);

        characterStats[0]=mei;
        characterStats[1]=alyssa;
        characterStats[2]=zypher;
        characterStats[3]=charlotte;

        for(Label label:characterStats){
            label.setStyle("-fx-font-family: 'Lucida Console'; -fx-text-fill: black;  -fx-font-size: 72;");
            pane.getChildren().add(label);
        }
        pane.setVisible(true);
        pane.setPrefSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        pane.toFront();

        root.getChildren().add(characterSelectCanvas);
        root.getChildren().add(pane);




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
        characterSelectorPositions1.put(0, new Point(427, 129));
        characterSelectorPositions1.put(1, new Point(427+626/4, 65));
        characterSelectorPositions1.put(2, new Point(427+2*(626/4), 111));
        characterSelectorPositions1.put(3, new Point(427+3*(626/4), 170));

        Map<Integer, Point> characterSelectorPositions2 = new HashMap<>();
        characterSelectorPositions2.put(0, new Point(427+24+6, 129));
        characterSelectorPositions2.put(1, new Point(427+(626/4)+24+6, 65));
        characterSelectorPositions2.put(2, new Point(427+2*(626/4)+24+6, 111));
        characterSelectorPositions2.put(3, new Point(427+3*(626/4)+24+6, 170));

        Map<Integer, Point> characterSelectorPositions3 = new HashMap<>();
        characterSelectorPositions3.put(0, new Point(427+2*(24+6), 129));
        characterSelectorPositions3.put(1, new Point(427+(626/4)+2*(24+6), 65));
        characterSelectorPositions3.put(2, new Point(427+2*(626/4)+2*(24+6), 111));
        characterSelectorPositions3.put(3, new Point(427+3*(626/4)+2*(24+6), 170));

        Map<Integer, Point> characterSelectorPositions4 = new HashMap<>();
        characterSelectorPositions4.put(0, new Point(427+3*(24+6), 129));
        characterSelectorPositions4.put(1, new Point(427+(626/4)+3*(24+6), 65));
        characterSelectorPositions4.put(2, new Point(427+2*(626/4)+3*(24+6), 111));
        characterSelectorPositions4.put(3, new Point(427+3*(626/4)+3*(24+6), 170));

        this.characterSelectorPositionList.add(characterSelectorPositions1);
        this.characterSelectorPositionList.add(characterSelectorPositions2);
        this.characterSelectorPositionList.add(characterSelectorPositions3);
        this.characterSelectorPositionList.add(characterSelectorPositions4);

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
        menuButtonSpriteList.add(null);
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME, new Point(1080-320,
                Constants.WINDOW_HEIGHT-72-4)));

        Container.getSprite(SpriteID.MENU_CHARACTER_ALL).setPosition(CHARACTERS_POSITION);


    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

       pane.setVisible(true);


        Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).setPosition(Constants.WINDOW_WIDTH-
                Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).getWidth()-4, 4);
        Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).setScale(1);
        Container.getSprite(SpriteID.MENU_CHARACTERSELECT_HELP).render(getBackgroundGC(), 0);
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
            lastSelected=subSelected[1];
        }

        Container.getSprite(playerOneSprite[lastSelected]).setPosition(Constants.WINDOW_WIDTH/28,Constants.WINDOW_HEIGHT/9);
        Container.getSprite(playerOneSprite[lastSelected]).setScale(1);
        Container.getSprite(playerOneSprite[lastSelected]).render(getBackgroundGC(), 0);

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
        this.playerSet = playerSet;
    }

    public boolean playerSetContainsPlayerWithID(int id) {
        for (Player player : this.playerSet) {
            if(player.getPlayerID() == id && !player.getClass().equals(CPUPlayer.class)) {
                return true;
            }
        }
        return false;
    }
}
