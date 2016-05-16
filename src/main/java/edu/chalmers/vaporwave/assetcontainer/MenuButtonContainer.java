package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.view.MenuButtonView;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class MenuButtonContainer {

    private Map<MenuButtonID, MenuButtonView> menuButtonContainer;

    private static double tasksDone;
    private static final double totalTasks = 4 + 1 + 2;

    MenuButtonContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.menuButtonContainer = new HashMap<>();

        Image menuButtonSpritesheet = Container.getImage(ImageID.MENU_BUTTON_SPRITESHEET);
        int buttonWidth = 308;
        int buttonHeight = 66;

        // Start menu (4)
        addButton(MenuButtonID.BUTTON_NEW_GAME, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 0)));
        addButton(MenuButtonID.BUTTON_EXIT_GAME, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 1)));
        addButton(MenuButtonID.BUTTON_OPTIONS, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 2)));
        addButton(MenuButtonID.BUTTON_HIGHSCORE, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(0, 3)));

        // Character select (1)
        addButton(MenuButtonID.BUTTON_START_GAME, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 0)));

        // Misc (2)
        addButton(MenuButtonID.BUTTON_BACK, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 2)));
        addButton(MenuButtonID.BUTTON_NEXT, new MenuButtonView(menuButtonSpritesheet, buttonWidth, buttonHeight, new Point(1, 1)));

    }

    private void addButton(MenuButtonID menuButtonID, MenuButtonView menuButton) {
        this.menuButtonContainer.put(menuButtonID, menuButton);
        tasksDone++;
    }

    MenuButtonView getButton(MenuButtonID menuButtonID) {
        return this.menuButtonContainer.get(menuButtonID);
    }

    MenuButtonView getButton(MenuButtonID menuButtonID, Point positionOnCanvas) {
        MenuButtonView theButton = getButton(menuButtonID);
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
