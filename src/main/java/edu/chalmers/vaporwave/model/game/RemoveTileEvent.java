package edu.chalmers.vaporwave.model.game;

import java.awt.*;

/**
 * This event is posted to the eventbus by ArenaView when a StaticTile should disappear
 * (generally when a animation is finished)
 */
public class RemoveTileEvent {

    private StaticTile tile;
    private Point gridPosition;

    public RemoveTileEvent(StaticTile tile, Point gridPosition) {
        this.tile = tile;
        this.gridPosition = gridPosition;
    }

    public StaticTile getTile() {
        return this.tile;
    }

    public Point getGridPosition() {
        return gridPosition;
    }

}
