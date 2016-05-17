package edu.chalmers.vaporwave.assetcontainer;

import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class SpriteContainer {

    private Map<SpriteID, Sprite> spriteContainer;

    private static double tasksDone;
    private static final double totalTasks = 17 + 8 + 5 + 4 + 5 + 9 + 3 + 16;

    SpriteContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.spriteContainer = new HashMap<>();

        // Menu images and controls (17)
        Image menuCharacter = Container.getImage(ImageID.MENU_CHARACTER);
        addSprite(SpriteID.MENU_CHARACTER_ALL,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_MEI_1,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ALYSSA_1,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ZYPHER_1,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {3, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_CHARLOTTE_1,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {4, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_MEI_2,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {1, 1}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ALYSSA_2,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {2, 1}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ZYPHER_2,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {3, 1}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_CHARLOTTE_2,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {4, 1}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_MEI_3,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {1, 2}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ALYSSA_3,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {2, 2}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ZYPHER_3,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {3, 2}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_CHARLOTTE_3,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {4, 2}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_MEI_4,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {1, 3}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ALYSSA_4,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {2, 3}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_ZYPHER_4,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {3, 3}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_CHARLOTTE_4,
                new Sprite(menuCharacter, new Dimension(442, 378), new int[] {4, 3}, new double[] {1, 1}, 1.0));

        Image menuCharacterSelector = Container.getImage(ImageID.MENU_CHARACTER_SELECTOR);
        addSprite(SpriteID.MENU_CHARACTER_SELECTOR_1,
                new Sprite(menuCharacterSelector, new Dimension(22, 33), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_SELECTOR_2,
                new Sprite(menuCharacterSelector, new Dimension(22, 33), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_SELECTOR_3,
                new Sprite(menuCharacterSelector, new Dimension(22, 33), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHARACTER_SELECTOR_4,
                new Sprite(menuCharacterSelector, new Dimension(22, 33), new int[] {3, 0}, new double[] {1, 1}, 1.0));

        Image menuCheckbox = Container.getImage(ImageID.MENU_CHECKBOX);
        addSprite(SpriteID.MENU_CHECKBOX_UNSELECTED_OFF,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHECKBOX_UNSELECTED_ON,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHECKBOX_SELECTED_OFF,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        addSprite(SpriteID.MENU_CHECKBOX_SELECTED_ON,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {3, 0}, new double[] {1, 1}, 1.0));

        // Game background and frame (5)

        addSprite(SpriteID.GAME_BACKGROUND_1, new Sprite(Container.getImage(ImageID.GAME_BACKGROUND_1)));

        addSprite(SpriteID.GAME_FRAME_NORTH_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_NORTH_1)));
        addSprite(SpriteID.GAME_FRAME_WEST_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_WEST_1)));
        addSprite(SpriteID.GAME_FRAME_EAST_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_EAST_1)));

        Image frameSouthSheet = Container.getImage(ImageID.GAME_FRAME_SOUTH_1);
        addSprite(SpriteID.GAME_FRAME_SOUTH_1,
                new AnimatedSprite(frameSouthSheet, new Dimension(402, 54), 4, 0.1, new int[] {0, 0}, new double[] {1, 1}));

        // HUD (4)
        Image hudSheet = Container.getImage(ImageID.HUD_BOX_SHEET);

        addSprite(SpriteID.HUD_BOX,
                new Sprite(hudSheet, new Dimension(134, 178), new double[] {1, 1}, new double[] {0, 0}, 1.0));
        addSprite(SpriteID.HUD_HEALTHBAR_FILLED,
                new Sprite(hudSheet, new Dimension(122, 14), new double[] {137, 1}, new double[] {0, 0}, 1.0));
        addSprite(SpriteID.HUD_STATUSBAR_FILLED,
                new Sprite(hudSheet, new Dimension(88, 14), new double[] {137, 17}, new double[] {0, 0}, 1.0));
        addSprite(SpriteID.HUD_STATUSBAR_PLUS,
                new Sprite(hudSheet, new Dimension(8, 14), new double[] {226, 17}, new double[] {0, 0}, 1.0));

        // Bombs (5)

        Image bombBlastSpriteSheet = Container.getImage(ImageID.BOMBS_EXPLOSIONS);
        addSprite(SpriteID.BOMB_ALYSSA,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 0}, new double[] {1, 1}));
        addSprite(SpriteID.BOMB_CHARLOTTE,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 1}, new double[] {1, 1}));
        addSprite(SpriteID.BOMB_ZYPHER,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 2}, new double[] {1, 1}));
        addSprite(SpriteID.BOMB_MEI,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 3}, new double[] {1, 1}));
        addSprite(SpriteID.MINE,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 4}, new double[] {1, 1}));

        // Blasts (9)

        addSprite(SpriteID.BLAST_CENTER,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 4}, new double[] {1, 1}));

        addSprite(SpriteID.BLAST_BEAM_WEST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 0}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_BEAM_NORTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 1}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_BEAM_EAST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 2}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_BEAM_SOUTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 3}, new double[] {1, 1}));

        addSprite(SpriteID.BLAST_END_WEST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 0}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_END_NORTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 1}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_END_EAST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 2}, new double[] {1, 1}));
        addSprite(SpriteID.BLAST_END_SOUTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 3}, new double[] {1, 1}));

        // Walls (3)

        Image wallSpriteSheet = Container.getImage(ImageID.WALLS);

        addSprite(SpriteID.WALL_DESTR_PARASOL,
                new Sprite(wallSpriteSheet, new Dimension(18, 18), new int[] {0, 1}, new double[] {1, 1}));
        addSprite(SpriteID.WALL_DESTR_PARASOL_DESTROYED,
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 1}, new double[] {1, 1}));
        addSprite(SpriteID.WALL_INDESTR_BEACHSTONE,
                new Sprite(wallSpriteSheet, new Dimension(18, 18), new int[] {4, 2}, new double[] {1, 1}));

        // Powerups (16)

        Image powerupSpritesheet = Container.getImage(ImageID.POWERUPS);

        addSprite(SpriteID.POWERUP_HEALTH,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 0}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_BOMBCOUNT,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 1}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_RANGE,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 2}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_SPEED,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 3}, new double[] {1, 1}));

        addSprite(SpriteID.POWERUP_HEALTH_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 0}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_BOMBCOUNT_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 1}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_RANGE_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 2}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_SPEED_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 3}, new double[] {1, 1}));

        addSprite(SpriteID.POWERUP_HEALTH_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 4}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_BOMBCOUNT_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 5}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_RANGE_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 6}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_SPEED_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 7}, new double[] {1, 1}));

        addSprite(SpriteID.POWERUP_HEALTH_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 4}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_BOMBCOUNT_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 5}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_RANGE_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 6}, new double[] {1, 1}));
        addSprite(SpriteID.POWERUP_SPEED_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 7}, new double[] {1, 1}));

    }

    private void addSprite(SpriteID spriteID, Sprite sprite) {
        this.spriteContainer.put(spriteID, sprite);
        tasksDone++;
    }

    Sprite getSprite(SpriteID spriteID) {
        return this.spriteContainer.get(spriteID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
