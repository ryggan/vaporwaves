package edu.chalmers.vaporwave.util;

/**
 * Enum for every powerup type, and their spawnchances.
 */
public enum PowerUpType {
    HEALTH, RANGE, SPEED, BOMB_COUNT, FISH;

    public static double getSpawnChance(PowerUpType powerUpState) {
        switch (powerUpState) {
            case HEALTH:
                return 1.0;
            case BOMB_COUNT:
                return 1.0;
            case RANGE:
                return 1.0;
            case SPEED:
                return 1.0;
            case FISH:
                return 1.0;
            default:
                return 0.0;
        }
    }
}
