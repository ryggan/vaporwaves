package edu.chalmers.vaporwave.model;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Player {
    private int playerId;

    public Player(int id) {
        this.playerId = id;
    }

    public int getId() {
        return this.playerId;
    }
}
