package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class BombCountPowerUp implements PowerUpState {
        private int randomValue = 1;

        public int getRandomValue() {
            return this.randomValue;
        }

        public int getEffect() {
            return 1;
        }
        public PowerUpStates getPowerType() {
            return PowerUpStates.BOMB_COUNT;
        }
}
