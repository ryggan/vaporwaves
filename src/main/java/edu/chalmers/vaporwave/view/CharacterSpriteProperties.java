package edu.chalmers.vaporwave.view;

import javafx.scene.image.Image;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class CharacterSpriteProperties {
    private String state;
    private Image spritesheet;
    private int dimensionX;
    private int dimensionY;
    private int frames;
    private double duration;
    private int[] firstFrame;
    private double[] offset;

    public CharacterSpriteProperties(String state, Image spritesheet, int dimensionX, int dimensionY, int frames, double duration, int[] firstFrame, double[] offset) {
        this.spritesheet = spritesheet;
        this.state = state;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.frames = frames;
        this.duration = duration;
        this.firstFrame = firstFrame;
        this.offset = offset;
    }

    public String getState() {
        return this.state;
    }

    public Image getSpritesheet() {
        return this.spritesheet;
    }

    public int getDimensionX() {
        return this.dimensionX;
    }

    public int getDimensionY() {
        return this.dimensionY;
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

    public double[] getOffset() {
        return this.offset;
    }

    public String toString() {
        return "state: " + getState() +
                "\nspritesheet: " + getSpritesheet() +
                "\nxDimension: " + getDimensionX() +
                "\nyDimension: " + getDimensionY() +
                "\nframes: " + getFrames() +
                "\nduration: " + getDuration() +
                "\nfirstFrame: " + getFirstFrame()[0] + ", " + getFirstFrame()[1] +
                "\noffset: " + getOffset()[0] + ", " + getOffset()[1];
    }

}
