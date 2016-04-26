package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.HashSet;
import java.util.Set;

public class NewGameEvent {
    private ArenaMap arenaMap;
    private GameCharacter gameCharacter;
    private Set<PowerUpState> enabledPowerUps;
    private int timeLimit;

    public NewGameEvent() {
        this.enabledPowerUps = new HashSet<>();
    }

    public void setArenaMap(ArenaMap arenaMap) {
        this.arenaMap = arenaMap;
    }

    public ArenaMap getArenaMap() {
        return this.arenaMap;
    }

    public void setGameCharacter(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    public GameCharacter getGameCharacter() {
        return this.gameCharacter;
    }

    public void addPowerUp(PowerUpState powerUpState) {
        this.enabledPowerUps.add(powerUpState);
    }

    public void removePowerUp(PowerUpState powerUpState) {
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

}
