package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;
import edu.chalmers.vaporwave.util.SoundContainer;
import edu.chalmers.vaporwave.util.SoundID;


public abstract class PowerUp extends StaticTile implements AnimatedTile {

    private int spawnChance;
    private int statusEffect;
    private PowerUpType powerUpType;
    private PowerUpState powerUpState;
    private double timeStamp;

    public enum PowerUpState {
        SPAWN, IDLE, PICKUP, DESTROY
    }

    public PowerUp(int spawnChance, int statusEffect, PowerUpType powerUpType){
        this();
        this.spawnChance = spawnChance;
        this.statusEffect = statusEffect;
        this.powerUpType = powerUpType;
    }

    public PowerUp() {
        this.powerUpState = PowerUpState.SPAWN;
        this.timeStamp = -1;
    }

    public void pickUp(double timeStamp) {
        setState(PowerUp.PowerUpState.PICKUP);
        setTimeStamp(timeStamp);

        SoundContainer.getInstance().playSound(SoundID.POWERUP);
    }

    public void destroy(double timeStamp) {
        setState(PowerUpState.DESTROY);
        setTimeStamp(timeStamp);
    }

    public int getSpawnChance(){
        return spawnChance;
    }

    public int getStatusEffect(){
        return statusEffect;
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

}
