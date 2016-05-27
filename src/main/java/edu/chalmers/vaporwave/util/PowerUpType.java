package edu.chalmers.vaporwave.util;

public enum PowerUpType {
    HEALTH, RANGE, SPEED, BOMB_COUNT, FISH;

    public static int getSpawnChance(PowerUpType powerUpState) {
        if(powerUpState.equals(PowerUpType.HEALTH)) {
            return 1;
        } else if(powerUpState.equals(PowerUpType.BOMB_COUNT)) {
            return 1;
        } else if(powerUpState.equals(PowerUpType.RANGE)) {
            return 1;
        } else if(powerUpState.equals(PowerUpType.SPEED)) {
            return 1;
        } else if(powerUpState.equals(PowerUpType.FISH)) {
            return 1;
        }
        return 0;
    }
}
