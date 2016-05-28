package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.event.AnimationFinishedEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;

/**
 * An extended version of Sprite that functions in much the same way, with the only addition
 * that animated picture now is supported, and several settings to come with it.
 * The animation takes snapshots from the sprite image in a consecutive manner, starting on a
 * specified position and then calculating the rest automatically.
 */
public class AnimatedSprite extends Sprite {

    private ArrayList<int[]> frames;
    private int length;
    private double duration;
    private double timeOffset;
    private boolean startFromBeginning;
    private boolean lingerOnLastFrame;
    private int loops; // -1 gives infinite loops
    private double startTime;
    private boolean playedYet;

    private boolean animationFinished;
    private AnimationFinishedEvent animationFinishedEvent;

    public AnimatedSprite(Image spriteSheet, Dimension spriteDimension, int length, double duration, int[] startPosition,
                          double[] offset, double scale) {

        super(spriteSheet, spriteDimension, startPosition, offset, scale);

        // Checking arguments, throwing exception if something is wrong
        if (spriteSheet == null || spriteDimension.getWidth() < 1 || spriteDimension.getHeight() < 1
                || length <= 0 || duration <= 0.0 || startPosition[0] < 0 || startPosition[1] < 0) {
            throw new IllegalArgumentException();
        }

        // Setting up variables
        this.frames = new ArrayList<>();
        this.length = length;
        this.duration = duration;
        this.timeOffset = 0;
        this.startFromBeginning = false;
        this.lingerOnLastFrame = false;
        this.animationFinished = false;
        this.loops = -1;
        this.startTime = 0;
        this.playedYet = false;

        Dimension sheetDimension = new Dimension((int)Math.floor(spriteSheet.getWidth() / spriteDimension.getWidth()),
                (int)Math.floor(spriteSheet.getHeight() / spriteDimension.getHeight()));

        // Initiating frames-list, by calculating every coordinate in the spritesheet
        int posx, posy;

        for (int i = 0; i < length; i++) {

            posx = startPosition[0] + i;
            posy = startPosition[1];

            while(posx >= sheetDimension.getWidth()) {
                posx -= sheetDimension.getWidth();
                posy++;
            }
            if (posy >= sheetDimension.getHeight()) {
                throw new IllegalArgumentException();
            }

            int[] frame = {posx, posy};
            frames.add(frame);
        }

    }
    public AnimatedSprite(Image spriteSheet, Dimension spriteDimension, int length, double duration, int[] startPosition, double[] offset) {
        this(spriteSheet, spriteDimension, length, duration, startPosition, offset, Constants.GAME_SCALE);
    }

    // This method is supposed to be used instead of the render(GraphicsContext gc) in the superclass, because
    // this one includes the calculation of which image is supposed to be shown, from the frames list.
    @Override
    public void render(GraphicsContext gc, double time) {

        this.playedYet = true;

        if (startFromBeginning && timeOffset == 0) {
            timeOffset = time;
        }

        if (loops != -1) {
            if (startTime == 0) {
                startTime = time;
            }
        }

        double timeToCheck = time - timeOffset;

        if (!animationFinished && loops != -1 && startTime != 0 && time - startTime > loops * (duration * length)) {
            this.animationFinished = true;
            if (this.animationFinishedEvent == null) {
                this.animationFinishedEvent = new AnimationFinishedEvent();
            }
            GameEventBus.getInstance().post(this.animationFinishedEvent);

        }

        int index = (int)((timeToCheck % (length * duration)) / duration);
        if (lingerOnLastFrame && animationFinished) {
            index = length - 1;
        }

        double width = getWidth() * getScale();
        double height = getHeight() * getScale();
        int sourcex = frames.get(index)[0] * (int)width;
        int sourcey = frames.get(index)[1] * (int)height;

        double targetx = (getPositionX() - getOffsetX()) * getScale();
        double targety = (getPositionY() - getOffsetY()) * getScale();
        if (getStayOnPixel()) {
            targetx = Math.round(targetx * getScale()) / getScale();
            targety = Math.round(targety * getScale()) / getScale();
        }

        if (lingerOnLastFrame || !animationFinished) {
            gc.drawImage(getImage(), sourcex, sourcey, width, height, targetx, targety, width, height);
        }
    }

    public void resetLoops() {
        startTime = 0;
        timeOffset = 0;
        playedYet = false;
        animationFinished = false;
    }

    @Override
    public String toString() {
        return "Animated " + super.toString() + " Length: " + length;
    }

    public int getLength() {
        return length;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setStartFromBeginning(boolean startFromBeginning) {
        this.startFromBeginning = startFromBeginning;
    }

    public void setLingerOnLastFrame(boolean lingerOnLastFrame) {
        this.lingerOnLastFrame = lingerOnLastFrame;
    }

    // if loops = -1, then infinite loops
    public void setLoops(int loops) {
        this.loops = loops;
    }

    public void setAnimationFinishedEvent(AnimationFinishedEvent animationFinishedEvent) {
        this.animationFinishedEvent = animationFinishedEvent;
    }

    public boolean getPlayedYet() {
        return this.playedYet;
    }

    public double getTotalTime() {
        return length * duration;
    }

    @Override
    public int hashCode(){
        int hash = super.hashCode() * 3;
        hash += this.length * 5;
        hash += this.duration * 7;
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if (super.equals(o) && o instanceof AnimatedSprite) {
            AnimatedSprite sprite = (AnimatedSprite) o;
            return (this.length == sprite.length && this.duration == sprite.duration);
        }
        return false;
    }
}
