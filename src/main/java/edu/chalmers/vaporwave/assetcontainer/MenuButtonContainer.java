package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This container contains MenuButtonSprites, which is almost the same thing as usual sprites,
 * with small exceptions.
 * This one also calculates it's total amount of tasks dynamically.
 */
class MenuButtonContainer {

    static class MenuButtonSpriteProperties {
        ImageID imageID;
        int width;
        int height;
        Point gridPositionInSheet;

        public MenuButtonSpriteProperties(ImageID imageID, int width, int height, Point gridPositionInSheet) {
            this.imageID = imageID;
            this.width = width;
            this.height = height;
            this.gridPositionInSheet = gridPositionInSheet;
        }
    }

    private static Map<MenuButtonID, MenuButtonSprite> menuButtonContainer;

    private static int tasksDone = 0;
    private static int totalTasks = 0;
    private static Set<Pair<MenuButtonID, MenuButtonSpriteProperties>> menuButtonSet = new HashSet<>();

    // First all images is prepared in a set, partly to be able to count how many tasks to be done.
    // Then the loading of the external files takes place, which is the part that takes time.
    static void prepare() throws Exception {
        menuButtonContainer = new HashMap<>();

        final int buttonWidth = 308;
        final int buttonHeight = 66;

        // Start menu (4)
        prepareButtonLoad(MenuButtonID.BUTTON_NEW_GAME, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(0, 0));
        prepareButtonLoad(MenuButtonID.BUTTON_EXIT_GAME, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(0, 1));
        prepareButtonLoad(MenuButtonID.BUTTON_OPTIONS, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(0, 2));
        prepareButtonLoad(MenuButtonID.BUTTON_HIGHSCORE, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(0, 3));

        // Character select (2)
        prepareButtonLoad(MenuButtonID.BUTTON_START_GAME, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(1, 0));
        prepareButtonLoad(MenuButtonID.BUTTON_SMALL_BACK, ImageID.MENU_SMALL_BUTTON_SPRITESHEET, 92, 22, new Point(0, 0));

        // Map select (1)
        prepareButtonLoad(MenuButtonID.BUTTON_CHANGE_THEME, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(1, 3));

        // Misc (2)
        prepareButtonLoad(MenuButtonID.BUTTON_BACK, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(1, 2));
        prepareButtonLoad(MenuButtonID.BUTTON_NEXT, ImageID.MENU_BUTTON_SPRITESHEET, buttonWidth, buttonHeight, new Point(1, 1));
    }

    static void init() {
        addButtons();
    }

    // prepareButtonLoad calculates total tasks, and addButton counts up how many tasks are done
    private static void prepareButtonLoad(MenuButtonID menuButtonID, ImageID imageID, int width, int height, Point gridPositionInSheet) {
        menuButtonSet.add(new Pair<>(menuButtonID, new MenuButtonSpriteProperties(imageID, width, height, gridPositionInSheet)));
        totalTasks += 1;
    }

    private static void addButtons() {
        for (Pair<MenuButtonID, MenuButtonSpriteProperties> pair : menuButtonSet) {
            MenuButtonSpriteProperties prop = pair.getSecond();
            addButton(pair.getFirst(),
                    new MenuButtonSprite(Container.getImage(prop.imageID), prop.width, prop.height, prop.gridPositionInSheet));
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
