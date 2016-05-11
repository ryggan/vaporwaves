package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.PowerUpType;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SpriteContainer {

    private static SpriteContainer instance;

    private Map<SpriteID, Sprite> spriteContainer;

    private static double tasksDone = 0;
    private static double totalTasks = 5 + 5 + 9 + 3 + 16;

    private SpriteContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.spriteContainer = new HashMap<>();

        // Game background and frame (5)

        addSprite(SpriteID.GAME_BACKGROUND_1, new Sprite(ImageContainer.getInstance().getImage(ImageID.GAME_BACKGROUND_1)));

        addSprite(SpriteID.GAME_FRAME_NORTH_1, new Sprite(ImageContainer.getInstance().getImage(ImageID.GAME_FRAME_NORTH_1)));
        addSprite(SpriteID.GAME_FRAME_WEST_1, new Sprite(ImageContainer.getInstance().getImage(ImageID.GAME_FRAME_WEST_1)));
        addSprite(SpriteID.GAME_FRAME_EAST_1, new Sprite(ImageContainer.getInstance().getImage(ImageID.GAME_FRAME_EAST_1)));

        Image frameSouthSheet = ImageContainer.getInstance().getImage(ImageID.GAME_FRAME_SOUTH_1);
        AnimatedSprite frameSouthSprite =
                new AnimatedSprite(frameSouthSheet, new Dimension(402, 54), 4, 0.1, new int[] {0, 0}, new double[] {1, 1});
        addSprite(SpriteID.GAME_FRAME_SOUTH_1, frameSouthSprite);

        // Bombs (5)

        Image bombBlastSpriteSheet = ImageContainer.getInstance().getImage(ImageID.BOMBS_EXPLOSIONS);
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

        Image wallSpriteSheet = ImageContainer.getInstance().getImage(ImageID.WALLS);

        addSprite(SpriteID.WALL_DESTR_PARASOL,
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 1, 1.0, new int[] {0, 1}, new double[] {1, 1}));
        addSprite(SpriteID.WALL_DESTR_PARASOL_DESTROYED,
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 1}, new double[] {1, 1}));
        addSprite(SpriteID.WALL_INDESTR_BEACHSTONE,
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 1, 1.0, new int[] {4, 2}, new double[] {1, 1}));

        // Powerups (16)

        Image powerupSpritesheet = ImageContainer.getInstance().getImage(ImageID.POWERUPS);

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

    public Sprite getSprite(SpriteID spriteID) {
        return this.spriteContainer.get(spriteID);
    }

    public static SpriteContainer getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new SpriteContainer();
        }
    }

    public static double getTasksDone() {
        return tasksDone;
    }

    public static double getTotalTasks() {
        return totalTasks;
    }

}
