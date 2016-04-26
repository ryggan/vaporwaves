package edu.chalmers.vaporwave.util;

public class Constants {

    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 720;

    public static final int GAME_WIDTH = 672;
    public static final int GAME_HEIGHT = 480;

    public static final int DEFAULT_GRID_WIDTH = 21;
    public static final int DEFAULT_GRID_HEIGHT = 15;

    public static final int DEFAULT_TILE_WIDTH = 16;
    public static final int DEFAULT_TILE_HEIGHT = 16;

    public static final double GAME_SCALE = 2.0;

    public static final MovableState[] CHARACTER_CHARACTER_STATE = { MovableState.WALK, MovableState.IDLE, MovableState.FLINCH, MovableState.DEATH, MovableState.SPAWN };
    public static final PowerUpState[] POWERUP_STATE = { PowerUpState.BOMB_COUNT, PowerUpState.HEALTH, PowerUpState.RANGE, PowerUpState.SPEED };

    public static final String GAME_CHARACTER_XML_FILE = "src/main/resources/configuration/gameCharacters.xml";

    public static final String DEFAULT_MAP_FILE = "src/main/resources/maps/default.vapormap";

    public static final int GRID_OFFSET_X = 0;
    public static final int GRID_OFFSET_Y = 62;
}
