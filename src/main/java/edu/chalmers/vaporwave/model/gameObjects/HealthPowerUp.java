package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class HealthPowerUp extends PowerUpState {
    public HealthPowerUp(){
        super(1,1,PowerUpStates.HEALTH);
    }
}
