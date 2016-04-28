package edu.chalmers.vaporwave.view;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.CharacterProperties;
import edu.chalmers.vaporwave.model.CharacterSpriteProperties;
import edu.chalmers.vaporwave.model.PowerUpProperties;
import edu.chalmers.vaporwave.model.PowerUpSpriteProperties;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.*;

public class ArenaView {

    private Canvas backgroundCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext tileGC;

    private ImageView backgroundPattern;

    private HUDView hudView;
    private Scoreboard scoreboard;

    private Sprite arenaBackgroundSprite;
    private Map<Compass, Sprite> arenaFrameSprites;

    private CharacterSprite[] characterSprites = new CharacterSprite[4];
    private CharacterSprite enemySprite;
    private Sprite[] bombSprite = new Sprite[4];

    private Sprite destructibleWallSprite;
    private Sprite destructibleWallDestroyedSprite;
    private Sprite indestructibleWallSprite;

    private Map<PowerUpState, Sprite> powerUpSprites;

    private Map<Point, BlastSpriteCollection> blastSpriteMap;
    private Set<Point> destroyedWalls;

    private Group root;

    private Label fps;

    private int updateCounter;

    public enum Compass {
        NORTH, WEST, EAST, SOUTH
    }
    
    public ArenaView(Group root) {
        this.root = root;
        this.arenaFrameSprites = new HashMap<>();
        this.blastSpriteMap = new HashMap<>();
        this.destroyedWalls = new HashSet<>();
        this.fps = new Label();
        
        this.powerUpSprites = new HashMap<>();
        this.backgroundPattern=new ImageView();

        root.getChildren().add(backgroundPattern);



        GameEventBus.getInstance().register(this);

        // Setting up area to draw graphics

        backgroundCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE), ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
        root.getChildren().add(backgroundCanvas);
        tileCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 2 * Constants.GAME_SCALE), (Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE);
        root.getChildren().add(tileCanvas);

        double xoffset = Math.floor((Constants.WINDOW_WIDTH / 2) - (Constants.GAME_WIDTH / 2)) - (Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
        double yoffset = 0;
        tileCanvas.setLayoutX(xoffset);
        tileCanvas.setLayoutY(yoffset);
        backgroundCanvas.setLayoutX(xoffset - Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE);
        backgroundCanvas.setLayoutY(yoffset);

        tileGC = tileCanvas.getGraphicsContext2D();
        backgroundGC = backgroundCanvas.getGraphicsContext2D();

        // Setting up sprites

        characterSprites[0] = new CharacterSprite("ALYSSA");
        initCharacterSprites(characterSprites[0]);
        characterSprites[1] = new CharacterSprite("CHARLOTTE");
        initCharacterSprites(characterSprites[1]);
//        characterSprites[2] = new CharacterSprite("ZYPHER");
//        initCharacterSprites(characterSprites[2]);
//        characterSprites[3] = new CharacterSprite("MEI");
//        initCharacterSprites(characterSprites[3]);

        enemySprite = new CharacterSprite("ENEMY");
        initCharacterSprites(enemySprite);

        arenaBackgroundSprite = new Sprite("images/background/sprite-arenabackground-03.png");
        arenaFrameSprites.put(Compass.NORTH, new Sprite("images/background/sprite-frame-beach-north.png"));
        arenaFrameSprites.put(Compass.WEST, new Sprite("images/background/sprite-frame-beach-west.png"));
        arenaFrameSprites.put(Compass.EAST, new Sprite("images/background/sprite-frame-beach-east.png"));
        Image frameSouthSheet = new Image("images/background/spritesheet-frame-beach-south-402x54.png");
        AnimatedSprite frameSouthSprite =
                new AnimatedSprite(frameSouthSheet, new Dimension(402, 54), 4, 0.1, new int[] {0, 0}, new double[] {1, 1});
        arenaFrameSprites.put(Compass.SOUTH, frameSouthSprite);

        Image bombSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18_v2.png");

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
        ((AnimatedSprite)destructibleWallDestroyedSprite).setStartFromBeginning(true);
        indestructibleWallSprite =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 1, 1.0, new int[] {0, 1}, new double[] {1, 1});

        Image powerupSpritesheet = new Image("images/spritesheet-powerups-18x18.png");
        powerUpSprites.put(PowerUpState.HEALTH, new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 0}, new double[] {1, 1}));
        powerUpSprites.put(PowerUpState.BOMB_COUNT, new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 1}, new double[] {1, 1}));
        powerUpSprites.put(PowerUpState.RANGE, new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 2}, new double[] {1, 1}));
        powerUpSprites.put(PowerUpState.SPEED, new AnimatedSprite(powerupSpritesheet, new Dimension(18, 18), 8, 0.1, new int[] {0, 3}, new double[] {1, 1}));
    }

    public void initArena(StaticTile[][] arenaTiles) {

        // Rendering background image to background canvas

//        createBackground(backgroundGC);

        arenaBackgroundSprite.setPosition(Constants.DEFAULT_TILE_WIDTH * 2, Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        arenaBackgroundSprite.render(backgroundGC, -1);

        arenaFrameSprites.get(Compass.NORTH).setPosition(0, -Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.NORTH).setScale(Constants.GAME_SCALE);
        arenaFrameSprites.get(Compass.NORTH).render(backgroundGC, -1);

        arenaFrameSprites.get(Compass.WEST).setPosition(0, Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.WEST).setScale(Constants.GAME_SCALE);
        arenaFrameSprites.get(Compass.WEST).render(backgroundGC, -1);

        arenaFrameSprites.get(Compass.EAST).setPosition(Constants.DEFAULT_TILE_WIDTH * (2 + Constants.DEFAULT_GRID_WIDTH), Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.EAST).setScale(Constants.GAME_SCALE);
        arenaFrameSprites.get(Compass.EAST).render(backgroundGC, -1);

        arenaFrameSprites.get(Compass.SOUTH).setPosition(0, (Constants.DEFAULT_GRID_HEIGHT + 1) * Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
        arenaFrameSprites.get(Compass.SOUTH).render(backgroundGC, 0);

        // Rendering indestructible walls on background canvas
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
        hudView = new HUDView(root);
        scoreboard = new Scoreboard(root);
        //make players a proper arraylist of the current players
        //scoreboard.addPlayersToScoreboard(players);
    }

    private void createRandomBackgroundPattern() {
        int randomNum = 1 + (int)(Math.random() * 4 );
        backgroundPattern.setImage(new Image("images/backgroundPatterns/pattern"+randomNum+".png"));



    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        blastSpriteMap.put(placeBombEvent.getGridPosition(), new BlastSpriteCollection(placeBombEvent.getGridPosition(), placeBombEvent.getRange()));
    }

    public void updateStats(double health, double speed, int bombRange, int bombCount){
        hudView.updateStats(health, speed, bombRange, bombCount);
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

        arenaFrameSprites.get(Compass.SOUTH).render(backgroundGC, timeSinceStart);

        tileGC.clearRect(0, 0, Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 2 * Constants.GAME_SCALE), Constants.GAME_HEIGHT + ((Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE) + 1);

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

        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] != null && !(arenaTiles[i][j] instanceof IndestructibleWall)) {
                    Sprite tileSprite = getTileSprite(arenaTiles[i][j]);
                    if (tileSprite != null) {
                        tileSprite.setPosition(i * Constants.DEFAULT_TILE_WIDTH + Constants.DEFAULT_TILE_WIDTH, (j+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y);
                        tileSprite.render(tileGC, timeSinceStart);
                    }
                }
            }
        }

        for (int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof Blast && this.blastSpriteMap.get(new Point(i, j)) != null) {
                    renderBlast(this.blastSpriteMap.get(new Point(i, j)), timeSinceStart, arenaTiles);
                }
            }
        }

        for (Movable movable : arenaMovables) {

            if (movable instanceof GameCharacter) {
                renderCharacter(movable, timeSinceStart);

            } else if (movable instanceof Enemy) {
                renderCharacter(movable, timeSinceStart);
            }
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

    private void renderBlast(BlastSpriteCollection blastSpriteCollection, double timeSinceStart, StaticTile[][] arenaTiles) {

        if(!blastSpriteCollection.getBlastHasOccured()) {
            blastSpriteCollection.initBlast(arenaTiles);
        }

        Set<Point> keys = blastSpriteCollection.getSpriteMap().keySet();
        for (Point key : keys) {
            blastSpriteCollection.getSprite(new Point(key.x, key.y)).render(tileGC, timeSinceStart);
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
        } else if (tile instanceof StatPowerUp) {
            return powerUpSprites.get(((StatPowerUp) tile).getPowerUpState());
        }
        return null;
    }

    public void renderCharacter(Movable character, double timeSinceStart) {
        MovableState state = character.getState();
        CharacterSprite sprites = null;

        if (character instanceof GameCharacter) {
            for (int i = 0; i < 4; i++) {
                if (characterSprites[i] != null && characterSprites[i].getName().equals(character.getName())) {
                    sprites = characterSprites[i];
                }
            }
        } else if (character instanceof Enemy) {
            sprites = enemySprite;
        }

        if (sprites != null) {
            Sprite[] currentSprite = sprites.getIdleSprites(); // Always idle if no other state is active
            if (state == MovableState.WALK) {
                currentSprite = sprites.getWalkSprites();
            } else if (state == MovableState.FLINCH) {
                currentSprite = sprites.getFlinchSprites();
            } else if (state == MovableState.SPAWN) {
                currentSprite = sprites.getSpawnSprites();
                ((AnimatedSprite)currentSprite[0]).resetLoops();
            } else if (state == MovableState.DEATH) {
                currentSprite = sprites.getDeathSprites();
                ((AnimatedSprite)currentSprite[0]).resetLoops();
                ((AnimatedSprite)currentSprite[0]).setLoops(1);
            }

            int spriteIndex = 0;
            if (state == MovableState.SPAWN || state == MovableState.DEATH || character.getDirection() == Direction.DOWN) {
                spriteIndex = 0;
            } else if (character.getDirection() == Direction.LEFT) {
                spriteIndex = 1;
            } else if (character.getDirection() == Direction.RIGHT) {
                spriteIndex = 2;
            } else if (character.getDirection() == Direction.UP) {
                spriteIndex = 3;
            }

            Sprite actualSprite = currentSprite[spriteIndex];
            actualSprite.setPosition(character.getCanvasPositionX() + Constants.DEFAULT_TILE_WIDTH, character.getCanvasPositionY() + Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
            actualSprite.render(tileGC, timeSinceStart);
        }
    }



    /**
     * Reads character information from an XML-file and populate the instance variables for the sprites
     */
    private void initCharacterSprites(CharacterSprite characterSprite) {
        XMLReader reader = new XMLReader(Constants.GAME_CHARACTER_XML_FILE);
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), characterSprite.getName());

        for (MovableState characterState : Constants.CHARACTER_CHARACTER_STATE) {
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
