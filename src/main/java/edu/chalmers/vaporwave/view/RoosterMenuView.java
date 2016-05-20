package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.Player;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoosterMenuView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;
    private int[] selectedPlayers;
    private List<Point> roosterSelectPosition;
    private Map<Boolean, List<SpriteID>> roosterSelectors;

    public RoosterMenuView(Group root) {
        super(root);
        this.menuButtonSpriteList = new ArrayList<>();

        this.roosterSelectPosition = new ArrayList<>();
        this.roosterSelectPosition.add(new Point(120, 300));
        this.roosterSelectPosition.add(new Point(350, 300));
        this.roosterSelectPosition.add(new Point(580, 300));
        this.roosterSelectPosition.add(new Point(830, 300));

        this.selectedPlayers = new int[0];

        List roosterSelectorsOff = new ArrayList<>();
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_OPEN_OFF);
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_P1_OFF);
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_P2_OFF);
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_P3_OFF);
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_P4_OFF);
        roosterSelectorsOff.add(SpriteID.MENU_ROOSTER_SELECT_CPU_OFF);

        List roosterSelectorsOn = new ArrayList<>();
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_OPEN_ON);
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_P1_ON);
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_P2_ON);
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_P3_ON);
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_P4_ON);
        roosterSelectorsOn.add(SpriteID.MENU_ROOSTER_SELECT_CPU_ON);

        this.roosterSelectors = new HashMap<>();
        this.roosterSelectors.put(Boolean.FALSE, roosterSelectorsOff);
        this.roosterSelectors.put(Boolean.TRUE, roosterSelectorsOn);

    }

    @Override
    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        if (this.menuButtonSpriteList.size() == 0) {
            this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_BACK, new Point(40, 20)));
            this.menuButtonSpriteList.add(null);
            this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT, new Point(740, 580)));
        }

        for (int i = 0; i < this.selectedPlayers.length; i++) {
            renderSpriteForSelectedPlayers(i, superSelected == 1 && subSelected[1] == i);
        }

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            if (menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        setActive();
    }

    private void renderSpriteForSelectedPlayers(int slot, boolean showSelected) {
        Container.getSprite(this.roosterSelectors.get(showSelected).get(this.selectedPlayers[slot])).setPosition(this.roosterSelectPosition.get(slot));
        Container.getSprite(this.roosterSelectors.get(showSelected).get(this.selectedPlayers[slot])).render(getBackgroundGC(), 0);
    }

    public void setSelectedPlayers(int[] selectedPlayers) {
        this.selectedPlayers = selectedPlayers.clone();
    }
}
