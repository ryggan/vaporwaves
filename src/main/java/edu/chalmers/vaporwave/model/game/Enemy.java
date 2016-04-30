package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.util.AI;
import edu.chalmers.vaporwave.util.Utils;

public class Enemy extends Movable {

    private AI ai;

    private double deathTimeStamp;

    public Enemy(String name, double canvasPositionX, double canvasPositionY, double speed, AI ai) {
        super(name, canvasPositionX, canvasPositionY, speed);
        this.ai = ai;
        this.deathTimeStamp = -1;
    }

    public AI getAI() {
        return this.ai;
    }

    public void setDeathTimeStamp(double deathTimeStamp) {
        this.deathTimeStamp = deathTimeStamp;
    }

    public double getDeathTimeStamp() {
        return this.deathTimeStamp;
    }

    @Override
    public int hashCode(){
        int hash = super.hashCode() + ai.hashCode();
        return hash;
    }

}
