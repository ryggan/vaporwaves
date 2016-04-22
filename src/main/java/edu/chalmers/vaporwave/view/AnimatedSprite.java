package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;

/**
 * An extended version of Sprite that functions in much the same way, with the only addition
 * that animated picture now is supported.
 *
 * Created by bob on 2016-04-15.
 */
public class AnimatedSprite extends Sprite {

    private ArrayList<int[]> frames;
    private int length;
    private Image spriteSheet;
    private Dimension spriteDimension;
    private Dimension sheetDimension;

    private double duration;
    private double timeOffset;
    private boolean startFromBeginning;
    private boolean runAnimation;
    private int loops; // -1 gives infinite loops
    private double startTime;

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
    public AnimatedSprite(Image spriteSheet, Dimension spriteDimension, int length, double duration, int[] startPosition, double[] offset) {

        // Checking arguments, throwing exception if something is wrong

        if (spriteSheet == null || spriteDimension == null || spriteDimension.getWidth() < 1 || spriteDimension.getHeight() < 1
                || length <= 0 || duration <= 0.0 || startPosition[0] < 0 || startPosition[1] < 0) {
            throw new IllegalArgumentException();
        }

        // Setting up variables

        this.frames = new ArrayList<int[]>();
        this.length = length;
        this.spriteSheet = spriteSheet;
        this.spriteDimension = spriteDimension;
        this.duration = duration;

        this.timeOffset = 0;
        this.startFromBeginning = false;
        this.runAnimation = true;
        this.loops = -1;
        this.startTime = 0;

        setWidth(spriteDimension.getWidth());
        setHeight(spriteDimension.getHeight());

        this.sheetDimension = new Dimension((int)Math.floor(spriteSheet.getWidth() / spriteDimension.getWidth()),
                (int)Math.floor(spriteSheet.getHeight() / spriteDimension.getHeight()));

        setOffset(offset[0], offset[1]);
        setScale(Constants.GAME_SCALE);

        // Initiating frames-list, by calculating every coordinate in the spritesheet

        int posx, posy;

        for (int i = 0; i < length; i++) {

            posx = startPosition[0] + i;
            posy = startPosition[1];

            while(posx >= sheetDimension.getWidth()) {
                posx -= sheetDimension.getWidth();
                posy++;
            }
            if (posy >= sheetDimension.getHeight())
                throw new IllegalArgumentException();

            int[] frame = {posx, posy};
            frames.add(frame);
        }

    }
    public AnimatedSprite(String fileName, Dimension spriteDimension, int length, double duration, int[] startPosition, double[] offset) {
        this(new Image(fileName), spriteDimension, length, duration, startPosition, offset);
    }

    public AnimatedSprite(AnimatedSprite sprite) {
        for(int[] i : sprite.frames) {
            int[] frame = {i[0], i[1]};
            frames.add(frame);
        }

        this.length = sprite.length;
        this.spriteSheet = sprite.spriteSheet;
        this.spriteDimension = sprite.spriteDimension;
        this.duration = sprite.duration;

        setWidth(sprite.spriteDimension.getWidth());
        setHeight(sprite.spriteDimension.getHeight());

        this.sheetDimension = sprite.sheetDimension;
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
        super.setScale(scale);
        setSpriteSheet(Utils.resize(this.spriteSheet, scale));
    }

    /**
     * This method is supposed to be used instead of the render(GraphicsContext gc) in the superclass, because
     * this one includes the calculation of which image is supposed to be shown, from the frames list.
     * @param gc
     * @param time
     */
    @Override
    public void render(GraphicsContext gc, double time) {

        if (startFromBeginning && timeOffset == 0) {
            timeOffset = time;
        }

        if (loops != -1) {
            if (startTime == 0) {
                startTime = time;
            }
//            else if (time - startTime > loops * (duration * length)) {
//                System.out.println("Resetting loops?");
//                resetLoops();
//            }
        }

        double timeToCheck = time - timeOffset;

        int index = (int)((timeToCheck % (length * duration)) / duration);
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

        if (loops != -1 && startTime != 0 && time - startTime > loops * (duration * length)) {
            runAnimation = false;
            // todo: here be some kind of listener when loops are done, if needed
        }

        if (runAnimation) {
            gc.drawImage(spriteSheet, sourcex, sourcey, width, height, targetx, targety, width, height);
        }
    }

    public void resetLoops() {
        runAnimation = true;
        startTime = 0;
    }

    @Override
    public String toString() {
        return "Animated "+super.toString() + " Length: "+length;
    }

    // GETTERS AND SETTERS:

    public void setSpriteSheet(Image spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public int getLength() {
        return length;
    }

    public void setStartFromBeginning(boolean startFromBeginning) {
        this.startFromBeginning = startFromBeginning;
    }

    // if loops = -1, then infinite loops
    public void setLoops(int loops) {
        this.loops = loops;
    }
}
