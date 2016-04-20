package edu.chalmers.vaporwave.model;

/**
 * Created by andreascarlsson on 2016-04-19.
 */
public class SpriteProperties {
    private String state;
    private int xDimension;
    private int yDimension;
    private int frames;
    private double duration;
    private int[] firstFrame;

    public SpriteProperties(String state, int xDimension, int yDimension, int frames, double duration, int[] firstFrame) {
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
        return this.getFirstFrame();
    }

}
