package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class MenuButtonContainer {

    private static Map<MenuButtonID, MenuButtonSprite> menuButtonContainer;

    private static int tasksDone = 0;
    private static int totalTasks = 0;
    private static Set<Pair<MenuButtonID, MenuButtonSprite>> menuButtonSet = new HashSet<>();

    public static void initMenuButtonContainer() throws Exception {
        menuButtonContainer = new HashMap<>();

        final Image menuButtonSpritesheet = Container.getImage(ImageID.MENU_BUTTON_SPRITESHEET);
        final Image smallMenuButtonSpritesheet = Container.getImage(ImageID.MENU_SMALL_BUTTON_SPRITESHEET);
        final int buttonWidth = 308;
        final int buttonHeight = 66;
        final int smallButtonWidth = 92;
        final int smallButtonHeight = 22;

        // Start menu (4)
        prepareButtonLoad(MenuButtonID.BUTTON_NEW_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 0)));
        prepareButtonLoad(MenuButtonID.BUTTON_EXIT_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 1)));
        prepareButtonLoad(MenuButtonID.BUTTON_OPTIONS, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 2)));
        prepareButtonLoad(MenuButtonID.BUTTON_HIGHSCORE, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 3)));

        // Character select (2)
        prepareButtonLoad(MenuButtonID.BUTTON_START_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 0)));

        prepareButtonLoad(MenuButtonID.BUTTON_SMALL_BACK, new MenuButtonSprite(smallMenuButtonSpritesheet, smallButtonWidth, smallButtonHeight, new Point(0, 0)));

        // Misc (2)
        prepareButtonLoad(MenuButtonID.BUTTON_BACK, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 2)));
        prepareButtonLoad(MenuButtonID.BUTTON_NEXT, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 1)));

        addButtons();
    }

    private static void prepareButtonLoad(MenuButtonID menuButtonID, MenuButtonSprite menuButtonSprite) {
        menuButtonSet.add(new Pair<>(menuButtonID, menuButtonSprite));
        totalTasks += 1;
    }

    private static void addButtons() {
        for (Pair<MenuButtonID, MenuButtonSprite> pair : menuButtonSet) {
            addButton(pair.getFirst(), pair.getSecond());
        }
    }

    
    private static void addButton(MenuButtonID menuButtonID, MenuButtonSprite menuButton) {
        menuButtonContainer.put(menuButtonID, menuButton);
        tasksDone++;
    }

    static MenuButtonSprite getButton(MenuButtonID menuButtonID) {
        return menuButtonContainer.get(menuButtonID);
    }

    static MenuButtonSprite getButton(MenuButtonID menuButtonID, Point positionOnCanvas) {
        MenuButtonSprite theButton = getButton(menuButtonID);
        theButton.setPositionOnCanvas(positionOnCanvas);
        return theButton;
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
