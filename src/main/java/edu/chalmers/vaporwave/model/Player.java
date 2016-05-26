package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ClonerUtility;
import net.java.games.input.Controller;

public class Player {
    private int playerID;
    private String playerName;
    private GameCharacter gameCharacter;
    private int score;
    private int kills;
    private int deaths;
    private int creeps;
    private int powerUpScore;
    private String[] playerInfo;

    private String[] directionControls;
    private String bombControl;
    private String mineControl;
    private Controller gamePad;

    public Player(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerInfo = new String[5];
        this.directionControls = new String[0];

//        System.out.println("New player, id: "+playerID+", name: "+playerName);

        resetPlayerGameStats();
    }

    public void incrementPowerUpScore() {
        powerUpScore++;
//        System.out.println("Powerupscore after update is " + powerUpScore);
    }

    // todo: this as a counter instead?
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

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int i) {
        this.playerID = i;
    }

    public String getName() {
        return this.playerName;
    }

    public void setName(String name) {
        this.playerName = name;
    }

    public String[] getPlayerInfo() {
        this.score = getScore();
        playerInfo[0] = playerName;
        playerInfo[1] = kills + "";
        playerInfo[2] = deaths + "";
        playerInfo[3] = creeps + "";
        playerInfo[4] = score + "";
        return ClonerUtility.stringArrayCloner(this.playerInfo);
    }

    public void setGamePad(Controller gamePad) {
        this.gamePad = gamePad;
//        System.out.println("Player "+getPlayerID()+", Gamepad set: "+gamePad);
    }

    public Controller getGamePad() {
        return this.gamePad;
    }

    public void setDirectionControls(String[] directionControls) {
        this.directionControls = ClonerUtility.stringArrayCloner(directionControls);
    }

    public String[] getDirectionControls() {
        return ClonerUtility.stringArrayCloner(this.directionControls);
    }

    public void setBombControl(String bombControl) {
        this.bombControl = bombControl;
    }

    public String getBombControl() {
        return this.bombControl;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (otherObject.getClass() != this.getClass()) {
            return false;
        }
        Player other = (Player)otherObject;
        return this.playerID == other.playerID &&
                this.playerName.equals(other.playerName) &&
                this.score == other.score &&
                this.kills == other.kills &&
                this.creeps == other.creeps &&
                this.powerUpScore == other.powerUpScore;
    }

    @Override
    public int hashCode() {
        return this.playerID * 3 +
                this.playerName.hashCode() * 5 +
                this.score * 11 +
                this.kills * 13 +
                this.creeps * 17 +
                this.powerUpScore * 19;
    }

    public String toString() {
        return "Player [ Name: "+playerName+", ID: "+playerID+", Score: "+score+", Kills: "+kills+", Deaths: "+deaths
                +", GamePad: "+gamePad+" ]";
    }

    public void resetPlayerGameStats() {
        this.kills = 0;
        this.creeps = 0;
        this.powerUpScore = 0;
        this.score = 0;
        this.deaths = 0;
    }

}
