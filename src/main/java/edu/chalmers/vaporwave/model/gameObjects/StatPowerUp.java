package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.List;
import java.util.Random;

public class StatPowerUp extends StaticTile {
    private PowerUpState powerUpState;

    //Probably remove this later
    public StatPowerUp() {}

    public PowerUpState getPowerUpState() {
        return this.powerUpState;
    }

    public StatPowerUp(List<PowerUpState> enabledPowerUpList) {

        if(enabledPowerUpList.size() > 0) {
            int randomMaxValue = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                randomMaxValue = randomMaxValue + PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                 j = j + PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
                j = j + PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
                if(j <= randomValue) {
                    powerUpState = enabledPowerUpList.get(i);
                }
            }
        }
    }
}
