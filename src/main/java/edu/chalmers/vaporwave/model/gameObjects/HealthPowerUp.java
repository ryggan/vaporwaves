package edu.chalmers.vaporwave.model.gameObjects;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public class HealthPowerUp implements PowerUpState {
    private int randomValue = 2;

    public int getRandomValue() {
        return this.randomValue;
    }

    public int getEffect() {
        return 1;
    }
    public void getPowerType() {

    }
}
