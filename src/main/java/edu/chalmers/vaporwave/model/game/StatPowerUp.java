package edu.chalmers.vaporwave.model.game;

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
                randomMaxValue += PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 1;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
//                j += PowerUpState.getSpawnChance(enabledPowerUpList.get(i));
//                System.out.println(enabledPowerUpList.size());
//                System.out.println(randomMaxValue);
//                if(j <= randomValue) {
                    powerUpState = enabledPowerUpList.get(randomValue);
//                    System.out.println(powerUpState);
//                }
            }
        }
    }
}
