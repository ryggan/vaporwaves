package edu.chalmers.vaporwave.util;

/**
 * Created by bob on 2016-05-16.
 */
public class Pair<F,S> {

    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public String toString() {
        return "Pair [ "+first+", "+second+" ]";
    }
}
