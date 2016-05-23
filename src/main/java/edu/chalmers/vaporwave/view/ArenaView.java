package edu.chalmers.vaporwave.view;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ArenaView {

    public enum Compass {
        NORTH, WEST, EAST, SOUTH
    }

    private static final double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
    private static final double yoffset = 0;

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
    private Sprite mineSprite;

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
        this.arenaFrameSprites = new HashMap<>();

        this.fps = new Label();

        this.powerUpSprites = new HashMap<>();
        this.powerUpSpawnSprites = new HashMap<>();
        this.powerUpPickupSprites = new HashMap<>();
        this.powerUpDestroySprites = new HashMap<>();

        this.backgroundPattern = new ImageView();

        root.getChildren().add(backgroundPattern);

        GameEventBus.getInstance().register(this);

        // Setting up area to draw graphics

        backgroundCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE),
                ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
        root.getChildren().add(backgroundCanvas);

        frameCanvas = new Canvas(backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
        root.getChildren().add(frameCanvas);

        tileCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 2 * Constants.GAME_SCALE),
                (Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE);
        root.getChildren().add(tileCanvas);

        backgroundCanvas.setLayoutX(xoffset - Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
        backgroundCanvas.setLayoutY(yoffset);
        frameCanvas.setLayoutX(backgroundCanvas.getLayoutX());
        frameCanvas.setLayoutY(backgroundCanvas.getLayoutY());
        tileCanvas.setLayoutX(xoffset);
        tileCanvas.setLayoutY(yoffset);

        tileGC = tileCanvas.getGraphicsContext2D();
        frameGC = frameCanvas.getGraphicsContext2D();
        backgroundGC = backgroundCanvas.getGraphicsContext2D();

        // Character sprites
        characterSprites[0] = Container.getCharacterSprite(CharacterID.ALYSSA);
        characterSprites[1] = Container.getCharacterSprite(CharacterID.CHARLOTTE);
        characterSprites[2] = Container.getCharacterSprite(CharacterID.ZYPHER);
        characterSprites[3] = Container.getCharacterSprite(CharacterID.MEI);

        Image characterMiscSpritesheet = Container.getImage(ImageID.CHARACTER_MISC);
        characterSparkleSprite =
                new AnimatedSprite(characterMiscSpritesheet, new Dimension(48, 48), 9, 0.08, new int[] {0, 0}, new double[] {16, 27});

        enemySprite = Container.getCharacterSprite(CharacterID.PCCHAN);

        // Background and frame
        arenaBackgroundSprite = Container.getSprite(SpriteID.GAME_BACKGROUND_1);
        arenaFrameSprites.put(Compass.NORTH, Container.getSprite(SpriteID.GAME_FRAME_NORTH_1));
        arenaFrameSprites.put(Compass.WEST, Container.getSprite(SpriteID.GAME_FRAME_WEST_1));
        arenaFrameSprites.put(Compass.EAST, Container.getSprite(SpriteID.GAME_FRAME_EAST_1));
        arenaFrameSprites.put(Compass.SOUTH, Container.getSprite(SpriteID.GAME_FRAME_SOUTH_1));

        // Bombs
        bombSprite[0] = Container.getSprite(SpriteID.BOMB_ALYSSA);
        bombSprite[1] = Container.getSprite(SpriteID.BOMB_CHARLOTTE);
        bombSprite[2] = Container.getSprite(SpriteID.BOMB_ZYPHER);
        bombSprite[3] = Container.getSprite(SpriteID.BOMB_MEI);
        mineSprite = Container.getSprite(SpriteID.MINE);

        // Walls
        destructibleWallSprite = Container.getSprite(SpriteID.WALL_DESTR_PARASOL);
        destructibleWallDestroyedSprite = Container.getSprite(SpriteID.WALL_DESTR_PARASOL_DESTROYED);
        indestructibleWallSprite = Container.getSprite(SpriteID.WALL_INDESTR_BEACHSTONE);

        // Powerups
        powerUpSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH));
        powerUpSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT));
        powerUpSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE));
        powerUpSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED));

        powerUpSpawnSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_SPAWN));
        powerUpSpawnSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_SPAWN));
        powerUpSpawnSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_SPAWN));
        powerUpSpawnSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_SPAWN));

        powerUpPickupSprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_PICKUP));
        powerUpPickupSprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_PICKUP));
        powerUpPickupSprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_PICKUP));
        powerUpPickupSprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_PICKUP));

        powerUpDestroySprites.put(PowerUpType.HEALTH, Container.getSprite(SpriteID.POWERUP_HEALTH_DESTROY));
        powerUpDestroySprites.put(PowerUpType.BOMB_COUNT, Container.getSprite(SpriteID.POWERUP_BOMBCOUNT_DESTROY));
        powerUpDestroySprites.put(PowerUpType.RANGE, Container.getSprite(SpriteID.POWERUP_RANGE_DESTROY));
        powerUpDestroySprites.put(PowerUpType.SPEED, Container.getSprite(SpriteID.POWERUP_SPEED_DESTROY));

        // Blasts
        blastSpriteCenter = Container.getSprite(SpriteID.BLAST_CENTER);

        blastSpriteBeam[0] = Container.getSprite(SpriteID.BLAST_BEAM_WEST);
        blastSpriteBeam[1] = Container.getSprite(SpriteID.BLAST_BEAM_NORTH);
        blastSpriteBeam[2] = Container.getSprite(SpriteID.BLAST_BEAM_EAST);
        blastSpriteBeam[3] = Container.getSprite(SpriteID.BLAST_BEAM_SOUTH);

        blastSpriteEnd[0] = Container.getSprite(SpriteID.BLAST_END_WEST);
        blastSpriteEnd[1] = Container.getSprite(SpriteID.BLAST_END_NORTH);
        blastSpriteEnd[2] = Container.getSprite(SpriteID.BLAST_END_EAST);
        blastSpriteEnd[3] = Container.getSprite(SpriteID.BLAST_END_SOUTH);

    }

    public void initArena(StaticTile[][] arenaTiles) {

        // Rendering gamebackground image to gamebackground canvas

        arenaBackgroundSprite.setPosition(Constants.DEFAULT_TILE_WIDTH * 2, Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaBackgroundSprite.render(backgroundGC, -1);

        arenaFrameSprites.get(Compass.NORTH).setPosition(0, -Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.NORTH).render(frameGC, -1);

        arenaFrameSprites.get(Compass.WEST).setPosition(0, Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.WEST).render(frameGC, -1);

        arenaFrameSprites.get(Compass.EAST).setPosition(Constants.DEFAULT_TILE_WIDTH * (2 + Constants.DEFAULT_GRID_WIDTH), Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.EAST).render(frameGC, -1);

        arenaFrameSprites.get(Compass.SOUTH).setPosition(0, (Constants.DEFAULT_GRID_HEIGHT + 1) * Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.SOUTH).render(frameGC, 0);

        // Rendering indestructible walls on gamebackground canvas
        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null && arenaTiles[i][j] instanceof IndestructibleWall) {
                    Sprite tileSprite = getTileSprite(arenaTiles[i][j]);
                    tileSprite.setPosition((i+1) * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH, (j+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y);
                    tileSprite.render(backgroundGC, -1);
                }
            }
        }

        // Creating sub-elements
        createRandomBackgroundPattern();
    }

    private void createRandomBackgroundPattern() {
//        int randomNum = 1 + (int)(Math.random() * 4);
//        backgroundPattern.setImage(new Image("images/backgroundPatterns/pattern"+randomNum+".png"));
//        backgroundPattern.setImage(new Image("images/backgroundPatterns/pattern1.png"));
        backgroundPattern.setImage(Container.getImage(ImageID.BACKGROUND_PATTERN_1));
    }

    public void initHUDandScoreboard(Set<Player> players) {
        if (this.hudView != null) {
            this.hudView.clearHUD();
        }
        this.hudView = new HUDView(root, players);

        if (this.scoreboardView != null) {
            this.scoreboardView.clearScoreboard();
        }
        this.scoreboardView = new ScoreboardView(root, players);
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
            scoreboardView.hideScoreboard();
        }
    }

    // todo - UPDATE VIEW SECTION

    public void updateView(List<Movable> arenaMovables, StaticTile[][] arenaTiles, Set<Player> players,
                           double timeSinceStart, double timeSinceLastCall) {

        frameGC.clearRect(0, 0, frameCanvas.getWidth(), frameCanvas.getHeight());
        tileGC.clearRect(0, 0, tileCanvas.getWidth(), tileCanvas.getHeight());

        // Rendering:

        arenaFrameSprites.get(Compass.NORTH).render(frameGC, timeSinceStart);
        arenaFrameSprites.get(Compass.WEST).render(frameGC, timeSinceStart);
        arenaFrameSprites.get(Compass.EAST).render(frameGC, timeSinceStart);
        arenaFrameSprites.get(Compass.SOUTH).render(frameGC, timeSinceStart);

        // Method for printing current fps count.
        // Updates every 10 frame
        if (updateCounter == 10){
            updateCounter = 0;
            fps.setText("FPS: " + (int) (1 / timeSinceLastCall));
            fps.setLayoutX(1029);
            this.root.getChildren().remove(fps);
            this.root.getChildren().add(fps);
        }
        updateCounter += 1;

        // ALL tile rendering should be done via renderChoosing, see below
        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null && !(arenaTiles[i][j] instanceof IndestructibleWall)) {
                    renderChoosing(arenaTiles[i][j], new Point(i, j), timeSinceStart);
                }
            }
        }

        // Movable rendering
        for (Movable movable : arenaMovables) {
            renderMovable(movable, timeSinceStart);
        }

//         Update timer
        this.hudView.updateStats(players);
        this.hudView.updateTimer(timeSinceStart);

        if (this.scoreboardView.isShowing()) {
            this.scoreboardView.updateScoreboard();
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
            tileSprite.setPosition(gridPosition.getX() * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH, (gridPosition.getY()+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y);
            tileSprite.render(tileGC, timeSinceStart);
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
                        (int)((gridPosition.getY()+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y));

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

        Sprite currentSprite = blastSpriteCenter;
        if (blast.getState() == BlastState.BEAM) {
            currentSprite = blastSpriteBeam[Utils.getIntegerFromDirection(blast.getDirection())];
        } else if (blast.getState() == BlastState.END) {
            currentSprite = blastSpriteEnd[Utils.getIntegerFromDirection(blast.getDirection())];
        }

        renderAnimatedTile(currentSprite, blast, gridPosition, timeSinceStart);
    }

    // Does a renderAnimatedTile() when destroyed, because of animation, but otherwise goes with the standard renderTile()
    private void renderDestructibleWall(DestructibleWall wall, Point gridPosition, double timeSinceStart) {

        if (wall.isDestroyed()) {
            renderAnimatedTile(destructibleWallDestroyedSprite, wall, gridPosition, timeSinceStart);
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
        MovableState state = movable.getState();
        CharacterSprite sprites = null;

        if (movable instanceof GameCharacter) {
            for (int i = 0; i < 4; i++) {
                if (characterSprites[i] != null && characterSprites[i].getName().equals(movable.getName())) {
                    sprites = characterSprites[i];
                }
            }
        } else if (movable instanceof Enemy) {
            sprites = enemySprite;
        }

        if (sprites != null) {
            Sprite[] currentSprite = sprites.getIdleSprites(); // Always idle if no other state is active

            if (state == MovableState.WALK) {
                currentSprite = sprites.getWalkSprites();

            } else if (state == MovableState.FLINCH) {
                currentSprite = sprites.getFlinchSprites();

            } else if (state == MovableState.SPAWN || state == MovableState.DEATH) {
                if (state == MovableState.SPAWN) {
                     // todo: changes for every character and enemy, not just user Character
                    currentSprite = sprites.getSpawnSprites();
                } else {
                    currentSprite = sprites.getDeathSprites();
                }
                if (movable instanceof Enemy && ((Enemy)movable).getDeathTimeStamp() == -1) {
                    ((Enemy)movable).setDeathTimeStamp(timeSinceStart);
                }
            }

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

            AnimatedSprite actualSprite = (AnimatedSprite) currentSprite[spriteIndex];
            actualSprite.setPosition(movable.getCanvasPositionX() + Constants.DEFAULT_TILE_WIDTH,
                    movable.getCanvasPositionY() + Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);

            if ((state == MovableState.SPAWN || state == MovableState.DEATH || state == MovableState.FLINCH)
                    && movable instanceof GameCharacter && (!actualSprite.getPlayedYet() || movable.hasChangedState())) {

                actualSprite.setAnimationFinishedEvent(new AnimationFinishedEvent(movable));
                actualSprite.setStartFromBeginning(true);
                actualSprite.resetLoops();
                actualSprite.setLoops(1);
                actualSprite.setLingerOnLastFrame(true);
            }

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

            // This last thing puts sparkles around character for every recently picked up powerup
            if (movable instanceof GameCharacter) {
                List<Pair<PowerUpType, Double>> list = ((GameCharacter) movable).getPowerUpPickedUp();
                ListIterator<Pair<PowerUpType, Double>> iterator = list.listIterator();

                while (iterator.hasNext()) {
                    double timeStamp = iterator.next().getSecond();
                    if (timeSinceStart - timeStamp <= ((AnimatedSprite)characterSparkleSprite).getTotalTime()) {

                        characterSparkleSprite.setPosition(movable.getCanvasPositionX() + Constants.DEFAULT_TILE_WIDTH,
                                movable.getCanvasPositionY() + Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);

                        characterSparkleSprite.render(tileGC, timeSinceStart - timeStamp);
                    }
                }
            }
        }
    }

    public Sprite getTileSprite(StaticTile tile) {
        if (tile instanceof Wall) {
            if (tile instanceof DestructibleWall) {
                return destructibleWallSprite;
            } else if (tile instanceof IndestructibleWall) {
                return indestructibleWallSprite;
            }
        } else if (tile instanceof Explosive) {
            if (tile instanceof Bomb) {

                String name = ((Bomb)tile).getOwner().getName();
                if (name.equals("ALYSSA")) {
                    return bombSprite[0];
                } else if (name.equals("ZYPHER")) {
                    return bombSprite[1];
                } else if (name.equals("CHARLOTTE")) {
                    return bombSprite[2];
                } else if (name.equals("MEI")) {
                    return bombSprite[3];
                }
            } else if (tile instanceof Mine) {
                return mineSprite;
            }
        } else if (tile instanceof PowerUp) {
            if (((PowerUp) tile).getState() == PowerUp.PowerUpState.SPAWN) {
                return powerUpSpawnSprites.get(((PowerUp) tile).getPowerUpType());
            } else if (((PowerUp) tile).getState() == PowerUp.PowerUpState.PICKUP) {
                return powerUpPickupSprites.get(((PowerUp) tile).getPowerUpType());
            } else if (((PowerUp) tile).getState() == PowerUp.PowerUpState.DESTROY) {
                return powerUpDestroySprites.get(((PowerUp) tile).getPowerUpType());
            } else {
                return powerUpSprites.get(((PowerUp) tile).getPowerUpType());
            }
        }
        return null;
    }

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
