package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Displayes the rooster buttons and changes them depending on which item is selected.
 * Nothing over-exciting.
 */
public class RoosterMenuView extends AbstractMenuView {

    private List<MenuButtonSprite> menuButtonSpriteList;
    private int[] selectedPlayers;
    private List<Point> roosterSelectPosition;
    private Map<Boolean, List<SpriteID>> roosterSelectors;

    public RoosterMenuView(Group root) {
        super(root);

        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_ROOSTER));

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
        Container.getSprite(SpriteID.MENU_ROOSTER_HELP).setPosition(Constants.WINDOW_WIDTH-
                Container.getSprite(SpriteID.MENU_ROOSTER_HELP).getWidth()-4, 4);
        Container.getSprite(SpriteID.MENU_ROOSTER_HELP).setScale(1);
        Container.getSprite(SpriteID.MENU_ROOSTER_HELP).render(getBackgroundGC(), 0);

        if (this.menuButtonSpriteList.size() == 0) {
            this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
            this.menuButtonSpriteList.add(null);
            this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT,
                    new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 100)));
        }

        for (int i = 0; i < this.selectedPlayers.length; i++) {
            renderSpriteForSelectedPlayers(i, superSelected == 1 && subSelected[1] == i);
        }

        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {
            if (this.menuButtonSpriteList.get(i) != null) {
                updateButton(this.menuButtonSpriteList.get(i), superSelected == i, pressedDown);
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
