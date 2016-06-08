package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.GameType;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.HashSet;
import java.util.Set;

/**
 * This event is a bit special from the other events. This one is created when the first
 * menu runs, and then is filled more and more as the primary player navigates through
 * the menus. When all needed settings are set, it is posted to the bus, which then gives
 * it to the GameController to set up a new game.
 */
public class NewGameEvent {

    private ArenaMap arenaMap;
    private Set<Player> players;
    private Set<PowerUpType> enabledPowerUps;

    private GameType gameType;

    private int timeLimit;
    private boolean destroyablePowerups;
    private boolean respawnPowerups;
    private int killLimit;
    private int scoreLimit;

    // Default values, many of which we were planning to make customizable by the user,
    // but is for now just set values (we ran out of time)
    public NewGameEvent() {
        this.enabledPowerUps = new HashSet<>();
        this.players = new HashSet<>();

        // Defaults:
        this.gameType = GameType.SURVIVAL;
        this.arenaMap = new ArenaMap("default", (new MapFileReader(Container.getMap(FileID.VAPORMAP_DEFAULT))).getMapObjects());

        this.timeLimit = 120;
        this.killLimit = 10;
        this.scoreLimit = 5000;

        this.destroyablePowerups = true;
        this.respawnPowerups = true;
    }

    // The player handling is one of the core functios of NewGameEvent
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

    // Setters and getters, many of which is unused but kept anyway since we want to
    // extend the users choices for customization in the future
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

    // Usual overrides
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
