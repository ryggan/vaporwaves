package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bob on 2016-05-09.
 */
public class ImageContainer {

    private static ImageContainer instance;

    private Map<ImageID, Image> imageContainer;

    private ImageContainer() {

        this.imageContainer = new HashMap<>();

        // todo: MENU STUFF:



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

        // Tile spritesheets:
        this.imageContainer.put(ImageID.BOMBS_EXPLOSIONS, new Image("images/spritesheet-bombs_and_explosions-18x18_v2.png"));
        this.imageContainer.put(ImageID.WALLS, new Image("images/spritesheet-walls_both-18x18.png"));
        this.imageContainer.put(ImageID.POWERUPS, new Image("images/spritesheet-powerups-18x18.png"));

//        this.imageContainer.put(ImageID.CHARACTER_MISC, new Image());

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
