package edu.chalmers.vaporwave.model.game;

/**
 * Created by bob on 2016-04-29.
 */
public class DoubleTile extends StaticTile {

    private StaticTile lowerTile;
    private StaticTile upperTile;

    public DoubleTile(StaticTile lowerTile, StaticTile upperTile) {
        this.lowerTile = lowerTile;
        this.upperTile = upperTile;
    }

    public StaticTile getLowerTile() {
        return this.lowerTile;
    }

    public StaticTile getUpperTile() {
        return this.upperTile;
    }

    public void setLowerTile(StaticTile lowerTile) {
        this.lowerTile = lowerTile;
    }

    public void setUpperTile(StaticTile upperTile) {
        this.upperTile = upperTile;
    }
}
