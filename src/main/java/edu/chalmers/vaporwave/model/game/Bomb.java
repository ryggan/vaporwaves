package edu.chalmers.vaporwave.model.game;

public class Bomb extends Explosive {

    private double delay;
    private double timeSinceStart;

    public Bomb(GameCharacter owner, int range, double delay, double timeSinceStart, double damage) {
        super(owner, range, damage);
        this.delay = delay;
        this.timeSinceStart = timeSinceStart;
    }

    public void updateTimer(double timeSinceStart) {
        if (timeSinceStart - this.timeSinceStart > delay) {
            explode();
        }
    }

}
