package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.view.HUDView;
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

    public GameController(Group root) {
        GameEventBus.getInstance().register(this);

        //Change this to proper values according to player preferences later, dummy values meanwhile
        enabledPowerUpList = new ArrayList<>();
        enabledPowerUpList.add(PowerUpState.BOMB_COUNT);
        enabledPowerUpList.add(PowerUpState.RANGE);
        enabledPowerUpList.add(PowerUpState.HEALTH);
        enabledPowerUpList.add(PowerUpState.SPEED);

        // Initiates view



        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);
        this.arenaView = new ArenaView(root);

        arenaView.initArena(arenaModel.getArenaTiles());
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), 0, 0);


        playerCharacter = new GameCharacter("CHARLOTTE");

//        updateStats();

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

////        Enemy enemyOne = new Enemy("Enemy", Utils.gridToCanvasPosition(5), Utils.gridToCanvasPosition(5), 0.6, new SemiStupidAI(gameCharacters));
//        Enemy enemyTwo = new Enemy("Enemy", Utils.gridToCanvasPosition(0), Utils.gridToCanvasPosition(4), 0.2, new StupidAI());
//        Enemy enemyThree = new Enemy("Enemy", Utils.gridToCanvasPosition(7), Utils.gridToCanvasPosition(0), 0.2, new StupidAI());
//        Enemy enemyFour = new Enemy("Enemy", Utils.gridToCanvasPosition(4), Utils.gridToCanvasPosition(8), 0.2, new StupidAI());
//        Enemy enemyFive = new Enemy("Enemy", Utils.gridToCanvasPosition(15), Utils.gridToCanvasPosition(10), 0.2, new StupidAI());
////        enemies.add(enemyOne);
//        enemies.add(enemyTwo);
//        enemies.add(enemyThree);
//        enemies.add(enemyFour);
//        enemies.add(enemyFive);

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
                case "ESCAPE":
                    GameEventBus.getInstance().post(new GoToMenuEvent());
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

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setTile(new Bomb(this.playerCharacter, this.playerCharacter.getBombRange(), Constants.DEFAULT_BOMB_DELAY), placeBombEvent.getGridPosition());
        this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {
        this.arenaModel.setTile(blastEvent.getBlast(), blastEvent.getBlast().getPosition());
        if (this.playerCharacter.getCurrentBombCount() < this.playerCharacter.getMaxBombCount()) {
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

        if (this.playerCharacter.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
            playerRecievesDamage();
        }

        // Checks if enemy is ON the bomb.
        for (Enemy enemy : this.enemies) {
            if (enemy.getGridPosition().equals(blastTileInitDoneEvent.getPosition())) {
                enemy.death();
                deadEnemies.add(enemy);
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


    //TODO
    private void updateStats() {
        System.out.println(this.playerCharacter.getHealth() + "\nHealth: " + this.playerCharacter.getHealth() + " / " + this.playerCharacter.getMaxHealth() + "\nRange: " + this.playerCharacter.getBombRange() + "\nBombs: " + this.playerCharacter.getCurrentBombCount() + " / " + this.playerCharacter.getMaxBombCount());
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
                this.playerCharacter.setHealth(this.playerCharacter.getHealth() + 10);
                this.playerCharacter.setMaxHealth(this.playerCharacter.getMaxHealth() + 10);
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
}
