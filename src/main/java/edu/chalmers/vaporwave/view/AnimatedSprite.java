package edu.chalmers.vaporwave.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by bob on 2016-04-15.
 */
public class AnimatedSprite extends Sprite {

    private ArrayList<int[]> frames;
    private int length;
    private Image spriteSheet;
    private Dimension spriteDimension;
    private Dimension sheetDimension;
    private double duration;

    /**
     * Constructor that takes an Image object, and uses it as an spritesheed, with some help from the other
     * arguments, to create an animated image.
     * Below this are three versions, the first where startPos is omitted, in case the animation is supposed to be
     * asynchronous, and the last two similar constructors but with the parameter Image sprSheet exchanged with
     * a String fname paramater. The last two throws FileNotFoundExceptions.
     * @param sprSheet
     * @param sprDim
     * @param length
     * @param duration
     * @param startPos
     */
    public AnimatedSprite(Image sprSheet, Dimension sprDim, int length, double duration, int[] startPos) {

        // Checking arguments

        if (sprSheet == null || sprDim == null || sprDim.getWidth() < 1 || sprDim.getHeight() < 1 || length == 0
                || duration <= 0.0 || startPos[0] < 0 || startPos[1] < 0) {
            throw new IllegalArgumentException();
        }

        // Setting up variables

        this.frames = new ArrayList<int[]>();
        this.length = length;
        this.spriteSheet = sprSheet;
        this.spriteDimension = sprDim;
        this.duration = duration;

        setWidth(spriteDimension.getWidth());
        setHeight(spriteDimension.getHeight());

        this.sheetDimension = new Dimension((int)Math.floor(spriteSheet.getWidth() / spriteDimension.getWidth()),
                (int)Math.floor(spriteSheet.getHeight() / spriteDimension.getHeight()));

        // Initiating frames-list, by calculating every coordinate in the spritesheet

        int posx = startPos[0];
        int posy = startPos[1];
//        int posx, posy;
        for (int i = 0; i < length; i++) {
//            posx = startPos[0] + i;
//            posy = startPos[1];
            posx++;
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
    public AnimatedSprite(Image sprSheet, Dimension sprDim, int length, double duration) {
        this(sprSheet, sprDim, length, duration, new int[] {0, 0});
    }
    public AnimatedSprite(String fname, Dimension sprDim, int length, double duration, int[] startPos) throws FileNotFoundException {
        this(new Image(fname), sprDim, length, duration, startPos);
    }
    public AnimatedSprite(String fname, Dimension sprDim, int length, double duration) throws FileNotFoundException {
        this(new Image(fname), sprDim, length, duration, new int[] {0, 0});
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
     * This method is supposed to be used instead of the render(GraphicsContext gc) in the superclass, because
     * this one includes the calculation of which image is supposed to be shown, from the frames list.
     * @param gc
     * @param time
     */
    public void render(GraphicsContext gc, double time) {
        int index = (int)((time % (length * duration)) / duration);

        gc.drawImage(spriteSheet, frames.get(index)[0], frames.get(index)[1], getWidth(), getHeight(),
                getPositionX(), getPositionY(), getWidth(), getHeight());
    }
    @Override
    public void render(GraphicsContext gc) {
        // unused method, overridden so that super.render() never gets called accidentally
    }

    public int getLength() {
        return length;
    }

    public void setScale() {

    }
}
