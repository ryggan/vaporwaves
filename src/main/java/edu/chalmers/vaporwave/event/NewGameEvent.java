package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.HashSet;
import java.util.Set;

public class NewGameEvent {
    private ArenaMap arenaMap;
    private Set<Player> remotePlayersSet;
    private Player localPlayer;
    private Set<PowerUpState> enabledPowerUps;
    private int timeLimit;

    public NewGameEvent() {
        this.enabledPowerUps = new HashSet<>();
        this.remotePlayersSet = new HashSet<>();
    }

    public void setArenaMap(ArenaMap arenaMap) {
        this.arenaMap = arenaMap;
    }

    public ArenaMap getArenaMap() {
        return this.arenaMap;
    }

    public void addPowerUp(PowerUpState powerUpState) {
        this.enabledPowerUps.add(powerUpState);
    }

    public void removePowerUp(PowerUpState powerUpState) {
        if(enabledPowerUps.contains(powerUpState)) {
            enabledPowerUps.remove(powerUpState);
        }
    }

    public void addRemotePlayer(Player player) {
        this.remotePlayersSet.add(player);
    }

    public void removePlayer(Player player) {
        if(remotePlayersSet.contains(player)) {
            remotePlayersSet.remove(player);
        }
    }

    public void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public Player getLocalPlayer() {
        return this.localPlayer;
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
            if(this.arenaMap == other.arenaMap &&
                    this.enabledPowerUps == other.enabledPowerUps &&
                    this.timeLimit==other.timeLimit) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 1628;
//        hash=hash+arenaMap.hashCode()*42;
        hash=hash+enabledPowerUps.hashCode()*85;
        hash=hash+timeLimit*345;
        return hash;
    }

}
