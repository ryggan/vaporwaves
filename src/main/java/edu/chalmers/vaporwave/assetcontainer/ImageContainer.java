package edu.chalmers.vaporwave.assetcontainer;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

class ImageContainer {

    private static Map<ImageID, Image> imageContainer;

    private static double tasksDone;
    private static final double totalTasks = 4 + 3 + 4 + 5 + 6 + 13 + 3;
    
    public static void initImageContainer() {
        // TODO: OBS!!! IF ADDING IMAGES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        imageContainer = new HashMap<>();

        // >>> MENU STUFF: <<<

        // Results menu (4):
        addImage(ImageID.MENU_RESULTS_ZYPHER,new Image("images/menu/menu-zypher-result.png"));
        addImage(ImageID.MENU_RESULTS_CHARLOTTE,new Image("images/menu/menu-charlotte-result.png"));
        addImage(ImageID.MENU_RESULTS_ALYSSA,new Image("images/menu/menu-alyssa-result.png"));
        addImage(ImageID.MENU_RESULTS_MEI,new Image("images/menu/menu-mei-result.png"));

        // Backgrounds (3):
        addImage(ImageID.MENU_BACKGROUND_RESULT, new Image("images/menu/menu-background-result.png"));
        addImage(ImageID.MENU_BACKGROUND_1, new Image("images/menu/menu-background-2.bmp"));
        addImage(ImageID.MENU_BUTTON_SPRITESHEET, new Image("images/menu/spritesheet_menubuttons-308x198.png"));

        // Character select (4):
        addImage(ImageID.MENU_CHARACTER, new Image("images/menu/spritesheet-character-select-442x378.png"));
        addImage(ImageID.MENU_CHARACTER_SELECTOR, new Image("images/menu/spritesheet-character-selector-22x33.png"));
        addImage(ImageID.MENU_CHECKBOX, new Image("images/menu/spritesheet-menu-checkbox-111x118.png"));
        addImage(ImageID.MENU_ROOSTER_SELECT, new Image("images/menu/spritesheet-rooster-select-133x94.png"));

        // >>> GAME STUFF: <<<

        // HUD stuff (5):
        addImage(ImageID.MENU_PAUSE, new Image("images/hud/pause_message.png"));
        addImage(ImageID.HUD_BOX_SHEET, new Image("images/hud/spritesheet-hudbox-136x180.png"));
        addImage(ImageID.HUD_TIMER_MESSAGE, new Image("images/hud/timer_message.png"));
        addImage(ImageID.HUD_GAMEOVER_MESSAGE, new Image("images/hud/gameover_message.png"));
//        addImage(ImageID.SCOREBOARD_BACK, new Image("images/hud/scoreboardBackground.png"));
        addImage(ImageID.SCOREBOARD_BACK, new Image("images/hud/scoreboardBackground2.png"));

        // Background stuff (6):
        addImage(ImageID.BACKGROUND_PATTERN_1, new Image("images/backgroundPatterns/pattern1.png"));

        addImage(ImageID.GAME_BACKGROUND_1, new Image("images/gamebackground/sprite-arenabackground-03.png"));
        addImage(ImageID.GAME_FRAME_NORTH_1, new Image("images/gamebackground/sprite-frame-beach-north.png"));
        addImage(ImageID.GAME_FRAME_WEST_1, new Image("images/gamebackground/sprite-frame-beach-west.png"));
        addImage(ImageID.GAME_FRAME_EAST_1, new Image("images/gamebackground/sprite-frame-beach-east.png"));
        addImage(ImageID.GAME_FRAME_SOUTH_1, new Image("images/gamebackground/spritesheet-frame-beach-south-402x54.png"));

        // Character/Enemy spritesheets (13):
        addImage(ImageID.CHARACTER_MISC, new Image("images/characters/spritesheet-character-misc-48x48.png"));

        addImage(ImageID.CHARACTER_ALYSSA_WALKIDLEFLINCH, new Image("images/characters/spritesheet-alyssa-walkidleflinch2-48x48.png"));
        addImage(ImageID.CHARACTER_ALYSSA_DEATH, new Image("images/characters/spritesheet-alyssa-death-56x56.png"));
        addImage(ImageID.CHARACTER_ALYSSA_SPAWN, new Image("images/characters/spritesheet-alyssa-respawn-48x128.png"));

        addImage(ImageID.CHARACTER_CHARLOTTE_WALKIDLEFLINCH, new Image("images/characters/spritesheet-charlotte-walkidleflinch-48x48.png"));
        addImage(ImageID.CHARACTER_CHARLOTTE_DEATH, new Image("images/characters/spritesheet-charlotte-death-48x48.png"));
        addImage(ImageID.CHARACTER_CHARLOTTE_SPAWN, new Image("images/characters/spritesheet-charlotte-respawn-48x128.png"));

        addImage(ImageID.CHARACTER_ZYPHER_WALKIDLEFLINCH, new Image("images/characters/spritesheet-zypher-walkidleflinch-48x48.png"));
        addImage(ImageID.CHARACTER_ZYPHER_DEATH, new Image("images/characters/spritesheet-zypher-death-48x48.png"));
        addImage(ImageID.CHARACTER_ZYPHER_SPAWN, new Image("images/characters/spritesheet-zypher-respawn-48x128.png"));

        addImage(ImageID.CHARACTER_MEI_WALKIDLEFLINCHDEATH, new Image("images/characters/spritesheet-mei-walkidleflinchdeath-48x48.png"));
        addImage(ImageID.CHARACTER_MEI_SPAWN, new Image("images/characters/spritesheet-mei-respawn-48x128.png"));

        addImage(ImageID.ENEMY_PCCHAN, new Image("images/spritesheet-enemy-pcchan-walkidleflinchspawndeath-48x48.png"));

        // Tile spritesheets (3):
        addImage(ImageID.BOMBS_EXPLOSIONS, new Image("images/spritesheet-bombs_and_explosions-18x18_v2.png"));
        addImage(ImageID.WALLS, new Image("images/spritesheet-walls_both-18x18.png"));
        addImage(ImageID.POWERUPS, new Image("images/spritesheet-powerups-18x18.png"));
    }

    private static void addImage(ImageID imageID, Image image) {
        imageContainer.put(imageID, image);
        tasksDone ++;
    }

    static Image getImage(ImageID imageID) {
        return imageContainer.get(imageID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }
}
