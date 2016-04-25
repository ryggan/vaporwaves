package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.model.gameObjects.Bomb;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.Movable;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Directions;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.PowerUpState;
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

    @Subscribe
    public void blastTileInitDone(BlastTileInitDoneEvent blastTileInitDoneEvent) {

        Point position = blastTileInitDoneEvent.getPosition();
        Point currentPosition = new Point(0,0);
        int range = blastTileInitDoneEvent.getRange();

        Map<Directions, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Directions.LEFT, true);
        blastDirections.put(Directions.UP, true);
        blastDirections.put(Directions.RIGHT, true);
        blastDirections.put(Directions.DOWN, true);

        if (this.playerCharacter.getGridPosition().equals(new Point(position.x, position.y))) {
            playerRecievesDamage();
        }

        for (int i=1; i<=range; i++) {
            if((position.x - i) >= 0 && blastDirections.get(Directions.LEFT)) {
                if (this.arenaModel.getArenaTiles()[position.x - i][position.y] instanceof DestructibleWall) {
                    blastDirections.put(Directions.LEFT, false);
                    currentPosition.setLocation(position.x - i, position.y);
                    this.arenaModel.setTile(null, currentPosition);
                    spawnPowerUp(currentPosition);
                } else if (this.arenaModel.getArenaTiles()[position.x - i][position.y] instanceof IndestructibleWall) {
                    blastDirections.put(Directions.LEFT, false);
                } else if (this.playerCharacter.getGridPosition().equals(new Point(position.x - i, position.y))) {
                    playerRecievesDamage();
                }
            }

            if((position.y - i) >= 0 && blastDirections.get(Directions.UP)) {
                if(this.arenaModel.getArenaTiles()[position.x][position.y - i] instanceof DestructibleWall) {
                    blastDirections.put(Directions.UP, false);
                    currentPosition.setLocation(position.x, position.y - i);
                    this.arenaModel.setTile(null, currentPosition);
                    spawnPowerUp(currentPosition);
                } else if (this.arenaModel.getArenaTiles()[position.x][position.y - i] instanceof IndestructibleWall) {
                    blastDirections.put(Directions.UP, false);
                } else if (this.playerCharacter.getGridPosition().equals(new Point(position.x, position.y - i))) {
                    playerRecievesDamage();
                }
            }

            if(position.x + i < this.arenaModel.getArenaTiles().length && blastDirections.get(Directions.RIGHT)) {
                if (this.arenaModel.getArenaTiles()[position.x + i][position.y] instanceof DestructibleWall) {
                    blastDirections.put(Directions.RIGHT, false);
                    currentPosition.setLocation(position.x + i, position.y);
                    this.arenaModel.setTile(null, currentPosition);
                    spawnPowerUp(currentPosition);
                } else if (this.arenaModel.getArenaTiles()[position.x + i][position.y] instanceof IndestructibleWall) {
                    blastDirections.put(Directions.RIGHT, false);
                } else if (this.playerCharacter.getGridPosition().equals(new Point(position.x + i, position.y))) {
                    playerRecievesDamage();
                }
            }

            if(position.y + i < this.arenaModel.getArenaTiles()[0].length && blastDirections.get(Directions.DOWN)) {
                if (this.arenaModel.getArenaTiles()[position.x][position.y + i] instanceof DestructibleWall) {
                    blastDirections.put(Directions.DOWN, false);
                    currentPosition.setLocation(position.x, position.y + i);
                    this.arenaModel.setTile(null, currentPosition);
                    spawnPowerUp(currentPosition);
                } else if (this.arenaModel.getArenaTiles()[position.x][position.y + i] instanceof IndestructibleWall) {
                    blastDirections.put(Directions.DOWN, false);
                } else if (this.playerCharacter.getGridPosition().equals(new Point(position.x, position.y + i))) {
                    playerRecievesDamage();
                }
            }
        }
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
