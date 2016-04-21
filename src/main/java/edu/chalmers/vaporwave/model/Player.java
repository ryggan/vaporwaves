package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Player {
    private int playerId;
    private String playerName;
    private GameCharacter character;
    private int score;
    //name id character
    //ListenerController
    //everything that should be saved
    //score

    public Player(int id) {
        this.playerId = id;
        this.score=0;
    }

    public Player(int id, String playerName) {
        this.playerId = id;
        this.playerName=playerName;
        this.score=0;
    }

    public void setScore(int score){
        this.score=score;
    }

    public void clearScore(){
        this.score=0;
    }

    public void setCharacter(GameCharacter c){
        character=c;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void clearCharacter(){
        this.character=null;
    }

    public int getId() {
        return this.playerId;
    }

    public class KeyBindings {
        // ?
    }
}
