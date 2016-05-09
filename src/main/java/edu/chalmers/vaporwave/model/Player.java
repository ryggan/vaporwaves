package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.game.GameCharacter;

public class Player {
    private int playerId;
    private String playerName;
    private GameCharacter gameCharacter;
    private int score;

    public Player(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void clearScore() {
        this.score = 0;
    }

    public void setCharacter(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    public GameCharacter getCharacter() {
        return this.gameCharacter;
    }

    public void clearCharacter() {
        this.gameCharacter = null;
    }

    public int getPlayerId() {
        return this.playerId;
    }

}
