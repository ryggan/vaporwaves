package edu.chalmers.vaporwave.model.game;

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

    // Recursive method to check if there is a tile of give class in a tree of DoubleTiles
    public boolean contains(Class type) {
        if (type.isInstance(lowerTile) || type.isInstance(upperTile)) {
            return true;
        } else if (lowerTile instanceof DoubleTile && ((DoubleTile)lowerTile).contains(type)) {
            return true;
        } else if (upperTile instanceof DoubleTile && ((DoubleTile)upperTile).contains(type)) {
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
