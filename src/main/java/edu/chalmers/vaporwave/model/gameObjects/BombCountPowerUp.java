package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class BombCountPowerUp extends PowerUpState {
    public BombCountPowerUp(){
        super(1,1,PowerUpStates.BOMB_COUNT);
    }
}
