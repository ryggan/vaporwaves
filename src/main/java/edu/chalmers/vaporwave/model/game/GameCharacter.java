package edu.chalmers.vaporwave.model.game;

import com.google.common.eventbus.EventBus;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.event.PlaceMineEvent;
import edu.chalmers.vaporwave.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameCharacter extends Movable {

    private double startHealth;
    private double startSpeed;
    private int startBombRange;
    private int startMaxBombCount;
    private double startDamage;
    private Point startPosition;

    private int bombRange;
    private int maxBombCount;
    private int currentBombCount;
    private int playerId;

    private List<Pair<PowerUpType, Double>> powerUpPickedUp;


    public GameCharacter(String name, int playerId) {
        super(name, 0, 0, 0);

        CharacterID characterID = CharacterID.valueOf(name);
        this.startHealth = Container.getCharacterHealth(characterID);
        this.startSpeed = Container.getCharacterSpeed(characterID);
        this.startBombRange = Container.getCharacterBombRange(characterID);
        this.startMaxBombCount = Container.getCharacterBombCount(characterID);
        this.startDamage = Container.getCharacterDamage(characterID);

        this.playerId = playerId;
    }

    public void setSpawnPosition(Point spawnPosition) {
        setCanvasPosition(Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y));

        // Set startPosition, do a clone of spawn position to avoid exploiting internal representation of Point
        this.startPosition = (Point)spawnPosition.clone();
    }

    private void resetStats() {
        setHealth(this.startHealth);
        setSpeed(this.startSpeed);
        this.bombRange = this.startBombRange;
        this.maxBombCount = this.startMaxBombCount;
        this.currentBombCount = this.maxBombCount;
        this.setDamage(this.startDamage);

        this.powerUpPickedUp = new ArrayList<>();
    }

    public void placeBomb() {
        if (this.currentBombCount > 0 && (getState() == MovableState.IDLE || getState() == MovableState.WALK)) {
            Container.playSound(SoundID.PLACE_BOMB);
            
            PlaceBombEvent event =
                    new PlaceBombEvent(this, Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()),
                    bombRange,
                    getDamage());
            EventBus bus = GameEventBus.getInstance();

            bus.post(event);
        }
    }

    public void placeMine() {
        if(this.currentBombCount > 0) {
            GameEventBus.getInstance().post(
                    new PlaceMineEvent(this, Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()),
                    bombRange,
                    getDamage()));
        }
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombRange() {
        return this.bombRange;
    }

    public void setCurrentBombCount(int bombCount) {
        this.currentBombCount = bombCount;
    }

    public int getCurrentBombCount() {
        return this.currentBombCount;
    }

    public void setMaxBombCount(int bombCount) {
        this.maxBombCount = bombCount;
    }

    public int getMaxBombCount() {
        return this.maxBombCount;
    }

    public void pickedUpPowerUp(PowerUpType powerUpType, double timeStamp) {
        this.powerUpPickedUp.add(new Pair<>(powerUpType, timeStamp));
    }

    public List<Pair<PowerUpType, Double>> getPowerUpPickedUp() {
        return powerUpPickedUp;
    }

    public int getPlayerID() {
        return this.playerId;
    }

    @Override
    public void flinch() {
        super.flinch();
        Container.playSound(SoundID.CHARACTER_FLINCH);
    }

    @Override
    public void spawn(Point spawningPosition) {
        if (spawningPosition == null) {
            super.spawn(this.startPosition);
        } else {
            super.spawn(spawningPosition);
        }
        resetStats();
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (otherObject.getClass() != this.getClass()) {
            return false;
        }
        GameCharacter other = (GameCharacter)otherObject;
        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode(){
        return super.hashCode() +
                (bombRange * 5) +
                (currentBombCount * 7) +
                (maxBombCount * 11);
    }
}
