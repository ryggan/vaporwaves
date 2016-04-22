package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;

public class StatPowerUp extends PowerUp {

    int spawnChance;
    int statusEffect;
    PowerUpState powerUpState;

    public StatPowerUp(int spawnChance, int statusEffect, PowerUpState powerUpState) {
        this.spawnChance = spawnChance;
        this.statusEffect = statusEffect;
        this.powerUpState = powerUpState;
    }

    public int getSpawnChance() {
        return spawnChance;
    }

    public int getStatusEffect() {
        return statusEffect;
    }

    public PowerUpState getPowerType() {
        return powerUpState;
    }
}
