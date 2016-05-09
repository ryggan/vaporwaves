package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageContainer {

    private static ImageContainer instance;

    private Map<ImageID, Image> imageContainer;

    private ImageContainer() {

        this.imageContainer = new HashMap<>();

        // todo: MENU STUFF:

        // Backgrounds:
        this.imageContainer.put(ImageID.MENU_BACKGROUND_1, new Image("images/menu-background-2.bmp"));

        // Start menu:
        this.imageContainer.put(ImageID.BUTTON_SINGLEPLAYER, new Image("images/spritesheet_singleplayer.png"));
        this.imageContainer.put(ImageID.BUTTON_EXIT, new Image("images/spritesheet_exit.png"));

        // Character select:
        this.imageContainer.put(ImageID.MENU_CHARACTER_SELECT, new Image("images/spritesheet_character_menu_draft.png"));

        // todo: GAME STUFF:

        // Background stuff:
        this.imageContainer.put(ImageID.BACKGROUND_PATTERN_1, new Image("images/backgroundPatterns/pattern1.png"));

        this.imageContainer.put(ImageID.GAME_BACKGROUND_1, new Image("images/background/sprite-arenabackground-03.png"));
        this.imageContainer.put(ImageID.GAME_FRAME_NORTH_1, new Image("images/background/sprite-frame-beach-north.png"));
        this.imageContainer.put(ImageID.GAME_FRAME_WEST_1, new Image("images/background/sprite-frame-beach-west.png"));
        this.imageContainer.put(ImageID.GAME_FRAME_EAST_1, new Image("images/background/sprite-frame-beach-east.png"));
        this.imageContainer.put(ImageID.GAME_FRAME_SOUTH_1, new Image("images/background/spritesheet-frame-beach-south-402x54.png"));

        // Character/Enemy spritesheets:
        this.imageContainer.put(ImageID.CHARACTER_MISC, new Image("images/characters/spritesheet-character-misc-48x48.png"));

        this.imageContainer.put(ImageID.CHARACTER_ALYSSA_WALKIDLEFLINCH, new Image("images/characters/spritesheet-alyssa-walkidleflinch2-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_ALYSSA_DEATH, new Image("images/characters/spritesheet-alyssa-death-56x56.png"));
        this.imageContainer.put(ImageID.CHARACTER_ALYSSA_SPAWN, new Image("images/characters/spritesheet-alyssa-respawn-48x128.png"));

        this.imageContainer.put(ImageID.CHARACTER_CHARLOTTE_WALKIDLEFLINCH, new Image("images/characters/spritesheet-charlotte-walkidleflinch-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_CHARLOTTE_DEATH, new Image("images/characters/spritesheet-charlotte-death-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_CHARLOTTE_SPAWN, new Image("images/characters/spritesheet-charlotte-respawn-48x128.png"));

        this.imageContainer.put(ImageID.CHARACTER_ZYPHER_WALKIDLEFLINCH, new Image("images/characters/spritesheet-alyssa-walkidleflinch2-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_ZYPHER_DEATH, new Image("images/characters/spritesheet-alyssa-death-56x56.png"));
        this.imageContainer.put(ImageID.CHARACTER_ZYPHER_SPAWN, new Image("images/characters/spritesheet-alyssa-respawn-48x128.png"));

        this.imageContainer.put(ImageID.CHARACTER_MEI_WALKIDLEFLINCH, new Image("images/characters/spritesheet-charlotte-walkidleflinch-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_MEI_DEATH, new Image("images/characters/spritesheet-charlotte-death-48x48.png"));
        this.imageContainer.put(ImageID.CHARACTER_MEI_SPAWN, new Image("images/characters/spritesheet-charlotte-respawn-48x128.png"));

        this.imageContainer.put(ImageID.ENEMY_PCCHAN, new Image("images/spritesheet-enemy-pcchan-walkidleflinchspawndeath-48x48.png"));

        // Tile spritesheets:
        this.imageContainer.put(ImageID.BOMBS_EXPLOSIONS, new Image("images/spritesheet-bombs_and_explosions-18x18_v2.png"));
        this.imageContainer.put(ImageID.WALLS, new Image("images/spritesheet-walls_both-18x18.png"));
        this.imageContainer.put(ImageID.POWERUPS, new Image("images/spritesheet-powerups-18x18.png"));

    }

    public Image getImage(ImageID imageID) {
        return this.imageContainer.get(imageID);
    }

    public static ImageContainer getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new ImageContainer();
        }
    }

}
