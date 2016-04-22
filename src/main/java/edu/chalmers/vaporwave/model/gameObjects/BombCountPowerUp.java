package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class BombCountPowerUp extends PowerUp {
    public BombCountPowerUp(){
        super(1,1, PowerUpState.BOMB_COUNT);
    }
}
