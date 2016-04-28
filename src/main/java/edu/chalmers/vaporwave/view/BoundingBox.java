package edu.chalmers.vaporwave.view;

public class BoundingBox {

    private double boundingBoxNorth;
    private double boundingBoxSouth;
    private double boundingBoxEast;
    private double boundingBoxWest;

    public BoundingBox() {
        boundingBoxNorth = 0;
        boundingBoxSouth = 0;
        boundingBoxEast = 0;
        boundingBoxWest = 0;
    }

    public BoundingBox(double boundingBoxNorth, double boundingBoxSouth, double boundingBoxEast, double boundingBoxWest) {
        this.boundingBoxNorth = boundingBoxNorth;
        this.boundingBoxSouth = boundingBoxSouth;
        this.boundingBoxEast = boundingBoxEast;
        this.boundingBoxWest = boundingBoxWest;
    }

    public void setBox(double boundingBoxNorth, double boundingBoxSouth, double boundingBoxEast, double boundingBoxWest) {
        this.boundingBoxNorth = boundingBoxNorth;
        this.boundingBoxSouth = boundingBoxSouth;
        this.boundingBoxEast = boundingBoxEast;
        this.boundingBoxWest = boundingBoxWest;
    }

    public double getNorth() {
        return this.boundingBoxNorth;
    }
    public double getSouth() {
        return this.boundingBoxSouth;
    }
    public double getEast() {
        return this.boundingBoxEast;
    }
    public double getWest() {
        return this.boundingBoxWest;
    }
}
