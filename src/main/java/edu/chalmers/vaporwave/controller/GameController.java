package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.event.DeathEvent;
import edu.chalmers.vaporwave.event.ExitToMenuEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.SpawnEvent;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.CPUPlayer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.model.menu.NewGameEvent;
import edu.chalmers.vaporwave.util.*;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This controller keeps the whole game going, via updateTimer method, which is
 * called from MainController, where the AnimationLoop is.
 */
public class GameController implements ContentController {

    private enum GameState {
        PRE_GAME, GAME_RUNS, GAME_PAUSED, GAME_OVER
    }

    private PauseMenuController pauseMenuController;

    private ArenaView arenaView;
    private ArenaModel arenaModel;
    private Set<Player> players;

    private Set<Enemy> enemies;
    private Set<Enemy> deadEnemies;
    private List<PowerUpType> enabledPowerUpList;

    private int updatedEnemyDirection;

    private double timeSinceStart;
    private double timeSinceStartOffset;
    private double pausedTime;
    private double gameEndTimer;

    private GameType gameType;
    private int killLimit;
    private int scoreLimit;

    private GameState gameState;

    private double timer;

    // settings for one specific game:
    private boolean destroyablePowerUps;
    private boolean respawnPowerups;

    public GameController(Group root) throws Exception {
        GameEventBus.getInstance().register(this);

        this.arenaView = new ArenaView(root);
    }

    public void initGame(Group root, NewGameEvent newGameEvent) throws Exception {

        Container.playSound(SoundID.GAME_MUSIC);

        enabledPowerUpList = new ArrayList<>();
        enabledPowerUpList.add(PowerUpType.BOMB_COUNT);
        enabledPowerUpList.add(PowerUpType.RANGE);
        enabledPowerUpList.add(PowerUpType.HEALTH);
        enabledPowerUpList.add(PowerUpType.SPEED);
//        enabledPowerUpList.add(PowerUpType.FISH);

        // Getting settings/content from newGameEvent
        this.gameType = newGameEvent.getGameType();
        this.timer = newGameEvent.getTimeLimit();
        this.killLimit = newGameEvent.getKillLimit();
        this.scoreLimit = newGameEvent.getScoreLimit();
        this.destroyablePowerUps = newGameEvent.getDestroyablePowerups();
        this.respawnPowerups = newGameEvent.getRespawnPowerups();
        this.players = newGameEvent.getPlayers();
        ArenaMap arenaMap = newGameEvent.getArenaMap();

                // Initiating variables
        this.timeSinceStart = 0.0;
        this.timeSinceStartOffset = 0.0;
        this.pausedTime = 0.0;
        this.gameEndTimer = 3.0;
        this.gameState = GameState.PRE_GAME;

        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();

                // Setting up model objects and view objects
        this.arenaModel = newGame(arenaMap);
        this.pauseMenuController = new PauseMenuController(root);

        this.arenaView.initArena(this.arenaModel.getArenaTiles());
        this.arenaView.initHUDandScoreboard(this.players);
        this.arenaView.updateView(this.arenaModel.getArenaMovables(), this.arenaModel.getArenaTiles(), this.players, 0, 0);

        try {
            for (Player player : this.players) {
                this.arenaModel.addMovable(player.getCharacter());
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        initGameCharacters(arenaMap);

        initEnemies();

        // This must be done after both Enemy and GameCharacter initialization,
        // for correct depth positioning when rendering
        this.arenaModel.sortMovables();
    }

    private void initGameCharacters(ArenaMap arenaMap) {
        // Separate list for only GameCharacters
        Set<GameCharacter> gameCharacters = new HashSet<>();
        for (Player player : this.players) {
            player.getCharacter().setPlayerID(player.getPlayerID());
            gameCharacters.add(player.getCharacter());
        }

        // Setting spawnpositions
        for (Player player : this.players) {
            player.getCharacter().setSpawnPosition(arenaMap.getSpawnPosition(Utils.getMapObjectPlayerFromID(player.getPlayerID())));
            player.getCharacter().spawn(arenaMap.getSpawnPosition(Utils.getMapObjectPlayerFromID(player.getPlayerID())));

            // Also adding AI, if CPU
            if (player instanceof CPUPlayer) {
                Set<GameCharacter> temporaryCharacterSet = cloneGameCharacterSet(gameCharacters);
                temporaryCharacterSet.remove(player.getCharacter());
                ((CPUPlayer)player).setPlayerAI(new SemiSmartCPUAI(temporaryCharacterSet));
            }
        }
    }

    private void initEnemies() {
        Set<GameCharacter> gameCharacters = new HashSet<>();
        for (Player player : this.players) {
            gameCharacters.add(player.getCharacter());
        }

        // Creating enemies
        Random random = new Random();
        for (int k = 0; k < 6; k++) {
            boolean free;
            Point spawnPosition = new Point(0,0);
            do {
                spawnPosition.setLocation(random.nextInt(this.arenaModel.getGridWidth()), random.nextInt(this.arenaModel.getGridHeight()));
                free = (this.arenaModel.getArenaTile(spawnPosition) == null && !isNearCharacter(spawnPosition));
            } while (!free);
            Enemy enemy = new Enemy("PCCHAN "+random.nextInt(), Utils.gridToCanvasPositionX(spawnPosition.x),
                    Utils.gridToCanvasPositionY(spawnPosition.y), 0.6, new SemiSmartAI(gameCharacters));
            this.enemies.add(enemy);
        }

        // Adding enemies
        for(Enemy enemy : this.enemies) {
            try {
                this.arenaModel.addMovable(enemy);
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Tile out of bounds!");
            }
        }
    }

    private boolean isNearCharacter(Point position) {
        int radius = 4;
        for (Player player : this.players) {
            Point characterPosition = player.getCharacter().getGridPosition();
            if (Math.abs(characterPosition.getX() - position.getX()) < radius
                    && Math.abs(characterPosition.getY() - position.getY()) < radius) {
                return true;
            }
        }
        return false;
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) throws Exception {

        if (this.gameState != GameState.GAME_PAUSED) {
            updateTimeAndState(timeSinceStart, timeSinceLastCall);
        }

        updateGeneralInputs();

        if (this.gameState == GameState.GAME_RUNS) {

            // Player/CPU player inpout and movement etc
            for (Player player : this.players) {
                if (player instanceof CPUPlayer) {
                    updateCPUPlayerInputAction(player);
                } else {
                    updatePlayerInputAction(player);
                    updatePlayerPressedAction(player);
                }
            }

            // Enemy movement etc
            updateEnemyMovement();
        }

        // Updating positions (if not paused)
        if(this.gameState == GameState.GAME_RUNS || this.gameState == GameState.PRE_GAME) {
            updateMovablePositions();
        }

        // Model updating, before letting view have at it
        if(this.gameState == GameState.GAME_RUNS) {
            this.arenaModel.updateBombs(this.timeSinceStart);
            this.arenaModel.sortMovables();
        }

        // Calls view to update graphics
        if(this.gameState == GameState.GAME_RUNS || this.gameState == GameState.PRE_GAME) {
            this.arenaView.updateView(this.arenaModel.getArenaMovables(), this.arenaModel.getArenaTiles(), this.players,
                    this.timeSinceStart, timeSinceLastCall);
            this.arenaView.updateTimer(this.timer);
        }

        // Show scoreboard when holding tab
        this.arenaView.showScoreboard(this.gameState == GameState.GAME_RUNS
                && InputController.getInstance().getInput().contains("TAB"));

        // Check if end game!
        for (Player player : this.players) {
            if (this.gameType == GameType.SCORE_LIMIT && player.getScore() >= this.scoreLimit) {
                gameOverStart("SCORE ACCUMULATED!");
            }
        }

        // Removes dead enemies from game
        clearDeadEnemies();
    }

    private void updateTimeAndState(double timeSinceStart, double timeSinceLastCall) {
        this.timeSinceStart = timeSinceStart - this.timeSinceStartOffset;

        if (this.gameState == GameState.GAME_RUNS) {
            if (this.timer - timeSinceLastCall > 0) {
                this.timer -= timeSinceLastCall;
            } else {
                this.timer = 0;
                this.arenaView.updateTimer(this.timer);
                gameOverStart("TIME IS UP!");
            }
        } else if (this.gameState == GameState.GAME_OVER) {
            if (this.gameEndTimer- timeSinceLastCall > 0) {
                this.gameEndTimer -= timeSinceLastCall;
            } else {
                gameOverDone();
            }
        }
    }

    private void updateGeneralInputs() {
        List<String> pressed = InputController.getInstance().getPressed();

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "ESCAPE":
                    if (this.gameState == GameState.GAME_RUNS || this.gameState == GameState.GAME_PAUSED) {
                        exitGame(MenuState.START_MENU);
                    }
                    break;
                case "P":
                    if (this.gameState == GameState.GAME_PAUSED) {
                        unPauseGame();
                    } else if (this.gameState == GameState.GAME_RUNS) {
                        pauseGame();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void updatePlayerInputAction(Player player) {
        List<String> allInput = InputController.getInstance().getAllInput(player);
        for (int i = 0; i < allInput.size(); i++) {
            String key = allInput.get(i);
            if (key.equals(player.getDirectionControls()[0]) || key.equals(player.getDirectionControls()[1])
                    || key.equals(player.getDirectionControls()[2]) || key.equals(player.getDirectionControls()[3])
                    || key.equals("LS_UP") || key.equals("LS_LEFT")|| key.equals("LS_DOWN")|| key.equals("LS_RIGHT")
                    || key.equals("DPAD_UP") || key.equals("DPAD_LEFT")|| key.equals("DPAD_DOWN")|| key.equals("DPAD_RIGHT")) {
                player.getCharacter().move(Utils.getDirectionFromString(key), this.arenaModel.getArenaTiles());
            }
        }
    }

    private void updatePlayerPressedAction(Player player) {
        List<String> allPressed = InputController.getInstance().getAllPressed(player);
        for (int i = 0; i < allPressed.size(); i++) {
            String key = allPressed.get(i);
            if (key.equals(player.getBombControl()) || key.equals("BTN_A")) {
                checkAndPlaceBomb(player);
            }
        }
    }

    private void updateCPUPlayerInputAction(Player player) {
        if (player instanceof CPUPlayer) {
            CPUPlayer cpuPlayer = (CPUPlayer) player;
            if (cpuPlayer.getPlayerAI().shouldPutBomb() && cpuPlayer.getCharacter().getState() == MovableState.IDLE) {
                checkAndPlaceBomb(cpuPlayer);
            }
            if (cpuPlayer.getCharacter().getState() == MovableState.IDLE) {
                cpuPlayer.getCharacter().move(cpuPlayer.getPlayerAI().getNextMove(cpuPlayer.getCharacter().getGridPosition(),
                        this.arenaModel.getArenaTiles(), this.enemies), this.arenaModel.getArenaTiles());
            }
        }
    }

    private void updateEnemyMovement() {
        if (this.updatedEnemyDirection == Constants.ENEMY_UPDATE_RATE) {
            for (Enemy enemy : this.enemies) {
                if(enemy.getState() == MovableState.IDLE) {
                    enemy.move(enemy.getAI().getNextMove(enemy.getGridPosition(),
                            this.arenaModel.getArenaTiles(), this.enemies), this.arenaModel.getArenaTiles());
                }
            }
            this.updatedEnemyDirection = 0;
        }
        this.updatedEnemyDirection += 1;
    }

    private void updateMovablePositions() {
        for (Movable movable : this.arenaModel.getArenaMovables()) {
            movable.updatePosition();

            // If moving, check for things that can be walked over
            if (movable.getState() == MovableState.IDLE || movable.getState() == MovableState.WALK) {

                // GameCharacter only
                if (movable instanceof GameCharacter) {
                    updateMovableEnemyCheck(movable);
                    updateMovablePowerupCheck(movable);
                }

                // The blast-check:
                if (!movable.isInvincible()) {
                    updateMovableBlastCheck(movable);
                }
            }
        }
    }

    private void updateMovableEnemyCheck(Movable movable) {
        GameCharacter gameCharacter = (GameCharacter) movable;

        if (!movable.isInvincible()) {
            for (Movable otherMovable : this.arenaModel.getArenaMovables()) {
                if (otherMovable instanceof Enemy && movable.intersects(otherMovable) && !otherMovable.isInvincible()
                        && (otherMovable.getState() == MovableState.IDLE || otherMovable.getState() == MovableState.WALK)) {
                    movable.dealDamage(otherMovable.getDamage());
                    if (movable.getHealth() <= 0) {
                        getPlayerForGameCharacter(gameCharacter).incrementDeaths();
                    }
                    break;
                }
            }
        }
    }

    private void updateMovablePowerupCheck(Movable movable) {
        GameCharacter gameCharacter = (GameCharacter) movable;

//        java.lang.ArrayIndexOutOfBoundsException: -1
//        at edu.chalmers.vaporwave.controller.GameController.updateMovablePowerupCheck(GameController.java:382)

        try {
            if (this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y] instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) this.arenaModel.getArenaTiles()[gameCharacter.getGridPosition().x][gameCharacter.getGridPosition().y];

                // If so, pick it up
                if (powerUp.getPowerUpType() != null && (powerUp.getState() == PowerUp.PowerUpState.IDLE
                        || powerUp.getState() == PowerUp.PowerUpState.SPAWN)) {
                    powerUp.pickUp(this.timeSinceStart);
                    playerWalksOnPowerUp(powerUp.getPowerUpType(), gameCharacter);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(gameCharacter.getName()+" - x: "+gameCharacter.getGridPosition().x+", y: "+gameCharacter.getGridPosition().y);
        }
    }

    private void updateMovableBlastCheck(Movable movable) {
        // Check for blast
        StaticTile currentTile = this.arenaModel.getArenaTile(movable.getGridPosition());
        Blast blast = null;
        if (currentTile instanceof Blast) {
            blast = (Blast) currentTile;
        } else if (currentTile instanceof DoubleTile) {
            blast = (Blast)((DoubleTile) currentTile).getTile(Blast.class);
        }

        // If blast was found, and the blast still is dangerous, deal damage
        if (blast != null && blast.isDangerous(this.timeSinceStart)) {
            movable.dealDamage(blast.getDamage());

            // Kill movable and deal kill-points, when health reaches zero
            if (movable.getHealth() <= 0) {
                if (movable instanceof GameCharacter) {
                    if (blast.getPlayerID() != ((GameCharacter) movable).getPlayerID()) {
                        getPlayerForID(blast.getPlayerID()).incrementKills();
                    }
                    getPlayerForGameCharacter((GameCharacter)movable).incrementDeaths();
                } else if (movable instanceof Enemy && getPlayerForID(blast.getPlayerID()) != null) {
                    getPlayerForID(blast.getPlayerID()).incrementCreeps();
                }

            // Deal damage-points, if movable didn't die
            } else {
                if (movable instanceof GameCharacter && blast.getPlayerID() != ((GameCharacter) movable).getPlayerID()) {
                    getPlayerForID(blast.getPlayerID()).damagedCharacterScore();
                } else if (movable instanceof Enemy) {
                    getPlayerForID(blast.getPlayerID()).damagedEnemyScore();
                }
            }
        }
    }

    private void clearDeadEnemies() {
        if (this.deadEnemies.size() > 0) {
            for (Enemy enemy : this.deadEnemies) {
                this.enemies.remove(enemy);
                this.arenaModel.getArenaMovables().remove(enemy);
            }
            this.deadEnemies.clear();
        }
    }

    private void checkAndPlaceBomb(Player player) {
        StaticTile tile = this.arenaModel.getArenaTile(player.getCharacter().getGridPosition());
        if (tile == null || (tile instanceof PowerUp && ((PowerUp) tile).getState() == PowerUp.PowerUpState.PICKUP)) {
            player.getCharacter().placeBomb();
        }
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        GameCharacter character = placeBombEvent.getCharacter();
        this.arenaModel.setDoubleTile(new Bomb(character, placeBombEvent.getRange(), Constants.DEFAULT_BOMB_DELAY,
                this.timeSinceStart, placeBombEvent.getDamage()), placeBombEvent.getGridPosition());
    }

    // This method is called via the eventbus, when a placed bomb's timer reaches zero,
    // and deals with creating blast tiles of correct type everywhere the blast should reach
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

        // The center is ALWAYS a blast
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

                        // Checking for stuff to destory
                        bombDetonatedDestroy(position, currentTile);

                        // Checking if blast should stack on other blasts, or stop
                        bombDetonatedStackingStop(position, currentTile, direction, explosive, state, blastDirections);

                        // If nothing is in the way, simply but a blast there
                    } else {
                        this.arenaModel.setTile(new Blast(explosive, state, direction, this.timeSinceStart), position);
                    }
                }
            }
        }
    }

    private void bombDetonatedDestroy(Point position, StaticTile currentTile) {
        // If a destructible wall is in the way, crack it and maybe also add an powerup
        if (currentTile instanceof DestructibleWall) {
            bombDetonatedDestroyWall(position, currentTile);

            // If a powerup, and destroyable powerups has been enabled in settings, simply destroy it
        } else if (this.destroyablePowerUps && currentTile instanceof PowerUp
                && ((PowerUp) currentTile).getState() == PowerUp.PowerUpState.IDLE) {
            ((PowerUp) currentTile).destroy(this.timeSinceStart);

            // If another explosive, detonate it with a tiny delay (makes for cool effects)
        } else if (currentTile instanceof Explosive) {
            ((Explosive)currentTile).setDelay(0.03, this.timeSinceStart);
        }
    }

    private void bombDetonatedStackingStop(Point position, StaticTile currentTile, Direction direction,
                                           Explosive explosive, BlastState state, Map<Direction, Boolean> blastDirections) {
        // Multiple stacking if, 1; a blast end is in the way, 2; if blasts are ortogonal to each other,
        // or 3: if it's the pickup-animation for powerups
        if ( (currentTile instanceof Blast && ( ((Blast)currentTile).getState() == BlastState.END
                || Utils.isOrtogonalDirections(((Blast)currentTile).getDirection(), direction) ) )
                || (currentTile instanceof DoubleTile && ((DoubleTile)currentTile).contains(Blast.class))
                        /* 3 */ || (currentTile instanceof PowerUp && ((PowerUp) currentTile).getState() == PowerUp.PowerUpState.PICKUP ) ) {

            StaticTile doubleTile = new DoubleTile(currentTile, new Blast(explosive, state, direction, this.timeSinceStart));
            this.arenaModel.setTile(doubleTile, position);

        // If no special multiple stacking, then the way is definitely shut, and blast ends here.
        } else {
            blastDirections.put(direction, false);
        }
    }

    private void bombDetonatedDestroyWall(Point position, StaticTile currentTile) {
        ((DestructibleWall)currentTile).destroy(this.timeSinceStart);
        PowerUp statPowerUp = this.arenaModel.spawnPowerUp(this.enabledPowerUpList);
        if (statPowerUp != null) {
            statPowerUp.setTimeStamp(this.timeSinceStart);
            StaticTile doubleTile = new DoubleTile(statPowerUp, currentTile);
            this.arenaModel.setTile(doubleTile, position);
        }
    }

    // Check if a specific position is within the bounds of the ArenaModel.
    private boolean isValidPosition(Point position) {
        return position.x >= 0 &&
                position.y >= 0 &&
                position.x < this.arenaModel.getArenaTiles().length &&
                position.y < this.arenaModel.getArenaTiles()[0].length;
    }

    public ArenaModel newGame(ArenaMap arenaMap) {
        // Here goes all code for setting up the environment for a new game
        return new ArenaModel(arenaMap);
    }

    // Call on this method when player walks on PowerUpTile.
    // Will set the appropriate stat value on the character that walks on it.
    public void playerWalksOnPowerUp(PowerUpType powerUpType, GameCharacter gameCharacter) {
        gameCharacter.pickedUpPowerUp(powerUpType, this.timeSinceStart);
        getPlayerForGameCharacter(gameCharacter).incrementPowerUpScore();

        switch (powerUpType) {
            case HEALTH:
                gameCharacter.setHealth(Math.min(gameCharacter.getHealth() + 10, 100));
                break;
            case BOMB_COUNT:
                gameCharacter.setMaxBombCount(gameCharacter.getMaxBombCount() + 1);
                gameCharacter.setCurrentBombCount(gameCharacter.getCurrentBombCount() + 1);
                break;
            case SPEED:
                double gain = Constants.DEFAULT_POWERUP_SPEED_GAIN;
                if (gameCharacter.getSpeed() < gain * 15) {
                    gameCharacter.setSpeed(Math.round((gameCharacter.getSpeed() + gain) * 100.0) / 100.0);
                }
                break;
            case RANGE:
                gameCharacter.setBombRange(gameCharacter.getBombRange() + 1);
                break;
            case FISH:
                System.out.println("WE GOTS A FISH SIR");
                break;
        }
    }

    public void respawnPowerups(List<PowerUpType> powerups) {
        for (PowerUpType powerUpType : powerups) {

            PowerUp powerUp = new PowerUp(powerUpType);
            powerUp.setTimeStamp(this.timeSinceStart);

            Random generator = new Random();
            int gridPositionX, gridPositionY;
            do {
                gridPositionX = generator.nextInt(Constants.DEFAULT_GRID_WIDTH);
                gridPositionY = generator.nextInt(Constants.DEFAULT_GRID_HEIGHT);
            } while (this.arenaModel.getArenaTile(new Point(gridPositionX, gridPositionY)) != null);

            this.arenaModel.setTile(powerUp, gridPositionX, gridPositionY);
        }
    }

    @Subscribe
    public void movableDeath(DeathEvent deathEvent) {
        Movable movable = deathEvent.getMovable();
        if (movable instanceof GameCharacter) {

            // Respawning powerups:
            if (this.respawnPowerups) {
                List<PowerUpType> powerups = new ArrayList<>();
                for (Pair<PowerUpType, Double> pair : ((GameCharacter) movable).getPowerUpPickedUp()) {
                    powerups.add(pair.getFirst());
                }
                respawnPowerups(powerups);
            }

            // null => gameCharacter uses it's inherent startPosition
            movable.spawn(null);

            for (Player player : this.players) {
                if (this.gameType == GameType.CHARACTER_KILLS && player.getKills() >= this.killLimit) {
                    gameOverStart("PLAYERS KILLED!");
                }
            }

        } else if (movable instanceof Enemy) {

            for (Player player : this.players) {
                if (this.gameType == GameType.ENEMY_KILLS && player.getCreeps() >= this.killLimit) {
                    gameOverStart("ENEMIES KILLED!");
                }
            }

            Enemy enemy = (Enemy) movable;
            if (!this.deadEnemies.contains(enemy)) {
                this.deadEnemies.add(enemy);
            }
        }
    }

    @Subscribe
    public void movableSpawn(SpawnEvent spawnEvent) {
        Movable movable = spawnEvent.getMovable();
        if (movable instanceof GameCharacter) {
            movable.idle();

            if(this.gameState==GameState.PRE_GAME) {
//                Container.playSound(SoundID.START_GAME);
//                Container.playSound(SoundID.GAME_MUSIC);
                this.gameState = GameState.GAME_RUNS;
            }
        }
    }

    public GameState getGameState() {
        return this.gameState;
    }

    private void pauseGame() {
        this.pauseMenuController.getPauseMenuView().show();
        this.gameState = GameState.GAME_PAUSED;
        this.pausedTime = System.nanoTime();
    }

    private void unPauseGame() {
        this.pauseMenuController.getPauseMenuView().hide();
        this.gameState = GameState.GAME_RUNS;
        this.timeSinceStartOffset += (System.nanoTime() - this.pausedTime) / 1000000000.0;
    }

    private Player getPlayerForGameCharacter(GameCharacter gameCharacter) {
        for (Player player : this.players) {
            if (player.getCharacter().equals(gameCharacter)) {
                return player;
            }
        }
        return null;
    }

    private Player getPlayerForID(int id) {
        for (Player player : this.players) {
            if (player.getPlayerID() == id) {
                return player;
            }
        }
        return null;
    }

    private void gameOverStart(String message) {
        this.gameState = GameState.GAME_OVER;
        Container.stopSound(SoundID.GAME_MUSIC);
        Container.playSound(SoundID.TIME_UP);
        Container.playSound(SoundID.MENU_BGM_1);
        this.arenaView.showGameOverMessage(message);
    }

    private void gameOverDone() {
        exitGame(MenuState.RESULTS_MENU);
    }

    private void exitGame(MenuState destinationMenu) {
        Container.stopSound(SoundID.GAME_MUSIC);
        this.arenaModel.getArenaMovables().clear();
        this.arenaModel.clearTiles();
        this.enemies.clear();

        GameEventBus.getInstance().post(new ExitToMenuEvent(destinationMenu, this.players, this.gameType));
    }

    public Set<GameCharacter> cloneGameCharacterSet(Set<GameCharacter> gameChars) {
        Set<GameCharacter> clonedGameCharacterSet = new HashSet<>();
        for(GameCharacter gameCharacter : gameChars) {
            clonedGameCharacterSet.add(gameCharacter);
        }
        return clonedGameCharacterSet;
    }
}