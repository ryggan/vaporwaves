package edu.chalmers.vaporwave.model;

import javafx.scene.image.Image;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class SpriteProperties {
    private String state;
    private Image spritesheet;
    private int xDimension;
    private int yDimension;
    private int frames;
    private double duration;
    private int[] firstFrame;

    public SpriteProperties(String state, Image spritesheet, int xDimension, int yDimension, int frames, double duration, int[] firstFrame) {
        this.spritesheet = spritesheet;
        this.state = state;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.frames = frames;
        this.duration = duration;
        this.firstFrame = firstFrame;
    }

    public String getState() {
        return this.state;
    }

    public Image getSpritesheet() {
        return this.spritesheet;
    }

    public int getxDimension() {
        return this.xDimension;
    }

    public int getyDimension() {
        return this.yDimension;
    }

    public int getFrames() {
        return this.frames;
    }

    public double getDuration() {
        return this.duration;
    }

    public int[] getFirstFrame() {
        return this.firstFrame;
    }

    public String toString() {
        return "state: " + getState() +
                "\nspritesheet: " + getSpritesheet() +
                "\nxDimension: " + getxDimension() +
                "\nyDimension: " + getyDimension() +
                "\nframes: " + getFrames() +
                "\nduration: " + getDuration() +
                "\nfirstFrame: " + getFirstFrame()[0] + ", " + getFirstFrame()[1];
    }

}
