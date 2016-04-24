package edu.chalmers.vaporwave.view;


import java.awt.*;

import edu.chalmers.vaporwave.event.BlastTileInitDoneEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.gameObjects.DestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.IndestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;
import edu.chalmers.vaporwave.model.gameObjects.Wall;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Directions;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreascarlsson on 2016-04-23.
 */
public class BlastSpriteCollection {
    private Point position;
    private int range;
    private Map<Point, AnimatedSprite> spriteMap;
    private boolean blastHasOccured;
    private AnimatedSprite destructibleWallDestroyedSpriteLeft;
    private AnimatedSprite destructibleWallDestroyedSpriteUp;
    private AnimatedSprite destructibleWallDestroyedSpriteRight;
    private AnimatedSprite destructibleWallDestroyedSpriteDown;

    public BlastSpriteCollection(Point position, int range) {
        this.position = position;
        this.range = range;
        this.spriteMap = new HashMap<>();
        this.blastHasOccured = false;

        Image blastSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18.png");

        AnimatedSprite blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 4}, new double[]{1, 1});
        blastSprite.setLoops(1);
        blastSprite.setStartFromBeginning(true);
        blastSprite.setPosition(position.x * Constants.DEFAULT_TILE_WIDTH, position.y * Constants.DEFAULT_TILE_WIDTH);

        Image wallSpriteSheet = new Image("images/spritesheet-walls_both-18x18.png");
        destructibleWallDestroyedSpriteLeft =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 0}, new double[] {1, 1});
        destructibleWallDestroyedSpriteLeft.setLoops(1);
        destructibleWallDestroyedSpriteLeft.setStartFromBeginning(true);

        destructibleWallDestroyedSpriteUp =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 0}, new double[] {1, 1});
        destructibleWallDestroyedSpriteUp.setLoops(1);
        destructibleWallDestroyedSpriteUp.setStartFromBeginning(true);

        destructibleWallDestroyedSpriteRight =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 0}, new double[] {1, 1});
        destructibleWallDestroyedSpriteRight.setLoops(1);
        destructibleWallDestroyedSpriteRight.setStartFromBeginning(true);

        destructibleWallDestroyedSpriteDown =
                new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[] {1, 0}, new double[] {1, 1});
        destructibleWallDestroyedSpriteDown.setLoops(1);
        destructibleWallDestroyedSpriteDown.setStartFromBeginning(true);


        spriteMap.put(position, blastSprite);

        for (int i = 1; i <= range; i++) {
            if((position.x - i) >= 0) {
                if(i == range) {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 0}, new double[]{1, 1});
                } else {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 0}, new double[]{1, 1});
                }
                blastSprite.setLoops(1);
                blastSprite.setStartFromBeginning(true);
                blastSprite.setPosition((position.x - i) * Constants.DEFAULT_TILE_WIDTH, position.y * Constants.DEFAULT_TILE_WIDTH);
                spriteMap.put(new Point(position.x - i, position.y), blastSprite);
            }

            if((position.y - i) >= 0) {
                if(i == range) {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 1}, new double[]{1, 1});
                } else {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 1}, new double[]{1, 1});
                }
                blastSprite.setLoops(1);
                blastSprite.setStartFromBeginning(true);
                blastSprite.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y - i) * Constants.DEFAULT_TILE_WIDTH);
                spriteMap.put(new Point(position.x, position.y - i), blastSprite);
            }

            if(i == range) {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 2}, new double[]{1, 1});
            } else {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 2}, new double[]{1, 1});
            }
            blastSprite.setLoops(1);
            blastSprite.setStartFromBeginning(true);
            blastSprite.setPosition((position.x + i) * Constants.DEFAULT_TILE_WIDTH, position.y * Constants.DEFAULT_TILE_WIDTH);
            spriteMap.put(new Point(position.x + i, position.y), blastSprite);

            if(i == range) {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 3}, new double[]{1, 1});
            } else {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 3}, new double[]{1, 1});
            }
            blastSprite.setLoops(1);
            blastSprite.setStartFromBeginning(true);

            blastSprite.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y + i) * Constants.DEFAULT_TILE_WIDTH);
            spriteMap.put(new Point(position.x, position.y + i), blastSprite);
        }
    }

    public void initBlast(StaticTile[][] arenaTiles) {
        blastHasOccured = true;
        Map<Directions, Boolean> blastDirections = new HashMap<>();
        blastDirections.put(Directions.LEFT, true);
        blastDirections.put(Directions.UP, true);
        blastDirections.put(Directions.RIGHT, true);
        blastDirections.put(Directions.DOWN, true);

        for (int i = 1; i <= range; i++) {
            if ((position.x - i) >= 0 && (arenaTiles[position.x - i][position.y] instanceof Wall || !blastDirections.get(Directions.LEFT))) {
                if(blastDirections.get(Directions.LEFT)) {
                    blastDirections.put(Directions.LEFT, false);
                    if (arenaTiles[position.x - i][position.y] instanceof DestructibleWall) {
                        destructibleWallDestroyedSpriteLeft.setPosition((position.x - i) * Constants.DEFAULT_TILE_WIDTH, (position.y) * Constants.DEFAULT_TILE_WIDTH);
                        this.spriteMap.put(new Point(position.x - i, position.y), this.destructibleWallDestroyedSpriteLeft);
                    } else {
                        this.spriteMap.remove(new Point(position.x - i, position.y));
                    }
                } else {
                    this.spriteMap.remove(new Point(position.x - i, position.y));
                }
            }
            if ((position.y - i) >= 0 && (arenaTiles[position.x][position.y - i] instanceof Wall || !blastDirections.get(Directions.UP))) {
                if(blastDirections.get(Directions.UP)) {
                    blastDirections.put(Directions.UP, false);
                    if (arenaTiles[position.x][position.y - i] instanceof DestructibleWall) {
                        destructibleWallDestroyedSpriteUp.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y - i) * Constants.DEFAULT_TILE_WIDTH);
                        this.spriteMap.put(new Point(position.x, position.y - i), this.destructibleWallDestroyedSpriteUp);
                    } else {
                        this.spriteMap.remove(new Point(position.x, position.y - i));
                    }
                } else {
                    this.spriteMap.remove(new Point(position.x, position.y - i));
                }
            }
            if (position.x + i < arenaTiles.length && (arenaTiles[position.x + i][position.y] instanceof Wall || !blastDirections.get(Directions.RIGHT))) {
                if(blastDirections.get(Directions.RIGHT)) {
                    blastDirections.put(Directions.RIGHT, false);
                    if (arenaTiles[position.x + i][position.y] instanceof DestructibleWall) {
                        destructibleWallDestroyedSpriteRight.setPosition((position.x + i) * Constants.DEFAULT_TILE_WIDTH, (position.y) * Constants.DEFAULT_TILE_WIDTH);
                        this.spriteMap.put(new Point(position.x + i, position.y), this.destructibleWallDestroyedSpriteRight);
                    } else {
                        this.spriteMap.remove(new Point(position.x + i, position.y));
                    }
                } else {
                    this.spriteMap.remove(new Point(position.x + i, position.y));
                }
            }
            if (position.y + i < arenaTiles[0].length && (arenaTiles[position.x][position.y + i] instanceof Wall || !blastDirections.get(Directions.DOWN))) {
                if(blastDirections.get(Directions.DOWN)) {
                    blastDirections.put(Directions.DOWN, false);
                    if (arenaTiles[position.x][position.y + i] instanceof DestructibleWall) {
                        destructibleWallDestroyedSpriteDown.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y + i) * Constants.DEFAULT_TILE_WIDTH);
                        this.spriteMap.put(new Point(position.x, position.y + i), this.destructibleWallDestroyedSpriteDown);
                    } else {
                        this.spriteMap.remove(new Point(position.x, position.y + i));
                    }
                } else {
                    this.spriteMap.remove(new Point(position.x, position.y + i));
                }
            }
        }

        GameEventBus.getInstance().post(new BlastTileInitDoneEvent(position, range));
    }

    public Point getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    public Map<Point, AnimatedSprite> getSpriteMap() {
        return this.spriteMap;
    }

    public AnimatedSprite getSprite(Point position) {
        return this.spriteMap.get(position);
    }

    public boolean getBlastHasOccured() {
        return this.blastHasOccured;
    }

}
