package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This container deals with all the sprites; both Sprite and AnimatedSprite.
 */
class SpriteContainer {

    private static Map<SpriteID, Sprite> spriteContainer;

    private static int tasksDone;
    private static int totalTasks = 0;

    private static Set<Pair<SpriteID, Sprite>> spriteSet = new HashSet<>();

    public static void initSpriteContainer() throws Exception {
        spriteContainer = new HashMap<>();

        // Menu images and controls
        Image menuCharacter = Container.getImage(ImageID.MENU_CHARACTER);

        //Results Menu
        prepareSpriteLoad(SpriteID.MENU_RESULTS_ZYPHER, new Sprite(Container.getImage(ImageID.MENU_RESULTS_ZYPHER), 1.0));
        prepareSpriteLoad(SpriteID.MENU_RESULTS_CHARLOTTE, new Sprite(Container.getImage(ImageID.MENU_RESULTS_CHARLOTTE), 1.0));
        prepareSpriteLoad(SpriteID.MENU_RESULTS_MEI, new Sprite(Container.getImage(ImageID.MENU_RESULTS_MEI), 1.0));
        prepareSpriteLoad(SpriteID.MENU_RESULTS_ALYSSA, new Sprite(Container.getImage(ImageID.MENU_RESULTS_ALYSSA), 1.0));

        //Character Select
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_HELP, new Sprite((Container.getImage(ImageID.MENU_CHARACTERSELECT_HELP)), 1.0));

        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALL,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_1,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_1,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_1,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {3, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_1,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {4, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_2,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {1, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_2,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {2, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_2,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {3, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_2,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {4, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_3,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {1, 2}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_3,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {2, 2}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_3,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {3, 2}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_3,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {4, 2}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_4,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {1, 3}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_4,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {2, 3}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_4,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {3, 3}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_4,
                new Sprite(menuCharacter, new Dimension(626,443), new int[] {4, 3}, new double[] {1, 1}, 1.0));

        Image menuCharacterSelector = Container.getImage(ImageID.MENU_CHARACTER_SELECTOR);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_1,
                new Sprite(menuCharacterSelector, new Dimension(24, 27), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_2,
                new Sprite(menuCharacterSelector, new Dimension(24, 27), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_3,
                new Sprite(menuCharacterSelector, new Dimension(24, 27), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_4,
                new Sprite(menuCharacterSelector, new Dimension(24, 27), new int[] {3, 0}, new double[] {1, 1}, 1.0));

        Image menuCheckbox = Container.getImage(ImageID.MENU_CHECKBOX);
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_UNSELECTED_OFF,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_UNSELECTED_ON,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_SELECTED_OFF,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_SELECTED_ON,
                new Sprite(menuCheckbox, new Dimension(111, 118), new int[] {3, 0}, new double[] {1, 1}, 1.0));

        Image menuRoosterSelect = Container.getImage(ImageID.MENU_ROOSTER_SELECT);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_OPEN_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {0, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P1_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {1, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P2_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {2, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P3_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {3, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P4_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {4, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_CPU_OFF,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {5, 0}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_OPEN_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {0, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P1_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {1, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P2_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {2, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P3_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {3, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P4_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {4, 1}, new double[] {1, 1}, 1.0));
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_CPU_ON,
                new Sprite(menuRoosterSelect, new Dimension(133, 94), new int[] {5, 1}, new double[] {1, 1}, 1.0));

        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_ALYSSA, new Sprite(Container.getImage(ImageID.MENU_CHARACTERSELECT_ALYSSA)));
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_MEI, new Sprite(Container.getImage(ImageID.MENU_CHARACTERSELECT_MEI)));
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_CHARLOTTE, new Sprite(Container.getImage(ImageID.MENU_CHARACTERSELECT_CHARLOTTE)));
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_ZYPHER, new Sprite(Container.getImage(ImageID.MENU_CHARACTERSELECT_ZYPHER)));

        //Roostermenu
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_HELP, new Sprite(Container.getImage(ImageID.MENU_ROOSTER_HELP)));

        //Startmenu
        prepareSpriteLoad(SpriteID.MENU_STARTMENU_HELP, new Sprite(Container.getImage(ImageID.MENU_STARTMENU_HELP)));
        prepareSpriteLoad(SpriteID.MENU_ALLCHARACTERS, new Sprite(Container.getImage(ImageID.MENU_ALLCHARACTERS)));
        prepareSpriteLoad(SpriteID.MENU_TITLE, new Sprite(Container.getImage(ImageID.MENU_TITLE)));

        // Game background and frame (5)
        prepareSpriteLoad(SpriteID.GAME_BACKGROUND_1, new Sprite(Container.getImage(ImageID.GAME_BACKGROUND_1)));

        prepareSpriteLoad(SpriteID.GAME_FRAME_NORTH_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_NORTH_1)));
        prepareSpriteLoad(SpriteID.GAME_FRAME_WEST_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_WEST_1)));
        prepareSpriteLoad(SpriteID.GAME_FRAME_EAST_1, new Sprite(Container.getImage(ImageID.GAME_FRAME_EAST_1)));

        Image frameSouthSheet = Container.getImage(ImageID.GAME_FRAME_SOUTH_1);
        prepareSpriteLoad(SpriteID.GAME_FRAME_SOUTH_1,
                new AnimatedSprite(frameSouthSheet, new Dimension(402, 54), 4, 0.1, new int[] {0, 0}, new double[] {1, 1}));

        // HUD (8)
        Image hudSheet = Container.getImage(ImageID.HUD_BOX_SHEET);

        prepareSpriteLoad(SpriteID.MENU_PAUSE, new Sprite(Container.getImage(ImageID.MENU_PAUSE)));
        prepareSpriteLoad(SpriteID.HUD_BOX,
                new Sprite(hudSheet, new Dimension(134, 178), new double[] {1, 1}, new double[] {0, 0}, 1.0));
        prepareSpriteLoad(SpriteID.HUD_HEALTHBAR_FILLED,
                new Sprite(hudSheet, new Dimension(122, 14), new double[] {137, 1}, new double[] {0, 0}, 1.0));
        prepareSpriteLoad(SpriteID.HUD_STATUSBAR_FILLED,
                new Sprite(hudSheet, new Dimension(88, 14), new double[] {137, 17}, new double[] {0, 0}, 1.0));
        prepareSpriteLoad(SpriteID.HUD_STATUSBAR_PLUS,
                new Sprite(hudSheet, new Dimension(8, 14), new double[] {226, 17}, new double[] {0, 0}, 1.0));

        prepareSpriteLoad(SpriteID.HUD_TIMER_MESSAGE, new Sprite(Container.getImage(ImageID.HUD_TIMER_MESSAGE), 1.0));
        prepareSpriteLoad(SpriteID.HUD_GAMEOVER_MESSAGE, new Sprite(Container.getImage(ImageID.HUD_GAMEOVER_MESSAGE), 1.0));

        prepareSpriteLoad(SpriteID.SCOREBOARD_BACKGROUND, new Sprite(Container.getImage(ImageID.SCOREBOARD_BACK), 1.0));

        // Bombs (5)
        Image bombBlastSpriteSheet = Container.getImage(ImageID.BOMBS_EXPLOSIONS);
        prepareSpriteLoad(SpriteID.BOMB_ALYSSA,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 0}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BOMB_CHARLOTTE,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BOMB_ZYPHER,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 2}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BOMB_MEI,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 3}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.MINE,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 4}, new double[] {1, 1}));

        // Blasts (9)
        prepareSpriteLoad(SpriteID.BLAST_CENTER,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 4}, new double[] {1, 1}));

        prepareSpriteLoad(SpriteID.BLAST_BEAM_WEST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 0}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_BEAM_NORTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_BEAM_EAST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 2}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_BEAM_SOUTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {9, 3}, new double[] {1, 1}));

        prepareSpriteLoad(SpriteID.BLAST_END_WEST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 0}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_END_NORTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_END_EAST,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 2}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.BLAST_END_SOUTH,
                new AnimatedSprite(bombBlastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 3}, new double[] {1, 1}));

        // Walls (3)
        Image wallSpriteSheet = Container.getImage(ImageID.WALLS);

        prepareSpriteLoad(SpriteID.WALL_DESTR_PARASOL,
                new Sprite(wallSpriteSheet, new Dimension(18, 18), new int[] {0, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.WALL_DESTR_PARASOL_DESTROYED,
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.WALL_INDESTR_BEACHSTONE,
                new Sprite(wallSpriteSheet, new Dimension(18, 18), new int[] {4, 2}, new double[] {1, 1}));

        // Powerups (16)
        Image powerupSpritesheet = Container.getImage(ImageID.POWERUPS);

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 0}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_RANGE,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 2}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_SPEED,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 3}, new double[] {1, 1}));

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 0}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 1}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 2}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_SPAWN,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 11, 0.1, new int[] {8, 3}, new double[] {1, 1}));

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 4}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 5}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 6}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_PICKUP,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {0, 7}, new double[] {1, 1}));

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 4}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 5}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 6}, new double[] {1, 1}));
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_DESTROY,
                new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 9, 0.1, new int[] {9, 7}, new double[] {1, 1}));

        Image fishSpritesheet = Container.getImage(ImageID.FISH);
        prepareSpriteLoad(SpriteID.FISH,
                new AnimatedSprite(fishSpritesheet, new Dimension(26, 16), 22, 0.1, new int[] {0, 0}, new double[] {1, 1}));

        addSprites();

    }

    private static void prepareSpriteLoad(SpriteID spriteID, Sprite sprite) {
        spriteSet.add(new Pair<>(spriteID, sprite));
        totalTasks += 1;
    }

    private static void addSprites() {
        for(Pair<SpriteID, Sprite> pair : spriteSet) {
            addSprite(pair.getFirst(), pair.getSecond());
        }
    }

    private static void addSprite(SpriteID spriteID, Sprite sprite) {
        spriteContainer.put(spriteID, sprite);
        tasksDone++;
    }

    static Sprite getSprite(SpriteID spriteID) {
        return spriteContainer.get(spriteID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
