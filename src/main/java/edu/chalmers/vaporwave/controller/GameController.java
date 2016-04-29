package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    private Set<Player> remotePlayersSet;
    private Player localPlayer;

    private Set<Enemy> enemies;
    private Set<Enemy> deadEnemies;
    private List<PowerUpState> enabledPowerUpList;

    private int updatedEnemyDirection;

    private double timeSinceStart;
    private double systemNanoTime;

    private SoundPlayer backgroundMusic;

    public GameController(Group root) {
        GameEventBus.getInstance().register(this);
        systemNanoTime = System.nanoTime();
    }

    public void initGame(Group root, NewGameEvent newGameEvent) {
        //Change this to proper values according to player preferences later, dummy values meanwhile
        backgroundMusic = new SoundPlayer("bg1.mp3");
        backgroundMusic.playSound();

        enabledPowerUpList = new ArrayList<>();
        enabledPowerUpList.add(PowerUpState.BOMB_COUNT);
        enabledPowerUpList.add(PowerUpState.RANGE);
        enabledPowerUpList.add(PowerUpState.HEALTH);
        enabledPowerUpList.add(PowerUpState.SPEED);

        this.localPlayer = newGameEvent.getLocalPlayer();

        // Initiates view

        timeSinceStart = 0.0;


        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);
        this.arenaView = new ArenaView(root);

        arenaView.initArena(arenaModel.getArenaTiles());
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), 0, 0);



        this.arenaView.updateStats(
                this.localPlayer.getCharacter().getHealth(),
                this.localPlayer.getCharacter().getSpeed(),
                this.localPlayer.getCharacter().getBombRange(),
                this.localPlayer.getCharacter().getCurrentBombCount()
        );

        try {
            arenaModel.addMovable(localPlayer.getCharacter());
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();

        Random random = new Random();
        for (int k = 0; k < 10; k++) {
            boolean free;
            Point spawnPosition = new Point(0,0);
            do {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getWidth()), random.nextInt(this.arenaModel.getHeight()));
                free = (arenaModel.getArenaTile(spawnPosition) == null);
            } while (!free);
            Enemy enemy = new Enemy("Enemy", Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y), 0.2, new StupidAI());
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
                enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(), localPlayer.getCharacter().getGridPosition(), this.arenaModel.getArenaTiles()), arenaModel.getArenaTiles());
//                enemy.placeBomb();
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
                    localPlayer.getCharacter().move(Utils.getDirectionFromString(key), arenaModel.getArenaTiles());
                    break;
                case "ESCAPE":
                    backgroundMusic.stopSound();
                    this.arenaModel.getArenaMovables().clear();
                    for (int j = 0; j < this.arenaModel.getArenaTiles().length; j++) {
                        for (int k = 0; k < this.arenaModel.getArenaTiles()[0].length; k++) {
                            this.arenaModel.getArenaTiles()[j][k] = null;
                        }
                    }
                    this.enemies.clear();
                    this.deadEnemies.clear();

                    GameEventBus.getInstance().post(new GoToMenuEvent());
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "SPACE":
                    if (arenaModel.getArenaTile(playerCharacter.getGridPosition()) == null) {
                        playerCharacter.placeBomb();
                    }
                    break;
                case "M":
                    localPlayer.getCharacter().placeMine();
                    break;
                case "X":
                    backgroundMusic.stopSound();
                    break;
            }
        }

        // Updating positions
        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

        if (this.localPlayer.getCharacter() != null &&
                this.arenaModel.getArenaTiles()[localPlayer.getCharacter().getGridPosition().x][localPlayer.getCharacter().getGridPosition().y]
                instanceof StatPowerUp) {
            StatPowerUp powerUp = (StatPowerUp)this.arenaModel.getArenaTiles()[localPlayer.getCharacter().getGridPosition().x][localPlayer.getCharacter().getGridPosition().y];

            if (powerUp.getPowerUpState() != null) {
                this.arenaModel.setTile(null, localPlayer.getCharacter().getGridPosition());
                playerWalksOnPowerUp(powerUp.getPowerUpState());
                updateStats();
            }
        }

        if (deadEnemies.size() > 0) {
            for (Enemy enemy : deadEnemies) {
                this.enemies.remove(enemy);
                this.arenaModel.removeMovable(enemy);
            }
            deadEnemies.clear();
        }

        this.arenaModel.updateBombs(this.timeSinceStart);

        // Calls view to update graphics
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setTile(new Bomb(this.localPlayer.getCharacter(), this.localPlayer.getCharacter().getBombRange(), Constants.DEFAULT_BOMB_DELAY, this.timeSinceStart, this.localPlayer.getCharacter().getDamage()), placeBombEvent.getGridPosition());
        this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void minePlaced(PlaceMineEvent placeMineEvent) {
        arenaModel.setTile(new Mine(this.localPlayer.getCharacter(), 1, this.localPlayer.getCharacter().getDamage()), placeMineEvent.getGridPosition());
        this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {

        this.playerCharacter.setCurrentBombCount(this.playerCharacter.getCurrentBombCount() + 1);

        Map<Direction, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Direction.LEFT, true);
        blastDirections.put(Direction.UP, true);
        blastDirections.put(Direction.RIGHT, true);
        blastDirections.put(Direction.DOWN, true);

        Explosive explosive = blastEvent.getExplosive();

        this.arenaModel.setTile(new Blast(explosive, BlastState.CENTER, null, this.timeSinceStart), explosive.getPosition());

        for (int i = 0; i < explosive.getRange(); i++) {
            for (int j = 0; j < 4; j++) {
                Direction direction = Utils.getDirectionFromInteger(j);

                Point position = Utils.getRelativePoint(explosive.getPosition(), i + 1, direction);

                if (!isValidPosition(position)) {
                    blastDirections.put(direction, false);
                }

                BlastState state = BlastState.BEAM;
                if (i == explosive.getRange() - 1) {
                    state = BlastState.END;
                }

                if (blastDirections.get(direction)) {
                    StaticTile currentTile = this.arenaModel.getArenaTile(position);
                    if (currentTile != null) {
                        if (currentTile instanceof DestructibleWall) {
//                            System.out.println("Destroy wall!");
                        } else if (currentTile instanceof Explosive) {
                            ((Explosive)currentTile).setDelay(0.03, timeSinceStart);
                        }
                        if (currentTile instanceof Blast && ((Blast)currentTile).getState() == BlastState.END) {
                            if (state == BlastState.END) {
                                state = BlastState.BEAM;
                            }
                            this.arenaModel.setTile(new Blast(explosive, state, direction, this.timeSinceStart), position);

                        } else {
                            blastDirections.put(direction, false);
                        }
                    } else {
                        this.arenaModel.setTile(new Blast(explosive, state, direction, this.timeSinceStart), position);
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

    //TODO
    private void updateStats() {
        this.arenaView.updateStats(
                this.localPlayer.getCharacter().getHealth(),
                this.localPlayer.getCharacter().getSpeed(),
                this.localPlayer.getCharacter().getBombRange(),
                this.localPlayer.getCharacter().getCurrentBombCount()
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
                if (localPlayer.getCharacter().getHealth() <= 90) {
                    this.localPlayer.getCharacter().setHealth(this.localPlayer.getCharacter().getHealth() + 10);
                } else if (localPlayer.getCharacter().getHealth() < 100) {
                    this.localPlayer.getCharacter().setHealth(100);
                }
                break;
            case BOMB_COUNT:
                this.localPlayer.getCharacter().setMaxBombCount(this.localPlayer.getCharacter().getMaxBombCount() + 1);
                this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() + 1);
                break;
            case SPEED:
                this.localPlayer.getCharacter().setSpeed(this.localPlayer.getCharacter().getSpeed() + 0.2);
                break;
            case RANGE:
                this.localPlayer.getCharacter().setBombRange(this.localPlayer.getCharacter().getBombRange() + 1);
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
