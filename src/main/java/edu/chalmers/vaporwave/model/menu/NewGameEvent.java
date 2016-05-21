package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.GameType;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.HashSet;
import java.util.Set;

public class NewGameEvent {

    private ArenaMap arenaMap;
    private Player localPlayer;
    private Set<Player> players;
    private Set<PowerUpType> enabledPowerUps;

    private GameType gameType;

    private int timeLimit;
    private boolean destroyablePowerups;
    private boolean respawnPowerups;
    private int killLimit;
    private int scoreLimit;

    public NewGameEvent() {
        this.enabledPowerUps = new HashSet<>();
        this.players = new HashSet<>();

        this.timeLimit = 120;
        this.destroyablePowerups = true;
        this.respawnPowerups = true;
        this.gameType = GameType.SURVIVAL;
        this.killLimit = 10;
        this.scoreLimit = 5000;
    }

    public void setArenaMap(ArenaMap arenaMap) {
        this.arenaMap = arenaMap;
    }

    public ArenaMap getArenaMap() {
        return this.arenaMap;
    }

    public void addPowerUp(PowerUpType powerUpState) {
        this.enabledPowerUps.add(powerUpState);
    }

    public void removePowerUp(PowerUpType powerUpState) {
        if(enabledPowerUps.contains(powerUpState)) {
            enabledPowerUps.remove(powerUpState);
        }
    }

    public void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public Player getLocalPlayer() {
        return this.localPlayer;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public Player getPrimaryPlayer() {
        for (Player player : players) {
            if (player.getPlayerID() == 0) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayerWithID(int id) {
        for (Player player : players) {
            if (player.getPlayerID() == id) {
                return player;
            }
        }
        return null;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public void setDestroyablePowerups(boolean destroyablePowerups) {
        this.destroyablePowerups = destroyablePowerups;
    }

    public boolean getDestroyablePowerups() {
        return this.destroyablePowerups;
    }

    public void setRespawnPowerups(boolean respawnPowerups) {
        this.respawnPowerups = respawnPowerups;
    }

    public boolean getRespawnPowerups() {
        return this.respawnPowerups;
    }

    public void setGameType (GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public void setKillLimit(int killLimit) {
        this.killLimit = killLimit;
    }

    public int getKillLimit() {
        return this.killLimit;
    }

    public void setScoreLimit(int scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public int getScoreLimit() {
        return this.scoreLimit;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof NewGameEvent) {
            NewGameEvent other = (NewGameEvent) o;
            return this.enabledPowerUps.equals(other.enabledPowerUps) &&
                    this.timeLimit == other.timeLimit;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (enabledPowerUps.hashCode() * 5) + (timeLimit * 7);
    }

}
