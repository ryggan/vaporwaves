package edu.chalmers.vaporwave.util;

/**
 * Our own implementation of a so called "tuple". In other words, a simple way of
 * containing two different objects connected to eachother.
 */
public class Quad<F,S,T,Q> {

    private F first;
    private S second;
    private T third;
    private Q fourth;

    public Quad(F first, S second, T third, Q fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
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

    public Q getFourth() {
        return this.fourth;
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

    public void setFourth(Q fourth) {
        this.fourth = fourth;
    }

    public String toString() {
        return "Quad [ "+this.first+", "+this.second+", "+this.third+", "+this.fourth+" ]";
    }
}
