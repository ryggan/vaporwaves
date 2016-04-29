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

    // Recursive method to fetch the topmost blast instance.
    // todo: Maybe refactor and remove method above?
    public Blast getBlast() {
        if (upperTile instanceof Blast) {
            return (Blast)upperTile;
        } else if (lowerTile instanceof Blast) {
            return (Blast)lowerTile;
        } else if (upperTile instanceof DoubleTile) {
            Blast blast = ((DoubleTile)upperTile).getBlast();
            if (blast != null) {
                return blast;
            } else if (lowerTile instanceof DoubleTile) {
                return ((DoubleTile)lowerTile).getBlast();
            }
        }
        return null;
    }
}
