package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpStates;

/**
 * Created by FEngelbrektsson on 18/04/16.
 */
public abstract class PowerUpState {

    int spawnChance;
    int statusEffect;
    PowerUpStates powerType;

    public PowerUpState(int spawnChance, int statusEffect, PowerUpStates powerType){
        this.spawnChance=spawnChance;
        this.statusEffect=statusEffect;
        this.powerType=powerType;
    }

    public int getSpawnChance(){
        return spawnChance;
    }

    public int getStatusEffect(){
        return statusEffect;
    }

    public PowerUpStates getPowerType(){
        return powerType;
    }

}
