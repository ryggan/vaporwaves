package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.List;
import java.util.Random;

/**
 * This object is place on the arena and holds different advantages when a
 * character picks it up (i.e walks over it).
 * It also has a way of randomize which type it is.
 */
public class PowerUp extends StaticTile implements AnimatedTile {

    private PowerUpType powerUpType;
    private PowerUpState powerUpState;
    private double timeStamp;

    public enum PowerUpState {
        SPAWN, IDLE, PICKUP, DESTROY
    }

    // Default constructor, only used inside this class
    public PowerUp() {
        this.powerUpState = PowerUpState.SPAWN;
        this.timeStamp = -1;
    }

    // The randomizer constructor; it randomly choose from a list of allowed types,
    // and also takes their respective "spawnchance" into consideration
    public PowerUp(List<PowerUpType> enabledPowerUpList) {
        this();

        if(enabledPowerUpList.size() > 0) {
            int maxValue = 0;
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                maxValue += PowerUpType.getSpawnChance(enabledPowerUpList.get(i));
            }

            Random random = new Random();
            int randomValue = random.nextInt(maxValue);
            for (int i = 0; i < enabledPowerUpList.size(); i++) {
                setPowerUpType(enabledPowerUpList.get(randomValue));
            }
        }
    }

    // A much simpler constructor that only takes a type and sticks with it
    public PowerUp(PowerUpType powerUpType) {
        this();
        setPowerUpType(powerUpType);
    }

    // Different actions when somebody picks it up or destroys it. Both with timestamps, though,
    // since both variants results in animation
    public void pickUp(double timeStamp) {
        setState(PowerUp.PowerUpState.PICKUP);
        setTimeStamp(timeStamp);

        Container.playSound(SoundID.POWERUP);
    }

    public void destroy(double timeStamp) {
        setState(PowerUpState.DESTROY);
        setTimeStamp(timeStamp);
    }

    // sets and gets, baby
    public void setPowerUpType(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
    }

    public PowerUpType getPowerUpType(){
        return this.powerUpType;
    }

    public void setState(PowerUpState state) {
        this.powerUpState = state;
    }

    public PowerUpState getState() {
        return this.powerUpState;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTimeStamp() {
        return this.timeStamp;
    }

    public String toString() {
        return super.toString() + ": Powerup [ state:"+this.powerUpState+" ]";
    }

}
