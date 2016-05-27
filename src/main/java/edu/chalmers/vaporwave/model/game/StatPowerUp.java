package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.List;
import java.util.Random;

public class StatPowerUp extends PowerUp {

    public StatPowerUp(List<PowerUpType> enabledPowerUpList) {

        if(enabledPowerUpList.size() > 0) {
            int maxValue = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                maxValue += PowerUpType.getSpawnChance(enabledPowerUpList.get(i));
            }

            System.out.println("maxValue: " + maxValue  );

            Random random = new Random();
            int randomValue = random.nextInt(maxValue);
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                setPowerUpType(enabledPowerUpList.get(randomValue));
            }
        }
    }

    public StatPowerUp(PowerUpType powerUpType) {
        setPowerUpType(powerUpType);
    }

    public String toString() {
        return super.toString() + ": StatPowerUp";
    }
}
