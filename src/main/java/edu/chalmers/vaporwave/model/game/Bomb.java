package edu.chalmers.vaporwave.model.game;

public class Bomb extends Explosive {

//    private double timeSinceStart;

    public Bomb(GameCharacter owner, int range, double delay, double timeStamp, double damage) {
        super(owner, range, delay, timeStamp, damage);
    }

    public String toString() {
        return super.toString() + ": Bomb ";
    }

}
