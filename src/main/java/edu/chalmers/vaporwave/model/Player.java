package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ArrayCloner;
import net.java.games.input.Controller;

public class Player {
    private int playerId;
    private String playerName;
    private GameCharacter gameCharacter;
    private int score;
    private int kills;
    private int deaths;
    private int creeps;
    private int powerUpScore;
    private String[] playerInfo;

    private Controller gamePad;

    public Player(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = 0;
        this.deaths = 0;
        this.creeps = 0;
        this.kills = 0;
        this.powerUpScore = 0;
        playerInfo = new String[5];
    }

    public void incrementPowerUpScore() {
        powerUpScore++;
        System.out.println("Powerupscore after update is " + powerUpScore);
    }

    public int getScore() {
        this.score = this.powerUpScore*50 + this.kills*1000 + this.creeps*100 - this.deaths*200;
        if(this.score >= 0) {
            return this.score;
        } else {
            this.score = this.score + this.deaths*200 - this.powerUpScore*50 + this.kills*1000 + this.creeps*100;
            return this.score;
        }
    }

    public void incrementKills() {
        kills++;
    }

    public int getKills() {
        return this.kills;
    }

    public void incrementDeaths() {
        deaths++;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void incrementCreeps() {
        creeps++;
    }

    public int getCreeps() {
        return this.creeps;
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

    public void setPlayerId(int i) {
        this.playerId = i;
    }

    public String[] getPlayerInfo() {
        this.score = getScore();
        playerInfo[0] = playerName;
        playerInfo[1] = kills + "";
        playerInfo[2] = deaths + "";
        playerInfo[3] = creeps + "";
        playerInfo[4] = score + "";
        return ArrayCloner.stringArrayCloner(this.playerInfo);
    }

    public void setGamePad(Controller gamePad) {
        this.gamePad = gamePad;
    }

    public Controller getGamePad() {
        return this.gamePad;
    }
}
