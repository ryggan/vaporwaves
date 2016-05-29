package edu.chalmers.vaporwave.util;

/**
 * Our own implementation of a so called "tuple". In other words, a simple way of
 * containing two different objects connected to eachother.
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
