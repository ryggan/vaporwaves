package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;


public abstract class PowerUp extends StaticTile implements AnimatedTile {

    private int spawnChance;
    private int statusEffect;
    private PowerUpType powerUpType;
    private PowerUpState powerUpState;
    private double timeStamp;

    public enum PowerUpState {
        INTRO, IDLE, END
    }

    public PowerUp(int spawnChance, int statusEffect, PowerUpType powerUpType){
        this();
        this.spawnChance = spawnChance;
        this.statusEffect = statusEffect;
        this.powerUpType = powerUpType;
    }

    public PowerUp() {
        this.powerUpState = PowerUpState.INTRO;
        this.timeStamp = -1;
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
