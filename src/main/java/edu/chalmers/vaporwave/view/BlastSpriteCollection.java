package edu.chalmers.vaporwave.view;


import java.awt.*;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.BlastTileInitDoneEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.gameObjects.DestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;
import edu.chalmers.vaporwave.model.gameObjects.Wall;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class BlastSpriteCollection {
    private Point position;
    private int range;
    private Map<Point, AnimatedSprite> spriteMap;
    private Map<Direction, AnimatedSprite> destructibleWallDestroyedSprites;
    private Map<Direction, Boolean> blastDirections;
    private boolean blastHasOccured;

    public BlastSpriteCollection(Point position, int range) {
        this.position = position;
        this.range = range;
        this.spriteMap = new HashMap<>();
        this.destructibleWallDestroyedSprites = new HashMap<>(4);
        this.blastDirections = new HashMap<>();
        this.blastDirections.put(Direction.LEFT, true);
        this.blastDirections.put(Direction.UP, true);
        this.blastDirections.put(Direction.RIGHT, true);
        this.blastDirections.put(Direction.DOWN, true);
        this.blastHasOccured = false;

        Image blastSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18_v2.png");

        AnimatedSprite blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 4}, new double[]{1, 1});
        blastSprite.setLoops(1);
        blastSprite.setStartFromBeginning(true);
        blastSprite.setPosition((position.x+1) * Constants.DEFAULT_TILE_WIDTH, (position.y+1) * Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);

        Image wallSpriteSheet = new Image("images/spritesheet-walls_both-18x18.png");

        for (Direction direction : this.blastDirections.keySet()) {
            AnimatedSprite destructibleWallDestroyedSprite =
                    new AnimatedSprite(wallSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{1, 0}, new double[]{1, 1});
            destructibleWallDestroyedSprite.setLoops(1);
            destructibleWallDestroyedSprite.setStartFromBeginning(true);
            this.destructibleWallDestroyedSprites.put(direction, destructibleWallDestroyedSprite);
        }

        spriteMap.put(position, blastSprite);

        Map<Direction, Integer> spriteBlasts = new HashMap<>(4);
        spriteBlasts.put(Direction.LEFT, 0);
        spriteBlasts.put(Direction.UP, 1);
        spriteBlasts.put(Direction.RIGHT, 2);
        spriteBlasts.put(Direction.DOWN, 3);

        for (int i = 1; i <= range; i++) {
            for (Direction direction : this.blastDirections.keySet()) {
                Point currentPosition = new Point(Utils.getRelativePoint(this.getPosition(), i, direction));
                if(!(position.x - i < 0 && direction.equals(Direction.LEFT) && !(position.y - i < 0 && direction.equals(Direction.UP)))) {
                    if(i == range) {
                        blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, spriteBlasts.get(direction)}, new double[]{1, 1});
                    } else {
                        blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, spriteBlasts.get(direction)}, new double[]{1, 1});
                    }
                    blastSprite.setLoops(1);
                    blastSprite.setStartFromBeginning(true);
                    if (currentPosition.x >= 0 &&
                            currentPosition.y >= 0 &&
                            currentPosition.x < Constants.DEFAULT_GRID_WIDTH &&
                            currentPosition.y < Constants.DEFAULT_GRID_HEIGHT) {
                        blastSprite.setPosition((currentPosition.x+1) * Constants.DEFAULT_TILE_WIDTH, (currentPosition.y+1) * Constants.DEFAULT_TILE_HEIGHT + Constants.GRID_OFFSET_Y);
                        spriteMap.put(currentPosition, blastSprite);
                    }
                }
            }
        }
    }

    public void initBlast(StaticTile[][] arenaTiles) {
        blastHasOccured = true;

        for (int i = 1; i <= range; i++) {
            for (Direction direction : this.blastDirections.keySet()) {

                Point currentPosition = Utils.getRelativePoint(this.getPosition(), i, direction);
                if (isValidPosition(currentPosition, arenaTiles) && arenaTiles[currentPosition.x][currentPosition.y] instanceof Wall || !blastDirections.get(direction)) {
                    if (this.blastDirections.get(direction)) {
                        this.blastDirections.put(direction, false);
                        if (arenaTiles[currentPosition.x][currentPosition.y] instanceof DestructibleWall) {
                            destructibleWallDestroyedSprites.get(direction).setPosition((currentPosition.x+1) * Constants.DEFAULT_TILE_WIDTH, (currentPosition.y+1) * Constants.DEFAULT_TILE_WIDTH + Constants.GRID_OFFSET_Y);
                            this.spriteMap.put(currentPosition, this.destructibleWallDestroyedSprites.get(direction));
                        } else {
                            this.spriteMap.remove(currentPosition);
                        }
                    } else {
                        this.spriteMap.remove(currentPosition);
                    }
                }
            }
        }

        // Posting to GameEventBus
        GameEventBus.getInstance().post(new BlastTileInitDoneEvent(position, range));
    }

    /**
     * Check if a specific position is within the bounds of the ArenaModel.
     *
     * @param position The position to check
     * @return true if position is within bounds, otherwise false.
     */
    private boolean isValidPosition(Point position, StaticTile[][] arenaTiles) {
        return position.x >= 0 &&
                position.y >= 0 &&
                position.x < arenaTiles.length &&
                position.y < arenaTiles[0].length;
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
