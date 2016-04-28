package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.*;
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
    private List<PowerUpState> enabledPowerUpList;

    private int updatedEnemyDirection;

    private double timeSinceStart;

    public GameController(Group root) {
        GameEventBus.getInstance().register(this);

        initGame(root);
    }

    public void initGame(Group root) {
        //Change this to proper values according to player preferences later, dummy values meanwhile
        enabledPowerUpList = new ArrayList<>();
        enabledPowerUpList.add(PowerUpState.BOMB_COUNT);
        enabledPowerUpList.add(PowerUpState.RANGE);
        enabledPowerUpList.add(PowerUpState.HEALTH);
        enabledPowerUpList.add(PowerUpState.SPEED);

        // Initiates view

        timeSinceStart = 0.0;

        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);
        this.arenaView = new ArenaView(root);

        arenaView.initArena(arenaModel.getArenaTiles());
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), 0, 0);


        playerCharacter = new GameCharacter("ALYSSA");

        this.arenaView.updateStats(
                this.playerCharacter.getHealth(),
                this.playerCharacter.getSpeed(),
                this.playerCharacter.getBombRange(),
                this.playerCharacter.getCurrentBombCount()
        );

        try {
            arenaModel.addMovable(playerCharacter);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();

        Set<GameCharacter> gameCharacters = new HashSet<>();
        gameCharacters.add(playerCharacter);

        Random random = new Random();
        for (int k = 0; k < 10; k++) {
            boolean free;
            Point spawnPosition = new Point(0,0);
            do {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getWidth()), random.nextInt(this.arenaModel.getHeight()));
                free = (arenaModel.getArenaTile(spawnPosition) == null);
            } while (!free);
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

        this.timeSinceStart = timeSinceStart;

        List<String> input = ListenerController.getInstance().getInput();
        List<String> pressed = ListenerController.getInstance().getPressed();

        if (this.updatedEnemyDirection == 15) {
            for (Enemy enemy : enemies) {
                enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(), playerCharacter.getGridPosition(), this.arenaModel.getArenaTiles()), arenaModel.getArenaTiles());
                enemy.placeBomb();
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
//                case "ENTER":
//                    playerCharacter.spawn();
//                    break;
                case "ESCAPE":
                    this.arenaModel.getArenaMovables().clear();
                    for (int j = 0; j < this.arenaModel.getArenaTiles().length; j++) {
                        for (int k = 0; k < this.arenaModel.getArenaTiles()[0].length; k++) {
                            this.arenaModel.getArenaTiles()[j][k] = null;
                        }
                    }
                    this.enemies.clear();
                    this.deadEnemies.clear();
                    this.playerCharacter = null;

                    GameEventBus.getInstance().post(new GoToMenuEvent());
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "SPACE":
                    playerCharacter.placeBomb();
                    break;
                case "M":
                    playerCharacter.placeMine();
                    break;
            }
        }

        // Updating positions
        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

        if (this.playerCharacter != null &&
                this.arenaModel.getArenaTiles()[playerCharacter.getGridPosition().x][playerCharacter.getGridPosition().y]
                instanceof StatPowerUp) {
            StatPowerUp powerUp = (StatPowerUp)this.arenaModel.getArenaTiles()[playerCharacter.getGridPosition().x][playerCharacter.getGridPosition().y];

            if (powerUp.getPowerUpState() != null) {
                this.arenaModel.setTile(null, playerCharacter.getGridPosition());
                playerWalksOnPowerUp(powerUp.getPowerUpState());
                updateStats();
            }
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

        this.arenaModel.updateBombs(this.timeSinceStart);

        // Calls view to update graphics
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setTile(new Bomb(this.playerCharacter, this.playerCharacter.getBombRange(), Constants.DEFAULT_BOMB_DELAY, this.timeSinceStart, this.playerCharacter.getDamage()), placeBombEvent.getGridPosition());
        this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void minePlaced(PlaceMineEvent placeMineEvent) {
        arenaModel.setTile(new Mine(this.playerCharacter, 1, this.playerCharacter.getDamage()), placeMineEvent.getGridPosition());
        this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {
        this.arenaModel.setTile(blastEvent.getBlast(), blastEvent.getBlast().getPosition());
        if (this.playerCharacter != null && this.playerCharacter.getCurrentBombCount() < this.playerCharacter.getMaxBombCount()) {
            this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() + 1);
        }
    }

    /**
     * Gets called when a BlastSpriteCollection has finished its init-method.
     * Updates the model according the to blast that has occurred.
     *
     * @param blastTileInitDoneEvent The event that gets sent from BlastSpriteCollection
     */
    @Subscribe
    public void blastTileInitDone(BlastTileInitDoneEvent blastTileInitDoneEvent) {
        updateStats();
        Map<Direction, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Direction.LEFT, true);
        blastDirections.put(Direction.UP, true);
        blastDirections.put(Direction.RIGHT, true);
        blastDirections.put(Direction.DOWN, true);

//        if (this.playerCharacter.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
//            this.playerCharacter.dealDamage(blastTileInitDoneEvent.getDamage());
//        }
//
//        // Checks if enemy is ON the bomb.
//        for (Enemy enemy : this.enemies) {
//            if (enemy.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
//                enemy.dealDamage(blastTileInitDoneEvent.getDamage());
//                deadEnemies.add(enemy);
//            }
//        }
        for (Movable movable : arenaModel.getArenaMovables()) {
            if (movable.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
                movable.dealDamage(blastTileInitDoneEvent.getDamage());
            }
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
//                    } else if (this.playerCharacter.getGridPosition().equals(currentPosition)) {
//                        this.playerCharacter.dealDamage(blastTileInitDoneEvent.getDamage());
                    } else {
//                        for (Enemy enemy : this.enemies) {
//                            if (enemy.getGridPosition().equals(currentPosition)) {
//                                enemy.death();
//                                deadEnemies.add(enemy);
//                            }
//                        }
                        for (Movable movable : arenaModel.getArenaMovables()) {
                            if (movable.getGridPosition().equals((currentPosition))) {
                                movable.dealDamage(blastTileInitDoneEvent.getDamage());
                            }
                        }
                    }
                }
            }
        }

        updateStats();
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

    //TODO
    private void updateStats() {
        this.arenaView.updateStats(
                this.playerCharacter.getHealth(),
                this.playerCharacter.getSpeed(),
                this.playerCharacter.getBombRange(),
                this.playerCharacter.getCurrentBombCount()
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
    public void spawnPowerUp(Point position) {
        Random randomGenerator = new Random();
        if(randomGenerator.nextInt(4) < 2) {
            this.arenaModel.setTile(new StatPowerUp(enabledPowerUpList), position);
        }
    }

    /**
     * Call on this method when player walks on PowerUpTile.
     * Will set the appropriate stat value on the character that walks on it.
     * @param powerUpState
     */
    public void playerWalksOnPowerUp(PowerUpState powerUpState) {
        System.out.println(powerUpState);
        switch (powerUpState) {
            case HEALTH:
                if (playerCharacter.getHealth() <= 90) {
                    this.playerCharacter.setHealth(this.playerCharacter.getHealth() + 10);
                } else if (playerCharacter.getHealth() < 100) {
                    this.playerCharacter.setHealth(100);
                }
                break;
            case BOMB_COUNT:
                this.playerCharacter.setMaxBombCount(this.playerCharacter.getMaxBombCount() + 1);
                this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() + 1);
                break;
            case SPEED:
                this.playerCharacter.setSpeed(this.playerCharacter.getSpeed() + 0.2);
                break;
            case RANGE:
                this.playerCharacter.setBombRange(this.playerCharacter.getBombRange() + 1);
                break;
        }
    }

    @Subscribe
    public void movableDeath(DeathEvent deathEvent) {
        Movable movable = deathEvent.getMovable();
        if (movable instanceof GameCharacter) {
            movable.spawn(new Point(6, 5));
        } else if (movable instanceof Enemy) {
            deadEnemies.add((Enemy)movable);
        }
    }

    @Subscribe
    public void movableSpawn(SpawnEvent spawnEvent) {
        Movable movable = spawnEvent.getMovable();
        if (movable instanceof GameCharacter) {
            movable.idle();
        } else if (movable instanceof Enemy) {

        }
    }
}
