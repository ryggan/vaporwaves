package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpState;

public class TestPowerUp extends StaticTile {
    private PowerUpState powerUpState;

    public TestPowerUp(PowerUpState powerUpState) {
        this.powerUpState = powerUpState;
    }

    public PowerUpState getPowerUpState() {
        return this.powerUpState;
    }
}
