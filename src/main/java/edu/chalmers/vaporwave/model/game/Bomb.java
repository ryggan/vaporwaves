package edu.chalmers.vaporwave.model.game;

public class Bomb extends Explosive {

//    private double timeSinceStart;

    public Bomb(GameCharacter owner, int range, double delay, double timeStamp, double damage) {
        super(owner, range, delay, timeStamp, damage);
//        super(owner, range, damage);
//        this.delay = delay;
//        this.timeSinceStart = timeSinceStart;
    }

//    public void updateTimer(double timeSinceStart) {
//        if (timeSinceStart - this.timeSinceStart > delay) {
//            explode();
//        }
//    }

}
