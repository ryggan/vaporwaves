package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class SpeedPowerUp implements PowerUpState {
    private int spawnChance = 2;

    public int getSpawnChance() {
        return this.spawnChance;
    }

    public int getStatusEffect() {
        //Not sure what value here
        return 10;
    }
    public PowerUpStates getPowerType() {
        return PowerUpStates.SPEED;
    }
}
