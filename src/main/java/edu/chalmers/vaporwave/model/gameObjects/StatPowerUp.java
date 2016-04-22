package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.List;
import java.util.Random;

public class StatPowerUp extends PowerUp {
    private PowerUpState powerUpState;

    public StatPowerUp(List<PowerUp> enabledPowerUpList) {

        if(enabledPowerUpList.size() > 0) {
            int randomMaxValue = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                randomMaxValue = randomMaxValue + enabledPowerUpList.get(i).getSpawnChance();
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                j = j + enabledPowerUpList.get(i).getSpawnChance();
                if(j <= randomValue) {
//                    powerUpState = enabledPowerUpList.get(i);
                }
            }
        }
    }
}
