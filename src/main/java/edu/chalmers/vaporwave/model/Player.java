package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ClonerUtility;
import edu.chalmers.vaporwave.util.Constants;
import net.java.games.input.Controller;

/**
 * Represents every active player (generally up to four at the same time), and
 * holds all stats and other useful information
 */
public class Player implements Comparable<Player> {

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
    private Controller gamePad;

    public Player(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerInfo = new String[5];
        this.directionControls = new String[0];

        resetPlayerGameStats();
    }

    // Most of this is score-related; every time most stats change, the score does the same accordingly
    public void changeScore(int addition) {
        this.score = Math.max(this.score + addition, 0);
    }

    public int getScore() {
        return this.score;
    }

    public void resetPlayerGameStats() {
        this.kills = 0;
        this.creeps = 0;
        this.powerUpScore = 0;
        this.score = 0;
        this.deaths = 0;
    }

    public void incrementKills() {
        kills++;
        changeScore(Constants.SCORE_KILL_CHARACTER);
    }

    public void incrementPowerUpScore() {
        powerUpScore++;
        changeScore(Constants.SCORE_POWERUP);
    }

    public int getKills() {
        return this.kills;
    }

    public void incrementDeaths() {
        deaths++;
        changeScore(Constants.SCORE_DEATH_PENALTY);
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void incrementCreeps() {
        creeps++;
        changeScore(Constants.SCORE_KILL_ENEMY);
    }

    public void damagedCharacterScore() {
        changeScore(Constants.SCORE_DAMAGE_CHARACTER);
    }

    public void damagedEnemyScore() {
        changeScore(Constants.SCORE_DAMAGE_ENEMY);
    }

    public int getCreeps() {
        return this.creeps;
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

    // Next is normal setters and getters for all the other attributes
    public void setCharacter(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    public GameCharacter getCharacter() {
        return this.gameCharacter;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return this.playerName;
    }

    public void setName(String name) {
        this.playerName = name;
    }

    public void setGamePad(Controller gamePad) {
        this.gamePad = gamePad;
    }

    // Control specific stuff
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

    // Overridden normal methods
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

    @Override
    public int compareTo(Player player) {
        return getPlayerID() - player.getPlayerID();
    }
}
