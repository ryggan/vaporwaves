package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.TimerModel;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.view.Scoreboard;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {
    private Boolean scoreboardIsShowing = false;
    private List<Player> playerList;
    private Scoreboard scoreboard;
    private PauseMenuController pauseMenuController;

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
    private double timeSinceStartOffset;
    private double pausedTime;

    private boolean gameIsPaused = false;

    //seconds
    private double timeLimit;

    // settings for one specific game:
    private boolean destroyablePowerUps;

    public GameController(Group root) {
        GameEventBus.getInstance().register(this);
    }

    public void initGame(Group root, NewGameEvent newGameEvent) {

        Container.playSound(SoundID.GAME_MUSIC);

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
        timeSinceStartOffset = 0.0;
        pausedTime = 0.0;

        timeLimit = 10;

        ArenaMap arenaMap = new ArenaMap("default",
                (new MapFileReader(Container.getFile(FileID.VAPORMAP_DEFAULT))).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap, timeLimit);
        this.arenaView = new ArenaView(root);
        this.pauseMenuController = new PauseMenuController(root);

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

        Enemy felixBot = new Enemy("FelixBot", Utils.gridToCanvasPositionX(5), Utils.gridToCanvasPositionY(5), 0.4, new FelixTestAI(gameCharacters));
        enemies.add(felixBot);
        //

        Random random = new Random();
/*        for (int k = 0; k < 10; k++) {
            boolean free;
            Point spawnPosition = new Point(0,0);
            do {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getGridWidth()), random.nextInt(this.arenaModel.getGridHeight()));
                free = (arenaModel.getArenaTile(spawnPosition) == null);
            } while (!free);
            Enemy enemy = new Enemy("PCCHAN "+random.nextInt(), Utils.gridToCanvasPositionX(spawnPosition.x), Utils.gridToCanvasPositionY(spawnPosition.y), 0.2, new SemiStupidAI(gameCharacters));
            enemies.add(enemy);
        }*/

        for(Enemy enemy : enemies) {
            try {
                arenaModel.addMovable(enemy);
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Tile out of bounds!");
            }
        }
        this.healthBarModel = new HealthBarModel((int)this.localPlayer.getCharacter().getHealth());

        // // TODO: 11/05/16 fix this to some other class, probably arenaView

        playerList = new ArrayList<>();
        playerList.add(localPlayer);
        playerList.add(remotePlayer);
        this.scoreboard = new Scoreboard(root, playerList);

    }


    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        if (!gameIsPaused) {
            this.timeSinceStart = timeSinceStart - this.timeSinceStartOffset;
        }

        if(!TimerModel.getInstance().isPaused()) {
            if (timeLimit - timeSinceLastCall > 0) {
                timeLimit = timeLimit - timeSinceLastCall;
            } else {

                // todo: Change this to some other kind of event
//                GameEventBus.getInstance().post(new GoToMenuEvent(MenuState.RESULTS_MENU));
            }
            TimerModel.getInstance().updateTimer(timeLimit);
        }

        List<String> pressed = ListenerController.getInstance().getPressed();

        if(!gameIsPaused) {

            TimerModel.getInstance().setPaused(false);

            if (this.updatedEnemyDirection == 15) {
                for (Enemy enemy : enemies) {
                    enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(), localPlayer.getCharacter().getGridPosition(),
                            this.arenaModel.getArenaTiles()), arenaModel.getArenaTiles());
                }
                updatedEnemyDirection = 0;
            }
            updatedEnemyDirection += 1;
        } else {
            TimerModel.getInstance().setPaused(true);
        }

        // All player-specific input and pressed etc.
        if (!gameIsPaused) {
            for (Player player : this.playerList) {
                playerInputAction(player);
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "ESCAPE":
                    Container.stopSound(SoundID.GAME_MUSIC);
                    this.arenaModel.getArenaMovables().clear();
                    for (int j = 0; j < this.arenaModel.getArenaTiles().length; j++) {
                        for (int k = 0; k < this.arenaModel.getArenaTiles()[0].length; k++) {
                            this.arenaModel.getArenaTiles()[j][k] = null;
                        }
                    }
                    this.enemies.clear();
                    this.deadEnemies.clear();

                    GameEventBus.getInstance().post(new GoToMenuEvent(MenuState.START_MENU));
                    break;
                case "P":
                    if(gameIsPaused) {
                        unpauseGame();
                    } else {
                        pauseGame();
                    }
                    break;
            }
        }

        // Updating positions (if not paused)
        if(!gameIsPaused) {

            for (Movable movable : arenaModel.getArenaMovables()) {
                movable.updatePosition();

                // If moving and not invincible, check for things that will deal damage
                if (movable.getState() == MovableState.IDLE || movable.getState() == MovableState.WALK) {

                    // The enemy-check for characters only:
                    if (movable instanceof GameCharacter) {

                        GameCharacter gameCharacter = (GameCharacter) movable;

                        if (!movable.isInvincible()) {
                            for (Movable otherMovable : arenaModel.getArenaMovables()) {
                                if (otherMovable instanceof Enemy && movable.intersects(otherMovable) && !otherMovable.isInvincible()
                                        && (otherMovable.getState() == MovableState.IDLE || otherMovable.getState() == MovableState.WALK)) {
                                    movable.dealDamage(otherMovable.getDamage());
                                    updateStats();
                                    if (movable.getHealth() <= 0) {
                                        playerList.get(((GameCharacter) movable).getPlayerId()).incrementDeaths();
                                    }
                                    break;
                                }
                            }
                        }

                        // Walking over powerup?
                        if (this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y] instanceof StatPowerUp) {
                            StatPowerUp powerUp = (StatPowerUp) this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y];

                            // If so, pick it up
                            if (powerUp.getPowerUpType() != null && powerUp.getState() == PowerUp.PowerUpState.IDLE) {
                                powerUp.pickUp(this.timeSinceStart);
                                gameCharacter.pickedUpPowerUp(this.timeSinceStart);
                                playerWalksOnPowerUp(powerUp.getPowerUpType(), gameCharacter);
                                updateStats();
                                playerList.get(((GameCharacter) movable).getPlayerId()).incrementPowerUpScore();
                            }
                        }
                    }

                    // The blast-check:
                    if (!movable.isInvincible()) {
                        StaticTile currentTile = this.arenaModel.getArenaTile(movable.getGridPosition());
                        Blast blast = null;
                        if (currentTile instanceof Blast) {
                            blast = (Blast) currentTile;
                        } else if (currentTile instanceof DoubleTile) {
                            blast = ((DoubleTile) currentTile).getBlast();
                        }

                        // If blast was found, and the blast still is dangerous, deal damage
                        if (blast != null && blast.isDangerous(this.timeSinceStart)) {
                            movable.dealDamage(blast.getDamage());
                            updateStats();
                            if (movable.getHealth() <= 0) {
                                if (movable instanceof GameCharacter) {
                                    if (blast.getPlayerId() != ((GameCharacter) movable).getPlayerId()) {
                                        playerList.get(blast.getPlayerId()).incrementKills();
                                    }
                                    playerList.get(((GameCharacter) movable).getPlayerId()).incrementDeaths();
                                } else if (movable instanceof Enemy) {
                                    playerList.get(blast.getPlayerId()).incrementCreeps();
                                }
                            }
                        }
                    }
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
            arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), this.timeSinceStart, timeSinceLastCall);
        }


        if (ListenerController.getInstance().getInput().contains("TAB")) {
            if(!scoreboardIsShowing) {
                scoreboard.showScoreboard();
                scoreboardIsShowing = true;
            }
        } else {
            scoreboard.hideScoreboard();
            scoreboardIsShowing = false;
        }
    }

    private void playerInputAction(Player player) {
        List<String> allInput = ListenerController.getInstance().getAllInput(player);
        for (int i = 0; i < allInput.size(); i++) {
            String key = allInput.get(i);
            if (key.equals(player.getDirectionControls()[0]) || key.equals(player.getDirectionControls()[1])
                    || key.equals(player.getDirectionControls()[2]) || key.equals(player.getDirectionControls()[3])
                    || key.equals("LS_UP") || key.equals("LS_LEFT")|| key.equals("LS_DOWN")|| key.equals("LS_RIGHT")
                    || key.equals("DPAD_UP") || key.equals("DPAD_LEFT")|| key.equals("DPAD_DOWN")|| key.equals("DPAD_RIGHT")) {

                player.getCharacter().move(Utils.getDirectionFromString(key), arenaModel.getArenaTiles());
            }
        }

        List<String> allPressed = ListenerController.getInstance().getAllPressed(player);
        for (int i = 0; i < allPressed.size(); i++) {
            String key = allPressed.get(i);
            StaticTile tile = arenaModel.getArenaTile(player.getCharacter().getGridPosition());
            if (tile == null || (tile instanceof PowerUp)) {
                if (key.equals(player.getBombControl()) || key.equals("BTN_A")) {
                    player.getCharacter().placeBomb();
                } else if (key.equals(player.getMineControl()) || key.equals("BTN_B")) {
                    player.getCharacter().placeMine();
                }
            }
        }
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        GameCharacter character = placeBombEvent.getCharacter();
        arenaModel.setDoubleTile(new Bomb(character, placeBombEvent.getRange(), Constants.DEFAULT_BOMB_DELAY,
                this.timeSinceStart, placeBombEvent.getDamage()), placeBombEvent.getGridPosition());
        placeBombEvent.getCharacter().setCurrentBombCount(character.getCurrentBombCount() - 1);
        updateStats();
    }

    @Subscribe
    public void minePlaced(PlaceMineEvent placeMineEvent) {
        GameCharacter character = placeMineEvent.getCharacter();
        arenaModel.setTile(new Mine(character, placeMineEvent.getRange(), placeMineEvent.getDamage()), placeMineEvent.getGridPosition());
        character.setCurrentBombCount(character.getCurrentBombCount() - 1);
        updateStats();
    }

    // This method is called via the eventbus, when a gamecharacter calls placeBomb()
    @Subscribe
    public void bombDetonated(BlastEvent blastEvent) {

        GameCharacter owner = blastEvent.getExplosive().getOwner();
        owner.setCurrentBombCount(owner.getCurrentBombCount() + 1);

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

            // null => gameCharacter uses it's inherent startPosition
            movable.spawn(null);
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


        }
//        else if (movable instanceof Enemy) {
//
//        }
    }

    public boolean isGamePaused() {
        return gameIsPaused;
    }

    private void pauseGame() {
        this.pauseMenuController.getPauseMenuView().show();
        gameIsPaused = true;
        this.pausedTime = System.nanoTime();

//        System.out.println("Paused time: "+this.pausedTime);
    }

    private void unpauseGame() {
        this.pauseMenuController.getPauseMenuView().hide();
        gameIsPaused = false;
        this.timeSinceStartOffset += (System.nanoTime() - this.pausedTime) / 1000000000.0;

//        System.out.println("Time since start: "+this.timeSinceStart+", time since start offset: "+this.timeSinceStartOffset);
    }
}
