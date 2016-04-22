package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;

public class HealthPowerUp extends PowerUp {
    public HealthPowerUp(){
        super(1,1, PowerUpState.HEALTH);
    }
}
