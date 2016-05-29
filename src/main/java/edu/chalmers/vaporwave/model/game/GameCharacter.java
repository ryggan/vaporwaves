package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.assetcontainer.CharacterID;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Pair;
import edu.chalmers.vaporwave.util.PowerUpType;
import edu.chalmers.vaporwave.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a specific Movable that will be user controlled, or controlled by AI to
 * simulate a (somewhat dense) human user.
 * The most distinctive about GameCharacter, is that it can place bombs and has stats
 * that differs depending on which characteristics it is assigned.
 */
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
    private int playerID;

    private List<Pair<PowerUpType, Double>> powerUpPickedUp;

    // This constructor retrieves the specific stats based on the name given
    public GameCharacter(String name, int playerID) {
        super(name, 0, 0, 0);

        CharacterID characterID = CharacterID.valueOf(name);
        this.startHealth = Container.getCharacterHealth(characterID);
        this.startSpeed = Container.getCharacterSpeed(characterID);
        this.startBombRange = Container.getCharacterBombRange(characterID);
        this.startMaxBombCount = Container.getCharacterBombCount(characterID);
        this.startDamage = Container.getCharacterDamage(characterID);

        this.playerID = playerID;
    }

    // Test constructor
    public GameCharacter() {
        this ("ALYSSA", 0);
    }

    // Puts character on position and saves it for all future spawns
    public void setSpawnPosition(Point spawnPosition) {
        setCanvasPosition(Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y));

        // Set startPosition, do a clone of spawn position to avoid exploiting internal representation of Point
        this.startPosition = (Point)spawnPosition.clone();
    }

    // An extended version of Movables reset(), that resets even more attributes
    @Override
    public void reset() {
        super.reset();
        setHealth(this.startHealth);
        setSpeed(this.startSpeed);
        this.bombRange = this.startBombRange;
        this.maxBombCount = this.startMaxBombCount;
        this.currentBombCount = this.maxBombCount;
        this.setDamage(this.startDamage);

        this.powerUpPickedUp = new ArrayList<>();
    }

    // Called when pressing "place bomb"-key, posts a PlaceBombEvent back to GameController, to handle
    // the actual placing of the bomb
    public void placeBomb() {
        if (this.currentBombCount > 0 && (getState() == MovableState.IDLE || getState() == MovableState.WALK)) {

            if (Container.getIsInitialized()) {
                Container.playSound(SoundID.PLACE_BOMB);
            }

            GameEventBus.getInstance().post(new PlaceBombEvent(this, Utils.canvasToGridPosition(this.getCanvasPositionX(),
                    this.getCanvasPositionY()), bombRange, getDamage()));

            setCurrentBombCount(getCurrentBombCount() - 1);
        }
    }

    // Sets/gets
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

    public void setPlayerID(int ID) {
        this.playerID = ID;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    // Extended state methods that adds some GameCharacter specific functionality
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
        reset();
    }

    // Usual overrides
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
