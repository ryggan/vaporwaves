package edu.chalmers.vaporwave.util;

/**
 * Created by bob on 2016-04-15.
 *
 * Utility class, to get around the problem with inner classes and primitive variables.
 * Mainly used in MainController.AnimationTimer().
 */

public class LongValue {
    public long value;

    public LongValue(long n) {
        value = n;
    }
}
