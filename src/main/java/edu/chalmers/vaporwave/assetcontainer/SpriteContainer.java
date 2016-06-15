package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This container deals with all the sprites; both Sprite and AnimatedSprite.
 * This one also calculates it's total amount of tasks dynamically.
 */
class SpriteContainer {

    static class SpriteProperties {
        ImageID imageID;
        Dimension spriteDimension;
        int length;
        double duration;
        int[] sourceGridPosition;
        double[] sourceCanvasPosition;
        double[] offset;
        double scale;
        boolean animated;

        public SpriteProperties(ImageID imageID, Dimension spriteDimension, int length, double duration,
                                int[] sourceGridPosition, double[] sourceCanvasPosition, double[] offset,
                                double scale, boolean animated) {
            this.imageID = imageID;
            this.spriteDimension = spriteDimension;
            this.length = length;
            this.duration = duration;
            this.sourceGridPosition = sourceGridPosition;
            this.sourceCanvasPosition = sourceCanvasPosition;
            this.offset = offset;
            this.scale = scale;
            this.animated = animated;
        }
    }

    private static Map<SpriteID, Sprite> spriteContainer;

    private static int tasksDone;
    private static int totalTasks = 0;

    private static Set<Pair<SpriteID, SpriteProperties>> spriteSet = new HashSet<>();

    // First all images is prepared in a set, partly to be able to count how many tasks to be done.
    // Then the loading of the external files takes place, which is the part that takes time.
    static void prepare() throws Exception {
        spriteContainer = new HashMap<>();

        // Startmenu
        prepareSpriteLoad(SpriteID.MENU_STARTMENU_HELP, ImageID.MENU_STARTMENU_HELP, -1);
        prepareSpriteLoad(SpriteID.MENU_ALLCHARACTERS, ImageID.MENU_ALLCHARACTERS, -1);
        prepareSpriteLoad(SpriteID.MENU_TITLE, ImageID.MENU_TITLE, 1.0);

        // Roostermenu
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_HELP, ImageID.MENU_ROOSTER_HELP, 1.0);

        // Character Select
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_HELP, ImageID.MENU_CHARACTERSELECT_HELP, 1.0);

        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALL,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {0, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_1,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {1, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_1,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {2, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_1,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {3, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_1,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {4, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_2,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {1, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_2,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {2, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_2,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {3, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_2,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {4, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_3,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {1, 2}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_3,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {2, 2}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_3,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {3, 2}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_3,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {4, 2}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_MEI_4,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {1, 3}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ALYSSA_4,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {2, 3}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_ZYPHER_4,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {3, 3}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_CHARLOTTE_4,
                ImageID.MENU_CHARACTER, new Dimension(626,443), new int[] {4, 3}, new double[] {1, 1}, 1.0);

        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_1,
                ImageID.MENU_CHARACTER_SELECTOR, new Dimension(24, 27), new int[] {0, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_2,
                ImageID.MENU_CHARACTER_SELECTOR, new Dimension(24, 27), new int[] {1, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_3,
                ImageID.MENU_CHARACTER_SELECTOR, new Dimension(24, 27), new int[] {2, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTER_SELECTOR_4,
                ImageID.MENU_CHARACTER_SELECTOR, new Dimension(24, 27), new int[] {3, 0}, new double[] {1, 1}, 1.0);

        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_UNSELECTED_OFF,
                ImageID.MENU_CHECKBOX, new Dimension(111, 118), new int[] {0, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_UNSELECTED_ON,
                ImageID.MENU_CHECKBOX, new Dimension(111, 118), new int[] {1, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_SELECTED_OFF,
                ImageID.MENU_CHECKBOX, new Dimension(111, 118), new int[] {2, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHECKBOX_SELECTED_ON,
                ImageID.MENU_CHECKBOX, new Dimension(111, 118), new int[] {3, 0}, new double[] {1, 1}, 1.0);

        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_OPEN_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {0, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P1_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {1, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P2_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {2, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P3_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {3, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P4_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {4, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_CPU_OFF,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {5, 0}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_OPEN_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {0, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P1_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {1, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P2_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {2, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P3_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {3, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_P4_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {4, 1}, new double[] {1, 1}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_ROOSTER_SELECT_CPU_ON,
                ImageID.MENU_ROOSTER_SELECT, new Dimension(133, 94), new int[] {5, 1}, new double[] {1, 1}, 1.0);

        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_ALYSSA, ImageID.MENU_CHARACTERSELECT_ALYSSA, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_MEI, ImageID.MENU_CHARACTERSELECT_MEI, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_CHARLOTTE, ImageID.MENU_CHARACTERSELECT_CHARLOTTE, 1.0);
        prepareSpriteLoad(SpriteID.MENU_CHARACTERSELECT_ZYPHER, ImageID.MENU_CHARACTERSELECT_ZYPHER, 1.0);

        prepareSpriteLoad(SpriteID.MENU_SIGN_ALYSSA, ImageID.MENU_SIGN_ALYSSA, 1.0);
        prepareSpriteLoad(SpriteID.MENU_SIGN_MEI, ImageID.MENU_SIGN_MEI, 1.0);
        prepareSpriteLoad(SpriteID.MENU_SIGN_CHARLOTTE, ImageID.MENU_SIGN_CHARLOTTE, 1.0);
        prepareSpriteLoad(SpriteID.MENU_SIGN_ZYPHER, ImageID.MENU_SIGN_ZYPHER, 1.0);

        // Map select
        prepareSpriteLoad(SpriteID.MENU_MAPSELECT_MARK,
                ImageID.MENU_MAPSELECT_SHEET, new Dimension(121, 91), new double[] {1, 1}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_MAPSELECT_INDESTRUCTIBLE,
                ImageID.MENU_MAPSELECT_SHEET, new Dimension(5, 5), new double[] {124, 1}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_MAPSELECT_DESTRUCTIBLE,
                ImageID.MENU_MAPSELECT_SHEET, new Dimension(5, 5), new double[] {131, 1}, new double[] {0, 0}, 1.0);

        prepareSpriteLoad(SpriteID.MENU_MAPSELECT_ARROW_RIGHT,
                ImageID.MENU_MAPSELECT_SHEET, new Dimension(16, 28), new double[] {124, 8}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.MENU_MAPSELECT_ARROW_LEFT,
                ImageID.MENU_MAPSELECT_SHEET, new Dimension(16, 28), new double[] {124, 38}, new double[] {0, 0}, 1.0);

        // Results Menu
        prepareSpriteLoad(SpriteID.MENU_RESULTS_ZYPHER, ImageID.MENU_RESULTS_ZYPHER, 1.0);
        prepareSpriteLoad(SpriteID.MENU_RESULTS_CHARLOTTE, ImageID.MENU_RESULTS_CHARLOTTE, 1.0);
        prepareSpriteLoad(SpriteID.MENU_RESULTS_MEI, ImageID.MENU_RESULTS_MEI, 1.0);
        prepareSpriteLoad(SpriteID.MENU_RESULTS_ALYSSA, ImageID.MENU_RESULTS_ALYSSA, 1.0);

        // Game background and frame (5)
        prepareSpriteLoad(SpriteID.GAME_BACKGROUND_1, ImageID.GAME_BACKGROUND_1, -1);

        prepareSpriteLoad(SpriteID.GAME_FRAME_NORTH_1, ImageID.GAME_FRAME_NORTH_1, -1);
        prepareSpriteLoad(SpriteID.GAME_FRAME_WEST_1, ImageID.GAME_FRAME_WEST_1, -1);
        prepareSpriteLoad(SpriteID.GAME_FRAME_EAST_1, ImageID.GAME_FRAME_EAST_1, -1);

        prepareSpriteLoad(SpriteID.GAME_FRAME_SOUTH_1,
                ImageID.GAME_FRAME_SOUTH_1, new Dimension(402, 54), 4, 0.1, new int[] {0, 0}, new double[] {1, 1}, -1);

        // HUD (8)
        prepareSpriteLoad(SpriteID.MENU_PAUSE, ImageID.MENU_PAUSE, 1.0);
        prepareSpriteLoad(SpriteID.HUD_BOX,
                ImageID.HUD_BOX_SHEET, new Dimension(134, 178), new double[] {1, 1}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.HUD_HEALTHBAR_FILLED,
                ImageID.HUD_BOX_SHEET, new Dimension(122, 14), new double[] {137, 1}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.HUD_STATUSBAR_FILLED,
                ImageID.HUD_BOX_SHEET, new Dimension(88, 14), new double[] {137, 17}, new double[] {0, 0}, 1.0);
        prepareSpriteLoad(SpriteID.HUD_STATUSBAR_PLUS,
                ImageID.HUD_BOX_SHEET, new Dimension(8, 14), new double[] {226, 17}, new double[] {0, 0}, 1.0);

        prepareSpriteLoad(SpriteID.HUD_TIMER_MESSAGE, ImageID.HUD_TIMER_MESSAGE, 1.0);
        prepareSpriteLoad(SpriteID.HUD_GAMEOVER_MESSAGE, ImageID.HUD_GAMEOVER_MESSAGE, 1.0);

        prepareSpriteLoad(SpriteID.SCOREBOARD_BACKGROUND, ImageID.SCOREBOARD_BACK, 1.0);

        // Bombs (5)
        prepareSpriteLoad(SpriteID.BOMB_ALYSSA,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 2, 0.4, new int[] {0, 0}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BOMB_CHARLOTTE,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 2, 0.4, new int[] {0, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BOMB_ZYPHER,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 2, 0.4, new int[] {0, 2}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BOMB_MEI,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 2, 0.4, new int[] {0, 3}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.MINE,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 2, 0.4, new int[] {0, 4}, new double[] {1, 1}, -1);

        // Blasts (9)
        prepareSpriteLoad(SpriteID.BLAST_CENTER,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {2, 4}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.BLAST_BEAM_WEST,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {9, 0}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_BEAM_NORTH,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {9, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_BEAM_EAST,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {9, 2}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_BEAM_SOUTH,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {9, 3}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.BLAST_END_WEST,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {2, 0}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_END_NORTH,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {2, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_END_EAST,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {2, 2}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.BLAST_END_SOUTH,
                ImageID.BOMBS_EXPLOSIONS, new Dimension(18, 18), 7, 0.1, new int[] {2, 3}, new double[] {1, 1}, -1);

        // Walls (3)
        prepareSpriteLoad(SpriteID.WALL_DESTR_PARASOL,
                ImageID.WALLS, new Dimension(18, 18), new int[] {0, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.WALL_DESTR_PARASOL_DESTROYED,
                ImageID.WALLS, new Dimension(18, 18), 7, 0.1, new int[] {1, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.WALL_INDESTR_BEACHSTONE,
                ImageID.WALLS, new Dimension(18, 18), new int[] {4, 2}, new double[] {1, 1}, -1);

        // Powerups (16)
        prepareSpriteLoad(SpriteID.POWERUP_HEALTH,
                ImageID.POWERUPS, new Dimension(18, 18), 8, 0.1, new int[] {0, 0}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT,
                ImageID.POWERUPS, new Dimension(18, 18), 8, 0.1, new int[] {0, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_RANGE,
                ImageID.POWERUPS, new Dimension(18, 18), 8, 0.1, new int[] {0, 2}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_SPEED,
                ImageID.POWERUPS, new Dimension(18, 18), 8, 0.1, new int[] {0, 3}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_SPAWN,
                ImageID.POWERUPS, new Dimension(18, 18), 11, 0.1, new int[] {8, 0}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_SPAWN,
                ImageID.POWERUPS, new Dimension(18, 18), 11, 0.1, new int[] {8, 1}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_SPAWN,
                ImageID.POWERUPS, new Dimension(18, 18), 11, 0.1, new int[] {8, 2}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_SPAWN,
                ImageID.POWERUPS, new Dimension(18, 18), 11, 0.1, new int[] {8, 3}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_PICKUP,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {0, 4}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_PICKUP,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {0, 5}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_PICKUP,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {0, 6}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_PICKUP,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {0, 7}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.POWERUP_HEALTH_DESTROY,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {9, 4}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_BOMBCOUNT_DESTROY,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {9, 5}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_RANGE_DESTROY,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {9, 6}, new double[] {1, 1}, -1);
        prepareSpriteLoad(SpriteID.POWERUP_SPEED_DESTROY,
                ImageID.POWERUPS, new Dimension(18, 18), 9, 0.1, new int[] {9, 7}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.FISH,
                ImageID.FISH, new Dimension(26, 16), 22, 0.1, new int[] {0, 0}, new double[] {1, 1}, -1);

        prepareSpriteLoad(SpriteID.POWERUP_SPARKLES,
                ImageID.CHARACTER_MISC, new Dimension(48, 48), 9, 0.08, new int[] {0, 0}, new double[] {16, 27}, -1);


    }

    static void init() {
        addSprites();
    }

    private static void prepareSpriteLoad(SpriteID spriteID, ImageID imageID, double scale) {
        spriteSet.add(new Pair<>(spriteID, new SpriteProperties(imageID, null, -1, -1, null, null, null, scale, false)));
        totalTasks += 1;
    }

    private static void prepareSpriteLoad(SpriteID spriteID, ImageID imageID, Dimension dimension,
                                          int[] sourceGridPosition, double[] offset, double scale) {
        spriteSet.add(new Pair<>(spriteID, new SpriteProperties(imageID, dimension, -1, -1, sourceGridPosition, null, offset,
                scale, false)));
        totalTasks += 1;
    }

    private static void prepareSpriteLoad(SpriteID spriteID, ImageID imageID, Dimension dimension,
                                          double[] sourceCanvasPosition, double[] offset, double scale) {
        spriteSet.add(new Pair<>(spriteID, new SpriteProperties(imageID, dimension, -1, -1, null, sourceCanvasPosition, offset,
                scale, false)));
        totalTasks += 1;
    }

    private static void prepareSpriteLoad(SpriteID spriteID, ImageID imageID, Dimension spriteDimension, int length,
                                          double duration, int[] startPosition, double[] offset, double scale) {
        spriteSet.add(new Pair<>(spriteID, new SpriteProperties(imageID, spriteDimension, length, duration, startPosition,
                    null, offset, scale, true)));
        totalTasks += 1;
    }

    private static void addSprites() {
        for (Pair<SpriteID, SpriteProperties> pair : spriteSet) {
            SpriteProperties prop = pair.getSecond();

            double actualScale = prop.scale;
            if (actualScale < 0) {
                actualScale = Constants.GAME_SCALE;
            }

            if (prop.animated) {
                addSprite(pair.getFirst(), new AnimatedSprite(Container.getImage(prop.imageID), prop.spriteDimension,
                        prop.length, prop.duration, prop.sourceGridPosition, prop.offset, actualScale));

            } else {
                if (prop.spriteDimension != null) {

                    if (prop.sourceGridPosition != null) {
                        addSprite(pair.getFirst(), new Sprite(Container.getImage(prop.imageID), prop.spriteDimension,
                                prop.sourceGridPosition, prop.offset, actualScale));

                    } else if (prop.sourceCanvasPosition != null) {
                        addSprite(pair.getFirst(), new Sprite(Container.getImage(prop.imageID), prop.spriteDimension,
                                prop.sourceCanvasPosition, prop.offset, actualScale));
                    }

                } else {
                    addSprite(pair.getFirst(), new Sprite(Container.getImage(prop.imageID), actualScale));
                }
            }
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
