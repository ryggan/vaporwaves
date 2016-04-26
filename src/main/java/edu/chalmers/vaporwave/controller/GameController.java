package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
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

    private Set<Enemy> enemies;
    private Set<Enemy> deadEnemies;
    private int deadEnemiesAnimation = 0;

    private int updatedEnemyDirection;

    public GameController(Group root) {

        // Initiates view

        this.arenaView = new ArenaView(root);

        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);

        arenaView.initArena(arenaModel.getArenaTiles());


        GameEventBus.getInstance().register(this);

        playerCharacter = new GameCharacter("ALYSSA");

        updateStats();

        try {
            arenaModel.addMovable(playerCharacter);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }


        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();

        Set<GameCharacter> gameCharacters = new HashSet<>();
        gameCharacters.add(playerCharacter);

<<<<<<< HEAD
        Enemy enemyOne = new Enemy("Enemy", Utils.gridToCanvasPosition(5), Utils.gridToCanvasPosition(5), 0.6, new SemiStupidAI(gameCharacters));
        Enemy enemyTwo = new Enemy("Enemy", Utils.gridToCanvasPosition(0), Utils.gridToCanvasPosition(4), 0.2, new StupidAI());
        Enemy enemyThree = new Enemy("Enemy", Utils.gridToCanvasPosition(7), Utils.gridToCanvasPosition(0), 0.2, new StupidAI());
        Enemy enemyFour = new Enemy("Enemy", Utils.gridToCanvasPosition(4), Utils.gridToCanvasPosition(8), 0.2, new StupidAI());
        Enemy enemyFive = new Enemy("Enemy", Utils.gridToCanvasPosition(15), Utils.gridToCanvasPosition(10), 0.2, new StupidAI());
        enemies.add(enemyOne);
        enemies.add(enemyTwo);
        enemies.add(enemyThree);
        enemies.add(enemyFour);
        enemies.add(enemyFive);
        
        Random random = new Random();
        for (int k = 0; k < 10; k++) {
            boolean free = false;
            Point spawnPosition = new Point(0,0);
            while (!free) {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getWidth()), random.nextInt(this.arenaModel.getHeight()));
                if (arenaModel.getArenaTile(spawnPosition) == null) {
                    free = true;
                }
            }
            Enemy enemy = new Enemy("Enemy", Utils.gridToCanvasPosition(spawnPosition.x), Utils.gridToCanvasPosition(spawnPosition.y), 0.2, new StupidAI());
            enemies.add(enemy);
        }

        for(Enemy enemy : enemies) {
            try {
                arenaModel.addMovable(enemy);
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Tile out of bounds!");
            }
        }
    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        List<String> input = ListenerController.getInstance().getInput();
        List<String> pressed = ListenerController.getInstance().getPressed();

        if (this.updatedEnemyDirection == 15) {
            for (Enemy enemy : enemies) {
                enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(), playerCharacter.getGridPosition(), this.arenaModel.getArenaTiles()), arenaModel.getArenaTiles());
            }
            updatedEnemyDirection = 0;
        }
        updatedEnemyDirection += 1;

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            switch (key) {
                case "UP":
                case "LEFT":
                case "DOWN":
                case "RIGHT":
                    playerCharacter.move(Utils.getDirectionFromString(key), arenaModel.getArenaTiles());
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

        if (deadEnemies.size() > 0) {

            if (deadEnemiesAnimation == 0) {
                for (Enemy enemy : deadEnemies) {
                    this.enemies.remove(enemy);
                    this.arenaModel.removeMovable(enemy);
                }
                deadEnemies.clear();
                deadEnemiesAnimation = 0;
            } else {
                deadEnemiesAnimation += 1;
            }

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
        Map<Direction, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Direction.LEFT, true);
        blastDirections.put(Direction.UP, true);
        blastDirections.put(Direction.RIGHT, true);
        blastDirections.put(Direction.DOWN, true);

        if (this.playerCharacter.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
            playerRecievesDamage();
        }

        for (int i=1; i<=blastTileInitDoneEvent.getRange(); i++) {
            for (Direction direction : blastDirections.keySet()) {
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
                    } else {
                        for (Enemy enemy : this.enemies) {
                            if (enemy.getGridPosition().equals(currentPosition)) {
                                enemy.death();
                                deadEnemies.add(enemy);
                            }
                        }
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
