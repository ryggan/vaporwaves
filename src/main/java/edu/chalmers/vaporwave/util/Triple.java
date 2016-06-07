package edu.chalmers.vaporwave.util;

/**
 * Our own implementation of a so called "tuple". In other words, a simple way of
 * containing two different objects connected to eachother.
 */
public class Triple<F,S,T> {

    private F first;
    private S second;
    private T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public T getThird() {
        return this.third;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public void setThird(T third) {
        this.third = third;
    }

    public String toString() {
        return "Triple [ "+this.first+", "+this.second+", "+this.third+" ]";
    }
}
