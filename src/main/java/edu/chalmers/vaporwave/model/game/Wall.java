package edu.chalmers.vaporwave.model.game;

/**
 * All the walls.
 */
public abstract class Wall extends StaticTile {

    public String toString() {
        return super.toString() + ": Wall";
    }
}
