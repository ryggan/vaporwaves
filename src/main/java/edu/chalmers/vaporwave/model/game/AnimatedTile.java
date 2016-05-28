package edu.chalmers.vaporwave.model.game;

/**
 * An interface aside from head class StaticTile, to ensure that a specific tile class
 * can hold a timestamp, which is used to render a tile animation correctly.
 */
public interface AnimatedTile {
    double getTimeStamp();
}
