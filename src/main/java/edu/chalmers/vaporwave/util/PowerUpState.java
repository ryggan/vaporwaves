package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.view.Sprite;

public enum PowerUpState {
    HEALTH, RANGE, SPEED, BOMB_COUNT;

    public static int getSpawnChance(PowerUpState powerUpState) {
        if(powerUpState.equals(PowerUpState.HEALTH)) {
            return 1;
        } else if(powerUpState.equals(PowerUpState.BOMB_COUNT)) {
            return 1;
        } else if(powerUpState.equals(PowerUpState.RANGE)) {
            return 1;
        } else if(powerUpState.equals(PowerUpState.SPEED)) {
            return 1;
        }
        return 0;
    }
}
