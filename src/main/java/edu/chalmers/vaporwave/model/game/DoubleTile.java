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

    // Special recursive method to check if there is a blast in a tree of DoubleTiles
    public boolean containBlast() {
        if (lowerTile instanceof Blast || upperTile instanceof Blast) {
            return true;
        } else if (lowerTile instanceof DoubleTile && ((DoubleTile)lowerTile).containBlast()) {
            return true;
        } else if (upperTile instanceof DoubleTile && ((DoubleTile)upperTile).containBlast()) {
            return true;
        }
        return false;
    }
}
