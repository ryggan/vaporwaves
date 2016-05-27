package edu.chalmers.vaporwave.model.game;

public class Enemy extends Movable {

    private AI ai;

    private double deathTimeStamp;

    public Enemy(String name, double canvasPositionX, double canvasPositionY, double speed, AI ai) {
        super(name, canvasPositionX, canvasPositionY, speed);
        this.ai = ai;
        this.deathTimeStamp = -1;
        setDamage(20);
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
    public int hashCode() {
        return (super.hashCode() * 5);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Enemy){
            Movable other = (Movable) o;
            return super.equals(other);
        }
        return false;
    }

}
