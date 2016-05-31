package edu.chalmers.vaporwave.view;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.AnimationFinishedEvent;
import edu.chalmers.vaporwave.event.DeathEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.SpawnEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The big game view. This class is responsible for rendering everything on screen,
 * when in a game. It is called from GameController, which gives it all the info it needs
 * to start rendering; this class is completely passive in this sense, except for a couple
 * of events.
 */
public class ArenaView {

    public enum Compass {
        NORTH, WEST, EAST, SOUTH
    }

    private static final double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
    private static final double yoffset = 14;

    private Canvas backgroundCanvas;
    private Canvas frameCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext frameGC;
    private GraphicsContext tileGC;

    private ImageView backgroundPattern;

    private HUDView hudView;
    private ScoreboardView scoreboardView;

    private Sprite arenaBackgroundSprite;
    private Map<Compass, Sprite> arenaFrameSprites;

    private CharacterSprite[] characterSprites = new CharacterSprite[4];
    private Sprite characterSparkleSprite;
    private CharacterSprite enemySprite;
    private Sprite[] bombSprite = new Sprite[4];

    private Sprite destructibleWallSprite;
    private Sprite destructibleWallDestroyedSprite;
    private Sprite indestructibleWallSprite;

    private Map<PowerUpType, Sprite> powerUpSprites;
    private Map<PowerUpType, Sprite> powerUpSpawnSprites;
    private Map<PowerUpType, Sprite> powerUpPickupSprites;
    private Map<PowerUpType, Sprite> powerUpDestroySprites;

    private Sprite blastSpriteCenter;
    private Sprite[] blastSpriteBeam = new Sprite[4];
    private Sprite[] blastSpriteEnd = new Sprite[4];

    private Group root;

    private Label fps;

    private int updateCounter;

    public ArenaView(Group root) {

        this.root = root;

        this.powerUpSprites = new HashMap<>();
        this.powerUpSpawnSprites = new HashMap<>();
        this.powerUpPickupSprites = new HashMap<>();
        this.powerUpDestroySprites = new HashMap<>();

        this.arenaFrameSprites = new HashMap<>();

        this.backgroundPattern = new ImageView();

        this.fps = new Label();
        this.fps.setVisible(Debug.PRINT_LOG);

        this.root.getChildren().add(this.backgroundPattern);
        this.root.getChildren().add(this.fps);

        GameEventBus.getInstance().register(this);

        // Setting up area to draw graphics

        this.backgroundCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE),
                ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE) + this.yoffset);
        root.getChildren().add(this.backgroundCanvas);

        this.frameCanvas = new Canvas(this.backgroundCanvas.getWidth(), this.backgroundCanvas.getHeight());
        root.getChildren().add(this.frameCanvas);

        this.tileCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 2 * Constants.GAME_SCALE),
                (Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE + this.yoffset);
        root.getChildren().add(this.tileCanvas);

        this.backgroundCanvas.setLayoutX(this.xoffset - Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
        this.backgroundCanvas.setLayoutY(0);
        this.frameCanvas.setLayoutX(this.backgroundCanvas.getLayoutX());
        this.frameCanvas.setLayoutY(this.backgroundCanvas.getLayoutY());
        this.tileCanvas.setLayoutX(this.xoffset);
        this.tileCanvas.setLayoutY(0);

        this.tileGC = this.tileCanvas.getGraphicsContext2D();
        this.frameGC = this.frameCanvas.getGraphicsContext2D();
        this.backgroundGC = this.backgroundCanvas.getGraphicsContext2D();

        // Character sprites
        this.characterSprites[0] = Container.getCharacterSprite(CharacterID.ALYSSA);
        this.characterSprites[1] = Container.getCharacterSprite(CharacterID.CHARLOTTE);
        this.characterSprites[2] = Container.getCharacterSprite(CharacterID.ZYPHER);
        this.characterSprites[3] = Container.getCharacterSprite(CharacterID.MEI);

        this.characterSparkleSprite = Container.getSprite(SpriteID.POWERUP_SPARKLES);

        this.enemySprite = Container.getCharacterSprite(CharacterID.PCCHAN);

        // Background and frame
        this.arenaBackgroundSprite = Container.getSprite(SpriteID.GAME_BACKGROUND_1);
        this.arenaFrameSprites.put(Compass.NORTH, Container.getSprite(SpriteID.GAME_FRAME_NORTH_1));
        this.arenaFrameSprites.put(Compass.WEST, Container.getSprite(SpriteID.GAME_FRAME_WEST_1));
        this.arenaFrameSprites.put(Compass.EAST, Container.getSprite(SpriteID.GAME_FRAME_EAST_1));
        this.arenaFrameSprites.put(Compass.SOUTH, Container.getSprite(SpriteID.GAME_FRAME_SOUTH_1));

        // Bombs
        this.bombSprite[0] = Container.getSprite(SpriteID.BOMB_ALYSSA);
        this.bombSprite[1] = Container.getSprite(SpriteID.BOMB_CHARLOTTE);
        this.bombSprite[2] = Container.getSprite(SpriteID.BOMB_ZYPHER);
        this.bombSprite[3] = Container.getSprite(SpriteID.BOMB_MEI);

        // Walls
        this.destructibleWallSprite = Container.getSprite(SpriteID.WALL_DESTR_PARASOL);
        this.destructibleWallDestroyedSprite = Container.getSprite(SpriteID.WALL_DESTR_PARASOL_DESTROYED);
        this.indestructibleWallSprite = Container.getSprite(SpriteID.WALL_INDESTR_BEACHSTONE);

        // Powerups
        this.powerUpSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH));
        this.powerUpSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT));
        this.powerUpSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE));
        this.powerUpSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED));
        this.powerUpSprites.put(PowerUpType.FISH, Container.getSprite(SpriteID.FISH));

        this.powerUpSpawnSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_SPAWN));
        this.powerUpSpawnSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_SPAWN));
        this.powerUpSpawnSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_SPAWN));
        this.powerUpSpawnSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_SPAWN));
        this.powerUpSpawnSprites.put(PowerUpType.FISH, Container.getSprite(SpriteID.FISH));

        this.powerUpPickupSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_PICKUP));
        this.powerUpPickupSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_PICKUP));
        this.powerUpPickupSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_PICKUP));
        this.powerUpPickupSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_PICKUP));
        this.powerUpPickupSprites.put(PowerUpType.FISH, Container.getSprite(SpriteID.FISH));

        this.powerUpDestroySprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_DESTROY));
        this.powerUpDestroySprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_DESTROY));
        this.powerUpDestroySprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_DESTROY));
        this.powerUpDestroySprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_DESTROY));
        this.powerUpDestroySprites.put(PowerUpType.FISH, Container.getSprite(SpriteID.FISH));

        // Blasts
        this.blastSpriteCenter = Container.getSprite(SpriteID.BLAST_CENTER);

        this.blastSpriteBeam[0] = Container.getSprite(SpriteID.BLAST_BEAM_WEST);
        this.blastSpriteBeam[1] = Container.getSprite(SpriteID.BLAST_BEAM_NORTH);
        this.blastSpriteBeam[2] = Container.getSprite(SpriteID.BLAST_BEAM_EAST);
        this.blastSpriteBeam[3] = Container.getSprite(SpriteID.BLAST_BEAM_SOUTH);

        this.blastSpriteEnd[0] = Container.getSprite(SpriteID.BLAST_END_WEST);
        this.blastSpriteEnd[1] = Container.getSprite(SpriteID.BLAST_END_NORTH);
        this.blastSpriteEnd[2] = Container.getSprite(SpriteID.BLAST_END_EAST);
        this.blastSpriteEnd[3] = Container.getSprite(SpriteID.BLAST_END_SOUTH);

    }

    public void initArena(StaticTile[][] arenaTiles) {

        // Rendering gamebackground image to gamebackground canvas

        this.arenaBackgroundSprite.setPosition(Constants.DEFAULT_TILE_WIDTH * 2,
                    Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);
        this.arenaBackgroundSprite.render(backgroundGC, -1);

        this.arenaFrameSprites.get(Compass.NORTH).setPosition(0,
                    -Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);
        this.arenaFrameSprites.get(Compass.NORTH).render(frameGC, -1);

        this.arenaFrameSprites.get(Compass.WEST).setPosition(0,
                    Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);
        this.arenaFrameSprites.get(Compass.WEST).render(frameGC, -1);

        this.arenaFrameSprites.get(Compass.EAST).setPosition(Constants.DEFAULT_TILE_WIDTH * (2 + Constants.DEFAULT_GRID_WIDTH),
                Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);
        this.arenaFrameSprites.get(Compass.EAST).render(frameGC, -1);

        this.arenaFrameSprites.get(Compass.SOUTH).setPosition(0,
                (Constants.DEFAULT_GRID_HEIGHT + 1) * Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);
        this.arenaFrameSprites.get(Compass.SOUTH).render(frameGC, 0);

        // Rendering indestructible walls on gamebackground canvas
        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null && arenaTiles[i][j] instanceof IndestructibleWall) {
                    Sprite tileSprite = getTileSprite(arenaTiles[i][j]);
                    tileSprite.setPosition((i+1) * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH,
                            (j+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y + this.yoffset);
                    tileSprite.render(this.backgroundGC, -1);
                }
            }
        }

        // Creating sub-elements
        createRandomBackgroundPattern();
    }

    private void createRandomBackgroundPattern() {
        this.backgroundPattern.setImage(Container.getImage(ImageID.BACKGROUND_PATTERN_1));
    }

    public void initHUDandScoreboard(Set<Player> players) {
        if (this.hudView != null) {
            this.hudView.clearHUD();
        }
        this.hudView = new HUDView(this.root, players);

        if (this.scoreboardView != null) {
            this.scoreboardView.clearScoreboard();
        }
        this.scoreboardView = new ScoreboardView(this.root, players);
    }

    public void updateTimer(double timer) {
        this.hudView.updateTimer(timer);
    }

    public void showGameOverMessage(String message) {
        this.scoreboardView.hideScoreboard();
        this.hudView.showGameOverMessage(message);
    }

    public void showScoreboard(boolean showScoreboard) {
        if (showScoreboard) {
            if(!this.scoreboardView.isShowing()) {
                this.scoreboardView.showScoreboard();
            }
        } else {
            this.scoreboardView.hideScoreboard();
        }
    }

    // todo - UPDATE VIEW SECTION

    public void updateView(List<Movable> arenaMovables, StaticTile[][] arenaTiles, Set<Player> players,
                           double timeSinceStart, double timeSinceLastCall) {

        // Clearing canvas
        this.frameGC.clearRect(0, 0, this.frameCanvas.getWidth(), this.frameCanvas.getHeight());
        this.tileGC.clearRect(0, 0, this.tileCanvas.getWidth(), this.tileCanvas.getHeight());

        // Rendering frame sprites
        this.arenaFrameSprites.get(Compass.NORTH).render(this.frameGC, timeSinceStart);
        this.arenaFrameSprites.get(Compass.WEST).render(this.frameGC, timeSinceStart);
        this.arenaFrameSprites.get(Compass.EAST).render(this.frameGC, timeSinceStart);
        this.arenaFrameSprites.get(Compass.SOUTH).render(this.frameGC, timeSinceStart);

        printFps(timeSinceLastCall);

        // Rendering tiles
        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null && !(arenaTiles[i][j] instanceof IndestructibleWall)) {
                    renderChoosing(arenaTiles[i][j], new Point(i, j), timeSinceStart);
                }
            }
        }

        // Rendering movables
        for (Movable movable : arenaMovables) {
            renderMovable(movable, timeSinceStart);
        }

        // Update timer
        this.hudView.updateStats(players);
        this.hudView.updateTimer(timeSinceStart);

        // Updating scoreboard
        if (this.scoreboardView.isShowing()) {
            this.scoreboardView.updateScoreboard();
        }
    }

    // Method for printing current fps count, updates every 10 frame
    private void printFps(double timeSinceLastCall) {
        if (Debug.PRINT_LOG) {
            if (this.updateCounter == 10) {
                this.updateCounter = 0;
                this.fps.setText("FPS: " + (int) (1 / timeSinceLastCall));
                this.fps.setLayoutX(1029);
            }
            this.updateCounter += 1;
        }
    }

    // Method that just moves through the alternatives of rendering, for static tiles
    private void renderChoosing(StaticTile tile, Point gridPosition, double timeSinceStart) {
        if (tile instanceof DoubleTile) {
            renderDoubleTile((DoubleTile)tile, gridPosition, timeSinceStart);
        } else if (tile instanceof Blast) {
            renderBlast((Blast)tile, gridPosition, timeSinceStart);
        } else if (tile instanceof DestructibleWall) {
            renderDestructibleWall((DestructibleWall)tile, gridPosition, timeSinceStart);
        } else if (tile instanceof PowerUp) {
            renderPowerUp((PowerUp)tile, gridPosition, timeSinceStart);
        } else {
            renderTile(tile, gridPosition, timeSinceStart);
        }
    }

    // Standard method for rendering a static tile, if it has no specific one
    private void renderTile(StaticTile tile, Point gridPosition, double timeSinceStart) {
        Sprite tileSprite = getTileSprite(tile);
        if (tileSprite != null) {
            tileSprite.setPosition(gridPosition.getX() * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH,
                    (gridPosition.getY()+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y + this.yoffset);
            tileSprite.render(this.tileGC, timeSinceStart);
        }
    }

    // This one calls the renderChoosing()-method for both of the DoubleTiles' StaticTiles, which could cause a
    // recursive call until all StaticTiles are found and rendered
    private void renderDoubleTile(DoubleTile doubleTile, Point gridPosition, double timeSinceStart) {
        StaticTile[] tiles = new StaticTile[2];
        tiles[0] = doubleTile.getLowerTile();
        tiles[1] = doubleTile.getUpperTile();

        for (int i = 0; i < tiles.length; i++) {
            StaticTile tile = tiles[i];
            renderChoosing(tile, gridPosition, timeSinceStart);
        }
    }

    // AnimatedTile is a semi-smooth solution that lets us have just the one Sprite for every animation, even though
    // the animation in question is not supposed to be synched with all the other instances of the same object
    private void renderAnimatedTile(Sprite currentSprite, AnimatedTile tile, Point gridPosition, double timeSinceStart) {

        double timeDifference = timeSinceStart - tile.getTimeStamp();

        Point destinationCanvasPosition =
                new Point((int)(gridPosition.getX() * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH),
                        (int)((gridPosition.getY()+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y + this.yoffset));

        if (currentSprite instanceof AnimatedSprite &&
                timeDifference <= ((AnimatedSprite)currentSprite).getLength() * ((AnimatedSprite)currentSprite).getDuration()) {
            currentSprite.setPosition(destinationCanvasPosition);
            currentSprite.render(this.tileGC, timeDifference);
        } else {

            if (tile instanceof PowerUp && ((PowerUp) tile).getState() == PowerUp.PowerUpState.SPAWN) {
                ((PowerUp) tile).setState(PowerUp.PowerUpState.IDLE);
                renderTile((StaticTile)tile, gridPosition, timeSinceStart);
            } else {
                GameEventBus.getInstance().post(new RemoveTileEvent((StaticTile) tile, gridPosition));
            }
        }
    }

    // Simple rendering of blast; it finds the current sprite and then calls renderAnimatedTile()
    private void renderBlast(Blast blast, Point gridPosition, double timeSinceStart) {

        Sprite currentSprite = this.blastSpriteCenter;
        if (blast.getState() == BlastState.BEAM) {
            currentSprite = this.blastSpriteBeam[Utils.getIntegerFromDirection(blast.getDirection())];
        } else if (blast.getState() == BlastState.END) {
            currentSprite = this.blastSpriteEnd[Utils.getIntegerFromDirection(blast.getDirection())];
        }

        renderAnimatedTile(currentSprite, blast, gridPosition, timeSinceStart);
    }

    // Does a renderAnimatedTile() when destroyed, because of animation, but otherwise goes with the standard renderTile()
    private void renderDestructibleWall(DestructibleWall wall, Point gridPosition, double timeSinceStart) {

        if (wall.isDestroyed()) {
            renderAnimatedTile(this.destructibleWallDestroyedSprite, wall, gridPosition, timeSinceStart);
        } else {
            renderTile(wall, gridPosition, timeSinceStart);
        }
    }

    // Does a renderAnimatedTile() when destroyed, because of animation, but otherwise goes with the standard renderTile()
    private void renderPowerUp(PowerUp powerup, Point gridPosition, double timeSinceStart) {

        if (powerup.getState() != PowerUp.PowerUpState.IDLE) {
            renderAnimatedTile(getTileSprite(powerup), powerup, gridPosition, timeSinceStart);
        } else {
            renderTile(powerup, gridPosition, timeSinceStart);
        }
    }

    public void renderMovable(Movable movable, double timeSinceStart) {
        CharacterSprite sprites = null;

        // Get CharacterSprites for movable
        if (movable instanceof GameCharacter) {
            for (int i = 0; i < 4; i++) {
                if (this.characterSprites[i] != null && this.characterSprites[i].getName().equals(movable.getName())) {
                    sprites = this.characterSprites[i];
                }
            }
        } else if (movable instanceof Enemy) {
            sprites = this.enemySprite;
        }

        // Get rendering
        if (sprites != null) {

            // Setting up sprite
            Sprite[] currentSprites = getCurrentCharacterSprite(movable, sprites, timeSinceStart);
            int spriteIndex = getCharacterSpriteIndex(movable);
            AnimatedSprite actualSprite = (AnimatedSprite) currentSprites[spriteIndex];

            actualSprite.setPosition(movable.getCanvasPositionX() + Constants.DEFAULT_TILE_WIDTH,
                    movable.getCanvasPositionY() + Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);

            animatedSpriteSettings(actualSprite, movable);

            renderMovableSprite(movable, actualSprite, timeSinceStart);

            // This last thing puts sparkles around character for every recently picked up powerup
            if (movable instanceof GameCharacter) {
                addSparkles(movable, timeSinceStart);
            }
        }
    }

    // Chooses correct sprite for a character
    private Sprite[] getCurrentCharacterSprite(Movable movable, CharacterSprite sprites, double timeSinceStart) {
        MovableState state = movable.getState();

        Sprite[] currentSprite = sprites.getIdleSprites(); // Always idle if no other state is active

        if (state == MovableState.WALK) {
            currentSprite = sprites.getWalkSprites();

        } else if (state == MovableState.FLINCH) {
            currentSprite = sprites.getFlinchSprites();

        } else if (state == MovableState.SPAWN || state == MovableState.DEATH) {
            if (state == MovableState.SPAWN) {
                currentSprite = sprites.getSpawnSprites();
            } else {
                currentSprite = sprites.getDeathSprites();
            }
            if (movable instanceof Enemy && ((Enemy)movable).getDeathTimeStamp() == -1) {
                ((Enemy)movable).setDeathTimeStamp(timeSinceStart);
            }
        }
        return currentSprite;
    }

    // Gets the index for the specific sprite-array
    private int getCharacterSpriteIndex(Movable movable) {
        MovableState state = movable.getState();
        int spriteIndex = 0;
        if (state == MovableState.SPAWN || state == MovableState.DEATH || movable.getDirection() == Direction.DOWN) {
            spriteIndex = 0;
        } else if (movable.getDirection() == Direction.LEFT) {
            spriteIndex = 1;
        } else if (movable.getDirection() == Direction.RIGHT) {
            spriteIndex = 2;
        } else if (movable.getDirection() == Direction.UP) {
            spriteIndex = 3;
        }
        return spriteIndex;
    }

    // Additional settings for character sprite, if it only should run once
    private void animatedSpriteSettings(AnimatedSprite actualSprite, Movable movable) {
        MovableState state = movable.getState();
        if ((state == MovableState.SPAWN || state == MovableState.DEATH || state == MovableState.FLINCH)
                && movable instanceof GameCharacter && (!actualSprite.getPlayedYet() || movable.hasChangedState())) {

            actualSprite.setAnimationFinishedEvent(new AnimationFinishedEvent(movable));
            actualSprite.setStartFromBeginning(true);
            actualSprite.resetLoops();
            actualSprite.setLoops(1);
            actualSprite.setLingerOnLastFrame(true);
        }
    }

    // When a sprite is determined, this is called to render it
    private void renderMovableSprite(Movable movable, AnimatedSprite actualSprite, double timeSinceStart) {
        // This only runs if the movable is an enemy and in death-state, to keep track of more than one enemy
        // animation and death event, at the same time
        if (movable instanceof Enemy && movable.getState() == MovableState.DEATH && ((Enemy)movable).getDeathTimeStamp() != -1) {
            double timeDifference = timeSinceStart - ((Enemy)movable).getDeathTimeStamp();
            if (timeDifference <= actualSprite.getLength() * actualSprite.getDuration()) {
                actualSprite.render(this.tileGC, timeDifference);
            } else {
                GameEventBus.getInstance().post(new DeathEvent(movable));
            }

        } else {
            // This little condition makes the movable flicker when invincible
            if (movable.getState() == MovableState.FLINCH || !movable.isInvincible()
                    || Math.round(timeSinceStart * 50) % 3 != 0) {

                actualSprite.render(tileGC, timeSinceStart);
            }
        }
    }

    // Adds sparkles to a character for every powerup picked up
    private void addSparkles(Movable movable, double timeSinceStart) {
        List<Pair<PowerUpType, Double>> list = ((GameCharacter) movable).getPowerUpPickedUp();
        ListIterator<Pair<PowerUpType, Double>> iterator = list.listIterator();

        while (iterator.hasNext()) {
            double timeStamp = iterator.next().getSecond();
            if (this.characterSparkleSprite instanceof AnimatedSprite
                        && timeSinceStart - timeStamp <= ((AnimatedSprite) this.characterSparkleSprite).getTotalTime()) {

                this.characterSparkleSprite.setPosition(movable.getCanvasPositionX() + Constants.DEFAULT_TILE_WIDTH,
                        movable.getCanvasPositionY() + Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y + this.yoffset);

                this.characterSparkleSprite.render(this.tileGC, timeSinceStart - timeStamp);
            }
        }
    }

    // Simple factory method to get the right sprite for a given tile
    public Sprite getTileSprite(StaticTile tile) {
        if (tile instanceof Wall) {
            if (tile instanceof DestructibleWall) {
                return this.destructibleWallSprite;
            } else if (tile instanceof IndestructibleWall) {
                return this.indestructibleWallSprite;
            }
        } else if (tile instanceof Bomb) {
            String name = ((Bomb)tile).getOwner().getName();
            switch (name) {
                case "ALYSSA":
                    return this.bombSprite[0];
                case "ZYPHER":
                    return this.bombSprite[1];
                case "CHARLOTTE":
                    return this.bombSprite[2];
                case "MEI":
                    return this.bombSprite[3];
                default:
            }
        } else if (tile instanceof PowerUp) {
            switch (((PowerUp) tile).getState()) {
                case SPAWN:
                    return this.powerUpSpawnSprites.get(((PowerUp) tile).getPowerUpType());
                case PICKUP:
                    return this.powerUpPickupSprites.get(((PowerUp) tile).getPowerUpType());
                case DESTROY:
                    return this.powerUpDestroySprites.get(((PowerUp) tile).getPowerUpType());
                default:
                    return this.powerUpSprites.get(((PowerUp) tile).getPowerUpType());
            }
        }
        return null;
    }

    // Event when an animation is finished, not entirely surprising
    @Subscribe
    public void animationFinished(AnimationFinishedEvent animationFinishedEvent) {
        if (animationFinishedEvent.getMovable() != null) {
            Movable movable = animationFinishedEvent.getMovable();
            if (movable.getState() == MovableState.DEATH) {
                GameEventBus.getInstance().post(new DeathEvent(movable));
            } else if (movable.getState() == MovableState.SPAWN) {
                GameEventBus.getInstance().post(new SpawnEvent(movable));
            }
        }
    }

}
