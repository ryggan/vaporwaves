package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ImageContainer {

    private static Map<ImageID, Image> imageContainer;

    private static int tasksDone = 0;
    private static int totalTasks = 0;
    private static Set<Pair<ImageID, String>> imageSet = new HashSet<>();

    public static void initImageContainer() throws Exception {

        imageContainer = new HashMap<>();

        // >>> MENU STUFF: <<<

        // Results menu (4):
        prepareImageLoad(ImageID.MENU_RESULTS_ZYPHER, "images/menu/menu-zypher-result.png");
        prepareImageLoad(ImageID.MENU_RESULTS_CHARLOTTE, "images/menu/menu-charlotte-result.png");
        prepareImageLoad(ImageID.MENU_RESULTS_ALYSSA, "images/menu/menu-alyssa-result.png");
        prepareImageLoad(ImageID.MENU_RESULTS_MEI, "images/menu/menu-mei-result.png");

        // Backgrounds (8):
        prepareImageLoad(ImageID.MENU_BACKGROUND_RESULT, "images/menu/menu-background-result.png");
        prepareImageLoad(ImageID.MENU_BACKGROUND_1, "images/menu/menu-background-2.bmp");
        prepareImageLoad(ImageID.MENU_BUTTON_SPRITESHEET, "images/menu/spritesheet_menubuttons-308x198.png");
        prepareImageLoad(ImageID.MENU_SMALL_BUTTON_SPRITESHEET, "images/menu/spritesheet-menubuttons-92x22.png");
        prepareImageLoad(ImageID.MENU_BACKGROUND_CHARACTERSELECT, "images/menu/menu-background-characterselect.png");
        prepareImageLoad(ImageID.MENU_BACKGROUND_ROOSTER, "images/menu/menu-background-roostermenu.png");
        prepareImageLoad(ImageID.MENU_BACKGROUND_LOADING, "images/menu/menu-background-loading.png");
        prepareImageLoad(ImageID.MENU_BACKGROUND_START, "images/menu/menu-background-startmenu.png");

        // Character select (5):
        prepareImageLoad(ImageID.MENU_CHARACTERSELECT_HELP, "images/menu/menu-characterselect-help.png");
        prepareImageLoad(ImageID.MENU_CHARACTER, "images/menu/spritesheet-character-select-443x626.png");
        prepareImageLoad(ImageID.MENU_CHARACTER_SELECTOR, "images/menu/spritesheet-character-selector-24x27.png");
        prepareImageLoad(ImageID.MENU_CHECKBOX, "images/menu/spritesheet-menu-checkbox-111x118.png");
        prepareImageLoad(ImageID.MENU_ROOSTER_SELECT, "images/menu/spritesheet-rooster-select-133x94.png");

        //Rooster (1):
        prepareImageLoad(ImageID.MENU_ROOSTER_HELP, "images/menu/menu-rooster-help.png");

        //Start (1):
        prepareImageLoad(ImageID.MENU_STARTMENU_HELP, "images/menu/menu-startmenu-help.png");

        // >>> GAME STUFF: <<<

        // HUD stuff (5):
        prepareImageLoad(ImageID.MENU_PAUSE, "images/hud/pause_message.png");
        prepareImageLoad(ImageID.HUD_BOX_SHEET, "images/hud/spritesheet-hudbox-136x180.png");
        prepareImageLoad(ImageID.HUD_TIMER_MESSAGE, "images/hud/timer_message.png");
        prepareImageLoad(ImageID.HUD_GAMEOVER_MESSAGE, "images/hud/gameover_message.png");
        //        prepareImageLoad(ImageID.SCOREBOARD_BACK, "images/hud/scoreboardBackground.png");
        prepareImageLoad(ImageID.SCOREBOARD_BACK, "images/hud/scoreboardBackground2.png");

        // Background stuff (6):
        prepareImageLoad(ImageID.BACKGROUND_PATTERN_1, "images/backgroundPatterns/pattern1.png");

        prepareImageLoad(ImageID.GAME_BACKGROUND_1, "images/gamebackground/sprite-arenabackground-03.png");
        prepareImageLoad(ImageID.GAME_FRAME_NORTH_1, "images/gamebackground/sprite-frame-beach-north.png");
        prepareImageLoad(ImageID.GAME_FRAME_WEST_1, "images/gamebackground/sprite-frame-beach-west.png");
        prepareImageLoad(ImageID.GAME_FRAME_EAST_1, "images/gamebackground/sprite-frame-beach-east.png");
        prepareImageLoad(ImageID.GAME_FRAME_SOUTH_1, "images/gamebackground/spritesheet-frame-beach-south-402x54.png");

        // Character/Enemy spritesheets (13):
        prepareImageLoad(ImageID.CHARACTER_MISC, "images/characters/spritesheet-character-misc-48x48.png");

        prepareImageLoad(ImageID.CHARACTER_ALYSSA_WALKIDLEFLINCH, "images/characters/spritesheet-alyssa-walkidleflinch2-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_ALYSSA_DEATH, "images/characters/spritesheet-alyssa-death-56x56.png");
        prepareImageLoad(ImageID.CHARACTER_ALYSSA_SPAWN, "images/characters/spritesheet-alyssa-respawn-48x128.png");

        prepareImageLoad(ImageID.CHARACTER_CHARLOTTE_WALKIDLEFLINCH, "images/characters/spritesheet-charlotte-walkidleflinch-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_CHARLOTTE_DEATH, "images/characters/spritesheet-charlotte-death-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_CHARLOTTE_SPAWN, "images/characters/spritesheet-charlotte-respawn-48x128.png");

        prepareImageLoad(ImageID.CHARACTER_ZYPHER_WALKIDLEFLINCH, "images/characters/spritesheet-zypher-walkidleflinch-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_ZYPHER_DEATH, "images/characters/spritesheet-zypher-death-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_ZYPHER_SPAWN, "images/characters/spritesheet-zypher-respawn-48x128.png");

        prepareImageLoad(ImageID.CHARACTER_MEI_WALKIDLEFLINCHDEATH, "images/characters/spritesheet-mei-walkidleflinchdeath-48x48.png");
        prepareImageLoad(ImageID.CHARACTER_MEI_SPAWN, "images/characters/spritesheet-mei-respawn-48x128.png");

        prepareImageLoad(ImageID.ENEMY_PCCHAN, "images/spritesheet-enemy-pcchan-walkidleflinchspawndeath-48x48.png");

        // Tile spritesheets (3):
        prepareImageLoad(ImageID.BOMBS_EXPLOSIONS, "images/spritesheet-bombs_and_explosions-18x18_v2.png");
        prepareImageLoad(ImageID.WALLS, "images/spritesheet-walls_both-18x18.png");
        prepareImageLoad(ImageID.POWERUPS, "images/spritesheet-powerups-18x18.png");

        addImages();
    }

    private static void prepareImageLoad(ImageID imageID, String imageString) {
        imageSet.add(new Pair<>(imageID, imageString));
        totalTasks += 1;
    }

    private static void addImages() {
        for (Pair<ImageID, String> pair : imageSet) {
            addImage(pair.getFirst(), new Image(pair.getSecond()));
        }
    }

    private static void addImage(ImageID imageID, Image image) {
        imageContainer.put(imageID, image);
        tasksDone += 1;
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
