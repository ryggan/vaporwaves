package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    private HealthBarModel healthBarModel;

    private Player localPlayer;
    private Player remotePlayer;

    private Set<Enemy> enemies;
    private Set<Enemy> deadEnemies;
    private List<PowerUpType> enabledPowerUpList;

    private int updatedEnemyDirection;

    private double timeSinceStart;

    private boolean gameIsPaused = false;

    //seconds
    private double timeLimit;

    // settings for one specific game:
    private boolean destroyablePowerUps;

    public GameController(Group root) {
        GameEventBus.getInstance().register(this);
    }

    public void initGame(Group root, NewGameEvent newGameEvent) {


        SoundContainer.getInstance().playSound(SoundID.GAME_MUSIC);

        enabledPowerUpList = new ArrayList<>();
        enabledPowerUpList.add(PowerUpType.BOMB_COUNT);
        enabledPowerUpList.add(PowerUpType.RANGE);
        enabledPowerUpList.add(PowerUpType.HEALTH);
        enabledPowerUpList.add(PowerUpType.SPEED);

        this.localPlayer = newGameEvent.getLocalPlayer();
        this.remotePlayer = newGameEvent.getRemotePlayer();

        this.destroyablePowerUps = true;

        // Initiates view

        timeSinceStart = 0.0;
        timeSinceStart = 0.0;

        timeLimit=10;

//        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());
        ArenaMap arenaMap = new ArenaMap("default",
                (new MapFileReader(FileContainer.getInstance().getFile(FileID.VAPORMAP_DEFAULT))).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap, timeLimit);
        this.arenaView = new ArenaView(root);


        arenaView.initArena(arenaModel.getArenaTiles());
        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), 0, 0);

        this.arenaView.updateStats(
                this.localPlayer.getCharacter().getHealth(),
                this.localPlayer.getCharacter().getSpeed(),
                this.localPlayer.getCharacter().getBombRange(),
                this.localPlayer.getCharacter().getCurrentBombCount(),
                timeSinceStart
        );

        try {
            arenaModel.addMovable(localPlayer.getCharacter());
            arenaModel.addMovable(remotePlayer.getCharacter());
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();

        //Felix code
        Set<GameCharacter> gameCharacters = new HashSet<>();
        gameCharacters.add(localPlayer.getCharacter());
        gameCharacters.add(remotePlayer.getCharacter());

        Enemy felixBot = new Enemy("FelixBot", Utils.gridToCanvasPositionX(5), Utils.gridToCanvasPositionY(5), 0.4, new SemiStupidAI(gameCharacters));
        enemies.add(felixBot);
        //

        Random random = new Random();
        for (int k = 0; k < 10; k++) {
            boolean free;
            Point spawnPosition = new Point(0,0);
            do {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getGridWidth()), random.nextInt(this.arenaModel.getGridHeight()));
                free = (arenaModel.getArenaTile(spawnPosition) == null);
            } while (!free);
            Enemy enemy = new Enemy("PCCHAN "+random.nextInt(), Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y), 0.2, new SemiStupidAI(gameCharacters));
            enemies.add(enemy);
        }

        for(Enemy enemy : enemies) {
            try {
                arenaModel.addMovable(enemy);
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Tile out of bounds!");
            }
        }
        this.healthBarModel=new HealthBarModel((int)this.localPlayer.getCharacter().getHealth());
    }



    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {



        this.timeSinceStart = timeSinceStart;
        if(timeLimit-timeSinceLastCall>0) {
            timeLimit = timeLimit - timeSinceLastCall;
        } else {

            GameEventBus.getInstance().post(new GoToMenuEvent(MenuState.RESULTS_MENU));
        }
        TimerModel.getInstance().updateTimer(timeLimit);

        List<String> input = ListenerController.getInstance().getInput();
        List<String> pressed = ListenerController.getInstance().getPressed();

        if(!gameIsPaused) {

            if (this.updatedEnemyDirection == 15) {
                for (Enemy enemy : enemies) {
                    enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(), localPlayer.getCharacter().getGridPosition(),
                            this.arenaModel.getArenaTiles()), arenaModel.getArenaTiles());
                }
                updatedEnemyDirection = 0;
            }
            updatedEnemyDirection += 1;
        }

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            switch (key) {
                case "UP":
                case "LEFT":
                case "DOWN":
                case "RIGHT":
                    if(!gameIsPaused) {
                        remotePlayer.getCharacter().move(Utils.getDirectionFromString(key), arenaModel.getArenaTiles());
                    }
                    break;
            }

            switch (key) {
                case "W":
                case "A":
                case "S":
                case "D":
                    if(!gameIsPaused) {
                        localPlayer.getCharacter().move(Utils.getDirectionFromString(key), arenaModel.getArenaTiles());
                    }
                    break;
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            StaticTile tile = arenaModel.getArenaTile(this.localPlayer.getCharacter().getGridPosition());
            if (tile == null || (tile instanceof PowerUp)) {
                switch (key) {
                    case "SHIFT":
                        if(!gameIsPaused) {
                            this.localPlayer.getCharacter().placeBomb();
                        }
                        break;
                    case "CAPS":
                        if(!gameIsPaused) {
                            this.localPlayer.getCharacter().placeMine();
                        }
                        break;
                }
            }

            tile = arenaModel.getArenaTile(this.remotePlayer.getCharacter().getGridPosition());
            if (tile == null || (tile instanceof PowerUp)) {
                switch (key) {
                    case "SPACE":
                        if(!gameIsPaused) {
                            this.remotePlayer.getCharacter().placeBomb();
                        }
                        break;
                    case "M":
                        if(!gameIsPaused) {
                            this.remotePlayer.getCharacter().placeMine();
                        }
                        break;
                }
            }


            switch (key) {

                case "ESCAPE":
                    SoundContainer.getInstance().stopSound(SoundID.GAME_MUSIC);
                    this.arenaModel.getArenaMovables().clear();
                    for (int j = 0; j < this.arenaModel.getArenaTiles().length; j++) {
                        for (int k = 0; k < this.arenaModel.getArenaTiles()[0].length; k++) {
                            this.arenaModel.getArenaTiles()[j][k] = null;
                        }
                    }
                    this.enemies.clear();
                    this.deadEnemies.clear();

                    GameEventBus.getInstance().post(new GoToMenuEvent());
                    break;
                case "P":
                    if(gameIsPaused) {
                        arenaView.hidePauseMenu();
                        gameIsPaused = false;
                    } else if(!gameIsPaused) {
                        arenaView.showPauseMenu();
                        gameIsPaused = true;
                    }
                    break;
            }

        }

        // Updating positions (if not paused)
        if(!gameIsPaused) {


            for (Movable movable : arenaModel.getArenaMovables()) {
                movable.updatePosition();

                // If moving and not invincible, check for things that will deal damage
                if (!movable.isInvincible()
                        && (movable.getState() == MovableState.IDLE || movable.getState() == MovableState.WALK)) {

                    // The enemy-check for characters only:
                    if (movable instanceof GameCharacter) {

                        GameCharacter gameCharacter = (GameCharacter) movable;

                        for (Movable otherMovable : arenaModel.getArenaMovables()) {
                            if (otherMovable instanceof Enemy && movable.intersects(otherMovable) && !otherMovable.isInvincible()
                                    && (otherMovable.getState() == MovableState.IDLE || otherMovable.getState() == MovableState.WALK)) {
                                movable.dealDamage(otherMovable.getDamage());
                                updateStats();
                                break;
                            }
                        }

                        // Walking over powerup?
                        if (this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y] instanceof StatPowerUp) {
                            StatPowerUp powerUp = (StatPowerUp) this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y];

                            // If so, pick it up
                            if (powerUp.getPowerUpType() != null && powerUp.getState() == PowerUp.PowerUpState.IDLE) {
                                powerUp.pickUp(timeSinceStart);
                                gameCharacter.pickedUpPowerUp(timeSinceStart);
                                playerWalksOnPowerUp(powerUp.getPowerUpType(), gameCharacter);
                                updateStats();
                            }
                        }
                    }
                }

                // The blast-check:
                StaticTile currentTile = this.arenaModel.getArenaTile(movable.getGridPosition());
                Blast blast = null;
                if (currentTile instanceof Blast) {
                    blast = (Blast) currentTile;
                } else if (currentTile instanceof DoubleTile) {
                    blast = ((DoubleTile) currentTile).getBlast();
                }

                // If blast was found, and the blast still is dangerous, deal damage
                if (blast != null && blast.isDangerous(timeSinceStart)) {
                    movable.dealDamage(blast.getDamage());
                    updateStats();
                }
            }
        }



        // Removes enemies
        if (deadEnemies.size() > 0) {
            for (Enemy enemy : deadEnemies) {
                this.enemies.remove(enemy);
                this.arenaModel.removeMovable(enemy);
            }
            deadEnemies.clear();
        }

        // Model updating, before letting view have at it
        if(!gameIsPaused) {
            this.arenaModel.updateBombs(this.timeSinceStart);
            this.arenaModel.sortMovables();
        }

        this.healthBarModel.updateHealth((int)this.localPlayer.getCharacter().getHealth());

        arenaView.updateHealth((int)this.healthBarModel.getHealth());

        // Calls view to update graphics
        if(!gameIsPaused) {
            arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
        }
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setDoubleTile(new Bomb(placeBombEvent.getCharacter(), placeBombEvent.getRange(), Constants.DEFAULT_BOMB_DELAY, this.timeSinceStart, placeBombEvent.getDamage()), placeBombEvent.getGridPosition());
        this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void minePlaced(PlaceMineEvent placeMineEvent) {
        arenaModel.setTile(new Mine(this.localPlayer.getCharacter(), 1, this.localPlayer.getCharacter().getDamage()), placeMineEvent.getGridPosition());
        this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() - 1);
        updateStats();
    }

    // This method is called via the eventbus, when a gamecharacter calls placeBomb()
    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {

        // This is temporarily; when more than one character, this must be moved outside, or altered
        this.localPlayer.getCharacter().setCurrentBombCount(this.localPlayer.getCharacter().getCurrentBombCount() + 1);

        // A map to keep track if there is an obstacle, or if the fire can keep burning
        Map<Direction, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Direction.LEFT, true);
        blastDirections.put(Direction.UP, true);
        blastDirections.put(Direction.RIGHT, true);
        blastDirections.put(Direction.DOWN, true);

        Explosive explosive = blastEvent.getExplosive();

        // The center is ALLWAYS a blast
        this.arenaModel.setTile(new Blast(explosive, BlastState.CENTER, null, this.timeSinceStart), explosive.getPosition());

        // Next, going through every tile within blast range
        for (int i = 0; i < explosive.getRange(); i++) {
            for (int j = 0; j < 4; j++) {
                Direction direction = Utils.getDirectionFromInteger(j);
                Point position = Utils.getRelativePoint(explosive.getPosition(), i + 1, direction);

                // Checks if inside of game arena
                if (!isValidPosition(position)) {
                    blastDirections.put(direction, false);
                }

                // Sets the state
                BlastState state = BlastState.BEAM;
                if (i == explosive.getRange() - 1) {
                    state = BlastState.END;
                }

                // If the way forward is clear, start placing and modifying blast-tile
                if (blastDirections.get(direction)) {

                    // If there is something in the way, lots of checks are done
                    StaticTile currentTile = this.arenaModel.getArenaTile(position);
                    if (currentTile != null) {

                        // If a destructible wall is in the way, crack it and maybe also add an powerup
                        if (currentTile instanceof DestructibleWall) {
                            ((DestructibleWall)currentTile).destroy(this.timeSinceStart);
                            StatPowerUp statPowerUp = this.arenaModel.spawnStatPowerUp(this.enabledPowerUpList);
                            if (statPowerUp != null) {
                                statPowerUp.setTimeStamp(this.timeSinceStart);
                                StaticTile doubleTile = new DoubleTile(statPowerUp, currentTile);
                                this.arenaModel.setTile(doubleTile, position);
                            }

                        // If a powerup, and destroyable powerups has been enabled in settings, simply destroy it
                        } else if (destroyablePowerUps && currentTile instanceof PowerUp
                                        && ((PowerUp) currentTile).getState() == PowerUp.PowerUpState.IDLE) {
                                ((PowerUp) currentTile).destroy(this.timeSinceStart);

                        // If another explosive, detonate it with a tiny delay (makes for cool effects)
                        } else if (currentTile instanceof Explosive) {
                            ((Explosive)currentTile).setDelay(0.03, this.timeSinceStart);
                        }

                        // Multiple stacking if, 1; a blast end is in the way, 2; if blasts are ortogonal to each other,
                        // or 3: if it's the pickup-animation for powerups
                        if ( (currentTile instanceof Blast && ( ((Blast)currentTile).getState() == BlastState.END
                                || Utils.isOrtogonalDirections(((Blast)currentTile).getDirection(), direction) ) )
                                || (currentTile instanceof DoubleTile && ((DoubleTile)currentTile).containBlast())
                        /* 3 */ || (currentTile instanceof PowerUp && ((PowerUp) currentTile).getState() == PowerUp.PowerUpState.PICKUP ) ) {

                            StaticTile doubleTile = new DoubleTile(currentTile,
                                    new Blast(explosive, state, direction, this.timeSinceStart));
                            this.arenaModel.setTile(doubleTile, position);

                        // If no special multiple stacking, then the way is definitely shut, and blast ends here.
                        } else {
                            blastDirections.put(direction, false);
                        }

                    // If nothing is in the way, simply but a blast there
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
                this.localPlayer.getCharacter().getCurrentBombCount(),
                timeSinceStart
        );
    }



    public ArenaModel newGame(ArenaMap arenaMap, double timeLimit) {
        TimerModel.getInstance().updateTimer(timeLimit);
        // Here goes all code for setting up the environment for a new game
        return new ArenaModel(arenaMap);
    }

    /**
     * Call on this method when player walks on PowerUpTile.
     * Will set the appropriate stat value on the character that walks on it.
     * @param powerUpType
     */
    public void playerWalksOnPowerUp(PowerUpType powerUpType, GameCharacter gameCharacter) {


        switch (powerUpType) {
            case HEALTH:
                if (gameCharacter.getHealth() <= 90) {
                    gameCharacter.setHealth(this.localPlayer.getCharacter().getHealth() + 10);
                } else if (gameCharacter.getHealth() < 100) {
                    gameCharacter.setHealth(100);
                }
                break;
            case BOMB_COUNT:
                gameCharacter.setMaxBombCount(gameCharacter.getMaxBombCount() + 1);
                gameCharacter.setCurrentBombCount(gameCharacter.getCurrentBombCount() + 1);
                break;
            case SPEED:
                gameCharacter.setSpeed(gameCharacter.getSpeed() + 0.2);
                break;
            case RANGE:
                gameCharacter.setBombRange(gameCharacter.getBombRange() + 1);
                break;
        }
    }

    @Subscribe
    public void movableDeath(DeathEvent deathEvent) {
        Movable movable = deathEvent.getMovable();
        if (movable instanceof GameCharacter) {

            //TODO
            movable.spawn(new Point(6, 5));
            updateStats();

        } else if (movable instanceof Enemy) {
            if (!deadEnemies.contains((Enemy)movable)) {
                deadEnemies.add((Enemy)movable);
            }
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

    public boolean isGamePaused() {
        return gameIsPaused;
    }
}
