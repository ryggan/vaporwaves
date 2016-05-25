package edu.chalmers.vaporwave.util;

/**
 * Utility class, to get around the problem with inner classes and primitive variables.
 * Mainly used in MainController.AnimationTimer().
 */
public class LongValue {
    public long value;

    public LongValue(long value) {
        this.value = value;
    }
}
