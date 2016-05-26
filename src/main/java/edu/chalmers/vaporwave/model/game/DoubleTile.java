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

    // Recursive method to fetch the topmost instance of given class.
    public StaticTile getTile(Class type) {
        if (type.isInstance(upperTile)) {
            return upperTile;
        } else if (type.isInstance(lowerTile)) {
            return lowerTile;
        } else if (upperTile instanceof DoubleTile) {
            StaticTile tile = ((DoubleTile)upperTile).getTile(type);
            if (tile != null) {
                return tile;
            } else if (lowerTile instanceof DoubleTile) {
                return ((DoubleTile)lowerTile).getTile(type);
            }
        }
        return null;
    }

    public String toString() {
        return super.toString() + ": DoubleTile [ upper: "+upperTile.toString()+", lower: "+lowerTile.toString()+" ]";
    }
}
