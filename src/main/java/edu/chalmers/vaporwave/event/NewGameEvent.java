package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewGameEvent {
    private ArenaMap arenaMap;
    private Player localPlayer;
    private Set<Player> players;
    private Set<PowerUpType> enabledPowerUps;
    private int timeLimit;

    public NewGameEvent() {
        this.enabledPowerUps = new HashSet<>();
        this.players = new HashSet<>();
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
            if (player.getPlayerId() == 0) {
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
