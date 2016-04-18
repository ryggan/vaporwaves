package edu.chalmers.vaporwave.model;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Player {
    private int playerId;
    private String playerName;
    //namn id options

    public Player(int id) {
        this.playerId = id;
    }

    public int getId() {
        return this.playerId;
    }
}
