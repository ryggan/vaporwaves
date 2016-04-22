package edu.chalmers.vaporwave.view;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.event.AnimationFinishedEvent;
import edu.chalmers.vaporwave.event.BlastEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.CharacterSpriteProperties;
import edu.chalmers.vaporwave.model.PowerUpProperties;
import edu.chalmers.vaporwave.model.PowerUpSpriteProperties;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaView {

    private Canvas backgroundCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext tileGC;

    private HUDView hudView;
    private Scoreboard scoreboard;

    private CharacterSprite[] characterSprites = new CharacterSprite[4];
    private Sprite[] bombSprite = new Sprite[4];

    private Sprite destructibleWallSprite;
    private Sprite destructibleWallDestroyedSprite;
    private Sprite indestructibleWallSprite;

    private Sprite explosionEndSprite;
    private Sprite explosionBeamSprite;
    private Sprite explosionCenterSprite;

    private Map<Point, Sprite> explosionSpriteList;

    private Group root;
    
    public ArenaView(Group root) {
        this.root = root;
        this.explosionSpriteList = new HashMap<Point, Sprite>();
        GameEventBus.getInstance().register(this);

        // Setting up area to draw graphics

        backgroundCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        root.getChildren().add(backgroundCanvas);
        tileCanvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        root.getChildren().add(tileCanvas);


        double xoffset = Math.floor((Constants.WINDOW_WIDTH - Constants.GAME_WIDTH) / 2);
        double yoffset = Math.floor((Constants.WINDOW_HEIGHT - Constants.GAME_HEIGHT) / 2);
        tileCanvas.setLayoutX(xoffset);
        tileCanvas.setLayoutY(yoffset);
        backgroundCanvas.setLayoutX(xoffset);
        backgroundCanvas.setLayoutY(yoffset);

        tileGC = tileCanvas.getGraphicsContext2D();
        backgroundGC = backgroundCanvas.getGraphicsContext2D();

        // Setting up sprites

        characterSprites[0] = new CharacterSprite("ALYSSA");
        initCharacterSprites(characterSprites[0]);
//        characterSprites[1] = new CharacterSprite("ZYPHER");
//        initCharacterSprites(characterSprites[1]);
//        characterSprites[2] = new CharacterSprite("CHARLOTTE");
//        initCharacterSprites(characterSprites[2]);
//        characterSprites[3] = new CharacterSprite("MEI");
//        initCharacterSprites(characterSprites[3]);

        Image bombSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18.png");

        bombSprite[0] =
                new AnimatedSprite(bombSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 0}, new double[] {1, 1});
        bombSprite[1] =
                new AnimatedSprite(bombSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 1}, new double[] {1, 1});
        bombSprite[2] =
                new AnimatedSprite(bombSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 2}, new double[] {1, 1});
        bombSprite[3] =
                new AnimatedSprite(bombSpriteSheet, new Dimension(18, 18), 2, 0.4, new int[] {0, 3}, new double[] {1, 1});

        Image wallSpriteSheet = new Image("images/spritesheet-walls_both-18x18.png");
        destructibleWallSprite =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 1, 1.0, new int[] {0, 0}, new double[] {1, 1});
        destructibleWallDestroyedSprite =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 0}, new double[] {1, 1});
        indestructibleWallSprite =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 1, 1.0, new int[] {0, 1}, new double[] {1, 1});

        Image blastSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18.png");
        explosionEndSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 0}, new double[] {1, 1});
        explosionBeamSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 1}, new double[] {1, 1});

    }

    public void initArena() {

        createBackground(backgroundGC);

        hudView = new HUDView();
        scoreboard = new Scoreboard(root);
        //make players a proper arraylist of the current players
        //scoreboard.addPlayersToScoreboard(players);
    }

    private void createBackground(GraphicsContext backgroundGC) {

        Sprite arenaBackgroundSprite = new Sprite("images/sprite-arenabackground-01.png");
        arenaBackgroundSprite.setPosition(0, 0);
        arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        arenaBackgroundSprite.render(backgroundGC, -1);

    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        Image blastSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18.png");
        explosionCenterSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {2, 4}, new double[] {1, 1});
        ((AnimatedSprite)explosionCenterSprite).setLoops(1);
        explosionSpriteList.put(placeBombEvent.getGridPosition(), explosionCenterSprite);
    }

    @Subscribe
    public void animationFinished(AnimationFinishedEvent animationFinishedEvent) {
        explosionCenterSprite = null;
    }

    public void updateView(ArrayList<Movable> arenaMovables, StaticTile[][] arenaTiles, double timeSinceStart, double timeSinceLastCall) {

        /**
         * Checks if player is holding tab, then shows the scoreboard.
         */
        if(ListenerController.getInstance().getInput().contains("TAB")) {
            scoreboard.showScoreboard();
        } else {
            scoreboard.hideScoreboard();
        }

        // Rendering:

        tileGC.clearRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null) {
                    Sprite tileSprite;
                    if(arenaTiles[i][j] instanceof Blast) {
                        tileSprite = getTileSprite(arenaTiles[i][j], new Point(i, j));
                    } else {
                        tileSprite = getTileSprite(arenaTiles[i][j]);
                    }
                    if (tileSprite != null) {
                        tileSprite.setPosition(i * Constants.DEFAULT_TILE_WIDTH, j * Constants.DEFAULT_TILE_WIDTH);
                        tileSprite.render(tileGC, timeSinceStart);
                    }

                }
            }
        }
        for (Movable movable : arenaMovables) {

            if (movable instanceof GameCharacter) {
                renderCharacter((GameCharacter)movable, timeSinceStart);

            } else if (movable instanceof Enemy) {

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
            }
        } else if (tile instanceof PowerUp) {

        }
        return null;
    }

    public Sprite getTileSprite(StaticTile tile, Point position) {
        return explosionSpriteList.get(position);
    }

    public void renderCharacter(GameCharacter character, double timeSinceStart) {
        CharacterState state = character.getState();
        CharacterSprite sprites = null;
        for (int i = 0; i < 4; i++) {
            if (characterSprites[i] != null && characterSprites[i].getName().equals(character.getName())) {
                sprites = characterSprites[i];
            }
        }

        if (sprites != null) {
            Sprite[] currentSprite = sprites.getIdleSprites(); // Always idle if no other state is active
            if (state == CharacterState.WALK) {
                currentSprite = sprites.getWalkSprites();
            } else if (state == CharacterState.FLINCH) {
                currentSprite = sprites.getFlinchSprites();
            } else if (state == CharacterState.SPAWN) {
                currentSprite = sprites.getSpawnSprites();
            } else if (state == CharacterState.DEATH) {
                currentSprite = sprites.getDeathSprites();
            }

            int spriteIndex = 0;
            if (state == CharacterState.SPAWN || state == CharacterState.DEATH || character.getDirection() == Directions.DOWN) {
                spriteIndex = 0;
            } else if (character.getDirection() == Directions.LEFT) {
                spriteIndex = 1;
            } else if (character.getDirection() == Directions.RIGHT) {
                spriteIndex = 2;
            } else if (character.getDirection() == Directions.UP) {
                spriteIndex = 3;
            }

            Sprite actualSprite = currentSprite[spriteIndex];
            actualSprite.setPosition(character.getCanvasPositionX(), character.getCanvasPositionY());
            actualSprite.render(tileGC, timeSinceStart);
        }
    }

    private void initPowerUpSprites(PowerUpSprite powerUpSprite) {
        XMLReader reader = new XMLReader(Constants.GAME_CHARACTER_XML_FILE);
        PowerUpProperties powerUpProperties = PowerUpLoader.loadPowerUp(reader.read(), powerUpSprite.getType());

        for(PowerUpState powerUpState : Constants.POWERUP_STATE) {
            PowerUpSpriteProperties powerUpSpriteProperties = powerUpProperties.getSpriteProperties(powerUpState);
            switch(powerUpState) {
                case HEALTH:
                    powerUpSprite.setHealthSprite(
                            new AnimatedSprite(powerUpSpriteProperties.getSpritesheet(),
                                    new Dimension(powerUpSpriteProperties.getDimensionX(),
                                            powerUpSpriteProperties.getDimensionY()),
                                    powerUpSpriteProperties.getFrames(),
                                    powerUpSpriteProperties.getDuration(),
                                    powerUpSpriteProperties.getFirstFrame(),
                                    powerUpSpriteProperties.getOffset())
                    );
                    break;
                case BOMB_COUNT:
                    powerUpSprite.setBombCountSprite(
                            new AnimatedSprite(powerUpSpriteProperties.getSpritesheet(),
                                    new Dimension(powerUpSpriteProperties.getDimensionX(),
                                            powerUpSpriteProperties.getDimensionY()),
                                    powerUpSpriteProperties.getFrames(),
                                    powerUpSpriteProperties.getDuration(),
                                    powerUpSpriteProperties.getFirstFrame(),
                                    powerUpSpriteProperties.getOffset())
                    );
                    break;
                case SPEED:
                    powerUpSprite.setSpeedSprite(
                            new AnimatedSprite(powerUpSpriteProperties.getSpritesheet(),
                                    new Dimension(powerUpSpriteProperties.getDimensionX(),
                                            powerUpSpriteProperties.getDimensionY()),
                                    powerUpSpriteProperties.getFrames(),
                                    powerUpSpriteProperties.getDuration(),
                                    powerUpSpriteProperties.getFirstFrame(),
                                    powerUpSpriteProperties.getOffset())
                    );
                    break;
                case RANGE:
                    powerUpSprite.setRangeSprite(
                            new AnimatedSprite(powerUpSpriteProperties.getSpritesheet(),
                                    new Dimension(powerUpSpriteProperties.getDimensionX(),
                                            powerUpSpriteProperties.getDimensionY()),
                                    powerUpSpriteProperties.getFrames(),
                                    powerUpSpriteProperties.getDuration(),
                                    powerUpSpriteProperties.getFirstFrame(),
                                    powerUpSpriteProperties.getOffset())
                    );
                    break;
            }
        }
    }

    /**
     * Reads character information from an XML-file and populate the instance variables for the sprites
     */
    private void initCharacterSprites(CharacterSprite characterSprite) {
        XMLReader reader = new XMLReader(Constants.GAME_CHARACTER_XML_FILE);
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), characterSprite.getName());

        for (CharacterState characterState : Constants.CHARACTER_CHARACTER_STATE) {
            CharacterSpriteProperties characterSpriteProperties = characterProperties.getSpriteProperties(characterState);
            switch (characterState) {
                case SPAWN:
                    characterSprite.setSpawnSprite(
                            new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                            new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                            characterSpriteProperties.getFrames(),
                            characterSpriteProperties.getDuration(),
                            characterSpriteProperties.getFirstFrame(),
                            characterSpriteProperties.getOffset())
                            );
                    break;
                case DEATH:
                    characterSprite.setDeathSprite(
                            new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                            new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                            characterSpriteProperties.getFrames(),
                            characterSpriteProperties.getDuration(),
                            characterSpriteProperties.getFirstFrame(),
                            characterSpriteProperties.getOffset())
                            );
                    break;
                case WALK:
                    for (int i = 0; i < 4; i++) {
                        characterSprite.setWalkSprite(
                                new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                characterSpriteProperties.getFrames(),
                                characterSpriteProperties.getDuration(),
                                new int[]{characterSpriteProperties.getFirstFrame()[0], i},
                                characterSpriteProperties.getOffset()),
                                i);
                    }
                    break;
                case IDLE:
                    for (int i = 0; i < 4; i++) {
                        characterSprite.setIdleSprite(new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                characterSpriteProperties.getFrames(),
                                characterSpriteProperties.getDuration(),
                                new int[]{i, characterSpriteProperties.getFirstFrame()[1]},
                                characterSpriteProperties.getOffset()),
                                i);
                    }
                    break;
                case FLINCH:
                    for (int i = 0; i < 4; i++) {
                        characterSprite.setFlinchSprite(new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                characterSpriteProperties.getFrames(),
                                characterSpriteProperties.getDuration(),
                                new int[]{characterSpriteProperties.getFirstFrame()[0] + i, i},
                                characterSpriteProperties.getOffset()),
                                i);
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
