package edu.chalmers.vaporwave.model.gameObjects;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Blast {
    double power;
    //radius also, what Type?

    public Blast(Explosive b) {
        this.power = b.getRange();
    }
}
