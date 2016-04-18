package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class BombCountPowerUp implements PowerUpState {
        private int spawnChance = 1;

        public int getSpawnChance() {
            return this.spawnChance;
        }

        public int getStatusEffect() {
            return 1;
        }
        public PowerUpStates getPowerType() {
            return PowerUpStates.BOMB_COUNT;
        }
}
