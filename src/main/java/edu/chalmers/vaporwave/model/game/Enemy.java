package edu.chalmers.vaporwave.model.game;

/**
 * An enemy is a Movable that only walks around the arena and damages characters if
 * touching them. While GameCharacter receives its input from GameController, Enemy
 * will allways need to own an AI to funtion.
 * Since there often will exist more than one Enemy object in the arena at once,
 * this class also utilizes a timestamp, to render different deathanimations for
 * different Enemies.
 */
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
