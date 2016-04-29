package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.Set;

public class NewGameEvent {
    private ArenaMap arenaMap;
    private GameCharacter gameCharacter;
    private Set<PowerUpState> enabledPowerUps;
    private int timeLimit;

    public NewGameEvent() {
//        this.enabledPowerUps = new HashSet<>();
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

    @Override
    public boolean equals(Object o){
        if(o instanceof NewGameEvent){
            NewGameEvent other = (NewGameEvent) o;
            if(this.arenaMap==other.arenaMap&&this.gameCharacter==other.gameCharacter&&
                    this.enabledPowerUps==other.enabledPowerUps&&this.timeLimit==other.timeLimit){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 1628;
        hash=hash+arenaMap.hashCode()*42;
        hash=hash+gameCharacter.hashCode()*73;
        hash=hash+enabledPowerUps.hashCode()*85;
        hash=hash+timeLimit*345;
        return hash;
    }

}
