package edu.chalmers.vaporwave.model.game;

import com.google.common.eventbus.EventBus;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.event.PlaceMineEvent;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameCharacter extends Movable {

    private int startBombRange;
    private int startMaxBombCount;
    private double startHealth;
    private int startDamage;
    private double startSpeed;
    private Point startPosition;

    private int bombRange;
    private int maxBombCount;
    private int currentBombCount;
    private int playerId;

//    private double powerUpTimeStamp;
    private List<Double> powerUpPickedUp;

    public GameCharacter(String name, int playerId) {
        this(name, new Point(0,0), playerId);
    }

    public GameCharacter(String name, Point spawnPosition, int playerId) {
        super(name, Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y), 0);

        this.startPosition = (Point)spawnPosition.clone();
        this.startHealth = Constants.DEFAULT_START_HEALTH;
        this.startSpeed = 1.5;
        this.startBombRange = 2;
        this.startMaxBombCount = 10;
        this.startDamage = 30;
        this.playerId = playerId;

//        setHealth(this.startHealth);
//        setSpeed(this.startSpeed);
//        this.bombRange = this.startBombRange;
//        this.maxBombCount = this.startMaxBombCount;
//        this.currentBombCount = this.maxBombCount;
//        this.setDamage(this.startDamage);

//        resetStats();
        spawn(null);

        this.powerUpPickedUp = new ArrayList<>();
    }

    private void resetStats() {
        setHealth(this.startHealth);
        setSpeed(this.startSpeed);
        this.bombRange = this.startBombRange;
        this.maxBombCount = this.startMaxBombCount;
        this.currentBombCount = this.maxBombCount;
        this.setDamage(this.startDamage);
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
            GameEventBus.getInstance().post(new PlaceMineEvent(
                    Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()
                    ), getDamage())
            );
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

    public void pickedUpPowerUp(double timeStamp) {
        powerUpPickedUp.add(timeStamp);
    }

    public List<Double> getPowerUpPickedUp() {
        return powerUpPickedUp;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    @Override
    public void flinch() {
        super.flinch();
        Container.playSound(SoundID.CHARACTER_FLINCH);
    }

    @Override
    public void spawn(Point spawningPosition) {
        resetStats();
        if (spawningPosition == null) {
            super.spawn(this.startPosition);
        } else {
            super.spawn(spawningPosition);
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof GameCharacter) {
            GameCharacter other = (GameCharacter) o;
            return this.bombRange == other.bombRange &&
                    this.currentBombCount == other.currentBombCount &&
                    this.maxBombCount == other.maxBombCount;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return super.hashCode() +
                (bombRange * 5) +
                (currentBombCount * 7) +
                (maxBombCount * 11);
    }
}
