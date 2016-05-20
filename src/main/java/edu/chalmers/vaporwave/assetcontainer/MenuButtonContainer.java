package edu.chalmers.vaporwave.assetcontainer;

import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class MenuButtonContainer {

    private static Map<MenuButtonID, MenuButtonSprite> menuButtonContainer;

    private static double tasksDone;
    private static final double totalTasks = 4 + 1 + 2;

    public static void initMenuButtonContainer() {
        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        menuButtonContainer = new HashMap<>();

        Image menuButtonSpritesheet = Container.getImage(ImageID.MENU_BUTTON_SPRITESHEET);
        int buttonWidth = 308;
        int buttonHeight = 66;

        // Start menu (4)
        addButton(MenuButtonID.BUTTON_NEW_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 0)));
        addButton(MenuButtonID.BUTTON_EXIT_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 1)));
        addButton(MenuButtonID.BUTTON_OPTIONS, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 2)));
        addButton(MenuButtonID.BUTTON_HIGHSCORE, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 3)));

        // Character select (1)
        addButton(MenuButtonID.BUTTON_START_GAME, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 0)));

        // Misc (2)
        addButton(MenuButtonID.BUTTON_BACK, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 2)));
        addButton(MenuButtonID.BUTTON_NEXT, new MenuButtonSprite(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 1)));
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
