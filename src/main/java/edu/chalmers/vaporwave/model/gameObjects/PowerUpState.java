package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public interface PowerUpState {
    public int getRandomValue();
    public int getEffect();
    public PowerUpStates getPowerType();
}
