package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.util.PowerUpType;
import edu.chalmers.vaporwave.assetcontainer.SoundID;

import java.util.List;
import java.util.Random;

public class PowerUp extends StaticTile implements AnimatedTile {

    private PowerUpType powerUpType;
    private PowerUpState powerUpState;
    private double timeStamp;

    public enum PowerUpState {
        SPAWN, IDLE, PICKUP, DESTROY
    }

    public PowerUp(List<PowerUpType> enabledPowerUpList) {
        this.powerUpState = PowerUpState.SPAWN;
        this.timeStamp = -1;

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

    public PowerUp(PowerUpType powerUpType) {
        setPowerUpType(powerUpType);
    }

    public void pickUp(double timeStamp) {
        setState(PowerUp.PowerUpState.PICKUP);
        setTimeStamp(timeStamp);

        Container.playSound(SoundID.POWERUP);
    }

    public void destroy(double timeStamp) {
        setState(PowerUpState.DESTROY);
        setTimeStamp(timeStamp);
    }

    public void setPowerUpType(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
    }

    public PowerUpType getPowerUpType(){
        return this.powerUpType;
    }

    public void setState(PowerUpState state) {
        this.powerUpState = state;
    }

    public PowerUpState getState() {
        return this.powerUpState;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }

    public String toString() {
        return super.toString() + ": Powerup [ state:"+this.powerUpState+" ]";
    }

}
