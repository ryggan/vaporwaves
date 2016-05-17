package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.event.AnimationFinishedEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.game.AnimatedTile;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * An extended version of Sprite that functions in much the same way, with the only addition
 * that animated picture now is supported.
 *
 * Created by bob on 2016-04-15.
 */
public class AnimatedSprite extends Sprite {

    private ArrayList<int[]> frames;
    private int length;
    private Image originalSpriteSheet;
    private Image spriteSheet;
//    private Dimension sheetDimension;
    private double duration;
    private double timeOffset;
    private boolean startFromBeginning;
    private boolean lingerOnLastFrame;
    private int loops; // -1 gives infinite loops
    private double startTime;
    private boolean playedYet;

    private boolean animationFinished;
    private AnimationFinishedEvent animationFinishedEvent;

    /**
     * Constructor that takes an Image object, and uses it as an spritesheed, with some help from the other
     * arguments, to create an animated image.
     * Below this are three versions, the first where startPos is omitted, in case the animation is supposed to be
     * asynchronous, and the last two similar constructors but with the parameter Image sprSheet exchanged with
     * a String fname paramater. The last two throws FileNotFoundExceptions.
     * @param spriteSheet
     * @param spriteDimension
     * @param length
     * @param duration
     * @param startPosition
     */
    public AnimatedSprite(Image spriteSheet, Dimension spriteDimension, int length, double duration, int[] startPosition, double[] offset, double scale) {

        // Checking arguments, throwing exception if something is wrong

        if (spriteSheet == null || spriteDimension == null || spriteDimension.getWidth() < 1 || spriteDimension.getHeight() < 1
                || length <= 0 || duration <= 0.0 || startPosition[0] < 0 || startPosition[1] < 0) {
            throw new IllegalArgumentException();
        }

        // Setting up variables

        this.frames = new ArrayList<>();

        this.originalSpriteSheet = spriteSheet;
        this.spriteSheet = spriteSheet;
        this.length = length;
        this.duration = duration;

        this.timeOffset = 0;
        this.startFromBeginning = false;
        this.lingerOnLastFrame = false;
        this.animationFinished = false;
        this.loops = -1;
        this.startTime = 0;
        this.playedYet = false;


        setWidth(spriteDimension.getWidth());
        setHeight(spriteDimension.getHeight());

        Dimension sheetDimension = new Dimension((int)Math.floor(spriteSheet.getWidth() / spriteDimension.getWidth()),
                (int)Math.floor(spriteSheet.getHeight() / spriteDimension.getHeight()));

        setOffsetXY(offset[0], offset[1]);
        setScale(scale);

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
    public AnimatedSprite(String fileName, Dimension spriteDimension, int length, double duration, int[] startPosition, double[] offset) {
        this(new Image(fileName), spriteDimension, length, duration, startPosition, offset);
    }

    /**
     * Sets the coordinates in the spritesheet for a specific frame in the frames list.
     * @param frameNum
     * @param posx
     * @param posy
     */
    public void setFramePos(int frameNum, int posx, int posy) {
        int[] frame = {posx, posy};
        frames.set(frameNum, frame);
    }

    /**
     * Besides setting the scale, also updates the Image, via Utils, to the new resized scale.
     * A slightly changed setScale() overridden, since the setImage() function is unused in AnimatedSprite.
     * @param scale
     */
    @Override
    public void setScale(double scale) {
        boolean differentScale = (scale != getScale());
        super.setScale(scale);
        if (differentScale) {
            setSpriteSheet(this.originalSpriteSheet);
        }
    }

    /**
     * This method is supposed to be used instead of the render(GraphicsContext gc) in the superclass, because
     * this one includes the calculation of which image is supposed to be shown, from the frames list.
     * @param gc
     * @param time
     */
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
            index = length-1;
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
            gc.drawImage(spriteSheet, sourcex, sourcey, width, height, targetx, targety, width, height);
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
        return "Animated"+super.toString() + " Length: "+length;
    }

    // GETTERS AND SETTERS:

    public void setSpriteSheet(Image spriteSheet) {
        this.originalSpriteSheet = spriteSheet;
        if (spriteSheet != null) {
            if (getScale() == 1.0) {
                this.spriteSheet = this.originalSpriteSheet;
            } else {
                this.spriteSheet = Utils.resize(this.originalSpriteSheet, getScale());
            }
        }
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

    public boolean isAnimationFinished() {
        return this.animationFinished;
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
        int hash = 17 + super.hashCode();
        hash += this.length * 31;
        hash += this.duration * 73;
        hash += this.originalSpriteSheet.hashCode() * 57;
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if (super.equals(o) && o instanceof AnimatedSprite) {
            AnimatedSprite sprite = (AnimatedSprite) o;
            return (this.length == sprite.length && this.duration == sprite.duration
                    && this.originalSpriteSheet == sprite.originalSpriteSheet);
        }
        return false;
    }
}
