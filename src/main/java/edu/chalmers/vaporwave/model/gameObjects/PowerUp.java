package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;


public abstract class PowerUp extends StaticTile {

    int spawnChance;
    int statusEffect;
    PowerUpState powerUpState;

    public PowerUp(int spawnChance, int statusEffect, PowerUpState powerUpState){
        this.spawnChance = spawnChance;
        this.statusEffect = statusEffect;
        this.powerUpState = powerUpState;
    }

    public PowerUp() {

    }

    public int getSpawnChance(){
        return spawnChance;
    }

    public int getStatusEffect(){
        return statusEffect;
    }

    public PowerUpState getPowerType(){
        return powerUpState;
    }

}
