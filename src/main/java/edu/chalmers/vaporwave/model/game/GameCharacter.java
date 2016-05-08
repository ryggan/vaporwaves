package edu.chalmers.vaporwave.model.game;

import com.google.common.eventbus.EventBus;
import edu.chalmers.vaporwave.controller.SoundController;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.event.PlaceMineEvent;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Sound;
import edu.chalmers.vaporwave.util.SoundPlayer;
import edu.chalmers.vaporwave.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class GameCharacter extends Movable {

    private int bombRange;
    private int maxBombCount;
    private int currentBombCount;

//    private double powerUpTimeStamp;
    private List<Double> powerUpPickedUp;

    public GameCharacter(String name) {
        super(name, Utils.gridToCanvasPositionX(6), Utils.gridToCanvasPositionY(5), 1);

        this.bombRange = 2;
        this.maxBombCount = 10;
        this.currentBombCount = this.maxBombCount;
        setSpeed(1.5);
        setDamage(30);

//        this.powerUpTimeStamp = -1;
        this.powerUpPickedUp = new ArrayList<>();
    }

    public void placeBomb() {
        if (this.currentBombCount > 0 && (getState() == MovableState.IDLE || getState() == MovableState.WALK)) {
            SoundController.getInstance().playSound(Sound.PLACE_BOMB);
            
            PlaceBombEvent event =
                    new PlaceBombEvent( Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()),
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

    @Override
    public boolean equals(Object o){
        if(o instanceof GameCharacter){
            GameCharacter other = (GameCharacter) o;
            if(this.bombRange==other.bombRange&&this.currentBombCount==other.currentBombCount&&this.maxBombCount==other.maxBombCount){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = super.hashCode();
        hash = hash + bombRange*23;
        hash=hash + currentBombCount*62;
        hash=hash + maxBombCount*71;
        return hash;
    }
}
