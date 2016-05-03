package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.List;
import java.util.Random;

public class StatPowerUp extends PowerUp {
//    private PowerUpType powerUpType;

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
//                j += PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
//                System.out.println(enabledPowerUpList.size());
//                System.out.println(randomMaxValue);
//                if(j <= randomValue) {
//                powerUpType = enabledPowerUpList.get(randomValue);
                setPowerUpType(enabledPowerUpList.get(randomValue));
//                    System.out.println(powerUpState);
//                }
            }
        }
    }

//    public PowerUpType getPowerUpType() {
//        return this.powerUpType;
//    }
}
