package edu.chalmers.vaporwave.model.game;

/**
 * A bomb is put in the arena by a GameCharacter.
 *
 * See Explosive for more.
 */
public class Bomb extends Explosive {

    public Bomb(GameCharacter owner, int range, double delay, double timeStamp, double damage) {
        super(owner, range, delay, timeStamp, damage);
    }

    public String toString() {
        return super.toString() + ": Bomb ";
    }

}
