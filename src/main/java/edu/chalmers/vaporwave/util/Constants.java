package edu.chalmers.vaporwave.util;

/**
 * Class to keep global constants in one place.
 */
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

    public static final String GAME_CHARACTER_XML_FILE = "src/main/resources/configuration/gameCharacters.xml";
    public static final String FONT_FILE_BAUHAUS = "src/main/resources/fonts/bauhaus-93.ttf";

    public static final String DEFAULT_MAP_FILE = "src/main/resources/maps/bobsmap.vapormap";

    public static final int GRID_OFFSET_X = 0;
    public static final int GRID_OFFSET_Y = 45;

    public static final double DEFAULT_BOMB_DELAY = 2.0;
    public static final int DEFAULT_BOMB_DAMAGE = 30;

    public static final double DEFAULT_POWERUP_SPAWN_CHANCE = 0.5;
    public static final double DEFAULT_POWERUP_SPEED_GAIN = 0.2;

    public static final double DEFAULT_START_HEALTH = 50.0;

    public static final int FRAMES_HELD_KEYS_UPDATE = 40;

    public static final int MAX_NUMBER_OF_PLAYERS = 4;

    // Sets how often the enemy will check for new direction from its AI-system.
    public static final int ENEMY_UPDATE_RATE = 15;

    public static final String COLORNO_VAPEPINK = "#fd2881";
    public static final String COLORNO_MAPGREY = "#808080";

    public static final int SCORE_KILL_CHARACTER = 500;
    public static final int SCORE_KILL_ENEMY = 100;
    public static final int SCORE_POWERUP = 50;
    public static final int SCORE_DAMAGE_CHARACTER = 20;
    public static final int SCORE_DAMAGE_ENEMY = 10;
    public static final int SCORE_DEATH_PENALTY = -200;
}

