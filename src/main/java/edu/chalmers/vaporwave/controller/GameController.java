package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.model.gameObjects.Bomb;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.Movable;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    private GameCharacter playerCharacter;

    public GameController(Group root) {

        // Initiates view

        this.arenaView = new ArenaView(root);

        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);

        arenaView.initArena(arenaModel.getArenaTiles());


        GameEventBus.getInstance().register(this);


        // Setting up background



        // Setting up tiles



        // TEST TILES
        playerCharacter = new GameCharacter("ALYSSA");

        updateStats();


        try {
            arenaModel.addMovable(playerCharacter);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }
    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Game logic

        // Input handling:


//        for (Point position : bombPositions) {
//            this.arenaModel.removeTile(position);
//        }
//        bombPositions.clear();

        List<String> input = ListenerController.getInstance().getInput();


        List<String> pressed = ListenerController.getInstance().getPressed();

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            switch (key) {
                case "UP":
                case "LEFT":
                case "DOWN":
                case "RIGHT":
                    playerCharacter.move(key, arenaModel.getArenaTiles());
                    break;
                case "ENTER":
                    playerCharacter.spawn();
                    break;
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "SPACE":
                    playerCharacter.placeBomb();
                    break;
            }
        }



        // Updating positions

        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

        if (this.arenaModel.getArenaTiles()[playerCharacter.getGridPosition().x][playerCharacter.getGridPosition().y]
                instanceof TestPowerUp) {
            TestPowerUp powerUp = (TestPowerUp)this.arenaModel.getArenaTiles()[playerCharacter.getGridPosition().x][playerCharacter.getGridPosition().y];
            this.arenaModel.setTile(null, playerCharacter.getGridPosition());

            if(powerUp.getPowerUpState().equals(PowerUpState.SPEED)) {
                this.playerCharacter.setSpeed(this.playerCharacter.getSpeed() + 0.1);
            } else if(powerUp.getPowerUpState().equals(PowerUpState.BOMB_COUNT)) {
                this.playerCharacter.setBombCount(this.playerCharacter.getBombCount() + 1);
            } else if(powerUp.getPowerUpState().equals(PowerUpState.RANGE)) {
                this.playerCharacter.setBombRange(this.playerCharacter.getBombRange() + 1);
            }

            updateStats();
        }

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setTile(new Bomb(this.playerCharacter, this.playerCharacter.getBombRange(), 1000), placeBombEvent.getGridPosition());
    }

    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {
        arenaModel.setTile(blastEvent.getBlast(), blastEvent.getBlast().getPosition());
        playerCharacter.setBombCount(this.playerCharacter.getBombCount() + 1);
    }

    /**
     * Gets called when a BlastSpriteCollection has finished its init-method.
     * Updates the model according the to blast that has occurred.
     *
     * @param blastTileInitDoneEvent The event that gets sent from BlastSpriteCollection
     */
    @Subscribe
    public void blastTileInitDone(BlastTileInitDoneEvent blastTileInitDoneEvent) {
        Map<Directions, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Directions.LEFT, true);
        blastDirections.put(Directions.UP, true);
        blastDirections.put(Directions.RIGHT, true);
        blastDirections.put(Directions.DOWN, true);

        if (this.playerCharacter.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
            playerRecievesDamage();
        }

        for (int i=1; i<=blastTileInitDoneEvent.getRange(); i++) {
            for (Directions direction : blastDirections.keySet()) {
                Point currentPosition = Utils.getRelativePoint(blastTileInitDoneEvent.getPosition(), i, direction);
                if (isValidPosition(currentPosition) && blastDirections.get(direction)) {
                    if (this.arenaModel.getArenaTile(currentPosition) instanceof DestructibleWall) {
                        blastDirections.put(direction, false);
                        this.arenaModel.setTile(null, currentPosition);
                        spawnPowerUp(currentPosition);
                    } else if (this.arenaModel.getArenaTile(currentPosition) instanceof IndestructibleWall) {
                        blastDirections.put(direction, false);
                    } else if (this.arenaModel.getArenaTile(currentPosition) instanceof Bomb) {
                        ((Bomb) this.arenaModel.getArenaTile(currentPosition)).explode();
                    } else if (this.playerCharacter.getGridPosition().equals(currentPosition)) {
                        playerRecievesDamage();
                    }
                }
            }
        }
    }

    /**
     * Check if a specific position is within the bounds of the ArenaModel.
     *
     * @param position The position to check
     * @return true if position is within bounds, otherwise false.
     */
    private boolean isValidPosition(Point position) {
        return position.x >= 0 &&
                position.y >= 0 &&
                position.x < this.arenaModel.getArenaTiles().length &&
                position.y < this.arenaModel.getArenaTiles()[0].length;
    }

    private void playerRecievesDamage() {
        this.playerCharacter.setHealth(this.playerCharacter.getHealth() - 30);
        if(this.playerCharacter.getHealth() < 0) {
            this.playerCharacter.setHealth(100);
            this.playerCharacter.death();
        }
        updateStats();
    }

    private void updateStats() {
            this.arenaView.updateStats(
            this.playerCharacter.getHealth(),
            this.playerCharacter.getSpeed(),
            this.playerCharacter.getBombRange(),
            this.playerCharacter.getBombCount()
        );
    }

    public ArenaModel newGame(ArenaMap arenaMap) {

        // Here goes all code for setting up the environment for a new game

        return new ArenaModel(arenaMap);
    }


    /** 10% chance to create a SuperPowerUp, 40% chance to create a StatPowerUp and 50% chance to return null.
     *
     * @return A powerup Tile.
     */
    public void spawnPowerup(int posx, int posy) {
        Random randomGenerator = new Random();
        int temporaryNumber = randomGenerator.nextInt(10);
        if(temporaryNumber == 0 || temporaryNumber == 1
                || temporaryNumber == 2 || temporaryNumber == 3) {
            arenaModel.setTile(new StatPowerUp(), posx, posy);
        } else if(temporaryNumber == 4) {
            arenaModel.setTile(new StatPowerUp(), posx, posy);
        }
    }

    private void spawnPowerUp(Point position) {
        Random randomGenerator = new Random();
        if (randomGenerator.nextInt(10) < 2) {
            this.arenaModel.setTile(new TestPowerUp(PowerUpState.RANGE), position);
        } else if (randomGenerator.nextInt(10) < 2) {
            this.arenaModel.setTile(new TestPowerUp(PowerUpState.BOMB_COUNT), position);
        } else if (randomGenerator.nextInt(10) < 2) {
            this.arenaModel.setTile(new TestPowerUp(PowerUpState.SPEED), position);
        }
    }

    /**
     * Call on this method when player walks on PowerUpTile.
     * Will set the appropriate stat value on the character that walks on it.
     * @param character
     * @param powerUpState
     */
    public void playerWalksOnPowerUp(GameCharacter character, PowerUpState powerUpState) {
        if(powerUpState.equals(PowerUpState.HEALTH)) {
            // Could change how much health should be applied
            character.setHealth(character.getHealth() + 10);
        } else if(powerUpState.equals(PowerUpState.BOMB_COUNT)) {
            character.setBombCount(character.getBombCount() + 1);
        } else if(powerUpState.equals(PowerUpState.RANGE)) {
            character.setBombRange(character.getBombRange() + 1);
        } else if(powerUpState.equals(PowerUpState.SPEED)) {
            character.setSpeed(character.getSpeed() + 10);
        }
    }
}
