package edu.chalmers.vaporwave.model.game;

public class Mine extends Explosive {

    public Mine(GameCharacter owner, int range, double damage) {
        super(owner, range, 0, -1, damage);
    }
}
