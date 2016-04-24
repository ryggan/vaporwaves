package edu.chalmers.vaporwave.view;


import java.awt.*;

import edu.chalmers.vaporwave.model.gameObjects.IndestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;
import edu.chalmers.vaporwave.util.Constants;
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

    public BlastSpriteCollection(Point position, int range) {
        this.position = position;
        this.range = range;
        this.spriteMap = new HashMap<>();
        this.blastHasOccured = false;

        Image blastSpriteSheet = new Image("images/spritesheet-bombs_and_explosions-18x18.png");


        AnimatedSprite blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 4}, new double[]{1, 1});
        blastSprite.setLoops(1);
        blastSprite.setPosition(position.x * Constants.DEFAULT_TILE_WIDTH, position.y * Constants.DEFAULT_TILE_WIDTH);

        spriteMap.put(position, blastSprite);

        for (int i = 1; i <= range; i++) {
            if((position.x - i) >= 0) {
                if(i == range) {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 0}, new double[]{1, 1});
                } else {
                    blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 0}, new double[]{1, 1});
                }
                blastSprite.setLoops(1);
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
                blastSprite.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y - i) * Constants.DEFAULT_TILE_WIDTH);
                spriteMap.put(new Point(position.x, position.y - i), blastSprite);
            }

            if(i == range) {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 2}, new double[]{1, 1});
            } else {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 2}, new double[]{1, 1});
            }
            blastSprite.setLoops(1);
            blastSprite.setPosition((position.x + i) * Constants.DEFAULT_TILE_WIDTH, position.y * Constants.DEFAULT_TILE_WIDTH);
            spriteMap.put(new Point(position.x + i, position.y), blastSprite);

            if(i == range) {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{2, 3}, new double[]{1, 1});
            } else {
                blastSprite = new AnimatedSprite(blastSpriteSheet, new Dimension(18, 18), 7, 0.1, new int[]{9, 3}, new double[]{1, 1});
            }
            blastSprite.setLoops(1);

            blastSprite.setPosition((position.x) * Constants.DEFAULT_TILE_WIDTH, (position.y + i) * Constants.DEFAULT_TILE_WIDTH);
            spriteMap.put(new Point(position.x, position.y + i), blastSprite);
        }
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

}
