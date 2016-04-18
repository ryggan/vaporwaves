package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class SpeedPowerUp implements PowerUpState {
    private int randomValue = 2;

    public int getRandomValue() {
        return this.randomValue;
    }

    public int getEffect() {
        //Not sure what value here
        return 10;
    }
    public PowerUpStates getPowerType() {
        return PowerUpStates.SPEED;
    }
}
