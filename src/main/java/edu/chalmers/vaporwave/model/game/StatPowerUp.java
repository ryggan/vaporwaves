package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.List;
import java.util.Random;

public class StatPowerUp extends PowerUp {

    public StatPowerUp(List<PowerUpType> enabledPowerUpList) {

        if(enabledPowerUpList.size() > 0) {
            int randomMaxValue = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                randomMaxValue += PowerUpType.getSpawnChance(enabledPowerUpList.get(i));
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 1;
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
