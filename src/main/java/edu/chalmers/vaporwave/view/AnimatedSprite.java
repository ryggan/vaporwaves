package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileNotFoundException;
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

    /**
     * Constructor that takes an Image object, and uses it as an spritesheed, with some help from the other
     * arguments, to create an animated image.
     * Below this are three versions, the first where startPos is omitted, in case the animation is supposed to be
     * asynchronous, and the last two similar constructors but with the parameter Image sprSheet exchanged with
     * a String fname paramater. The last two throws FileNotFoundExceptions.
     * @param spriteSheet
     * @param spriteDim
     * @param length
     * @param duration
     * @param startPos
     */
    public AnimatedSprite(Image spriteSheet, Dimension spriteDim, int length, double duration, int[] startPos) {

        // Checking arguments, throwing exception if something is wrong

        if (spriteSheet == null || spriteDim == null || spriteDim.getWidth() < 1 || spriteDim.getHeight() < 1 || length == 0
                || duration <= 0.0 || startPos[0] < 0 || startPos[1] < 0) {
            throw new IllegalArgumentException();
        }

        // Setting up variables

        this.frames = new ArrayList<int[]>();
        this.length = length;
        this.spriteSheet = spriteSheet;
        this.spriteDimension = spriteDim;
        this.duration = duration;

        setWidth(spriteDimension.getWidth());
        setHeight(spriteDimension.getHeight());

        this.sheetDimension = new Dimension((int)Math.floor(spriteSheet.getWidth() / spriteDimension.getWidth()),
                (int)Math.floor(spriteSheet.getHeight() / spriteDimension.getHeight()));

        // Initiating frames-list, by calculating every coordinate in the spritesheet

        int posx, posy;

        for (int i = 0; i < length; i++) {

            posx = startPos[0] + i;
            posy = startPos[1];

            while(posx >= sheetDimension.getWidth()) {
                posx -= sheetDimension.getWidth();
                posy++;
            }
            if (posy >= sheetDimension.getHeight())
                throw new IllegalArgumentException();

            int[] frame = {posx, posy};
            frames.add(frame);

//            System.out.println("posx: "+posx+" - posy: "+posy);
        }

    }
    public AnimatedSprite(Image sprSheet, Dimension sprDim, int length, double duration) {
        this(sprSheet, sprDim, length, duration, new int[] {0, 0});
    }
    public AnimatedSprite(String fileName, Dimension sprDim, int length, double duration, int[] startPos) throws FileNotFoundException {
        this(new Image(fileName), sprDim, length, duration, startPos);
    }
    public AnimatedSprite(String fileName, Dimension sprDim, int length, double duration) throws FileNotFoundException {
        this(new Image(fileName), sprDim, length, duration, new int[] {0, 0});
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
    public void render(GraphicsContext gc, double time) {
        int index = (int)((time % (length * duration)) / duration);
        double width = getWidth() * getScale();
        double height = getHeight() * getScale();
        int posx = frames.get(index)[0] * (int)width;
        int posy = frames.get(index)[1] * (int)height;
        gc.drawImage(spriteSheet, posx, posy, width, height, getPositionX(), getPositionY(), width, height);
    }
    @Override
    public void render(GraphicsContext gc) {

        // unused method, overridden so that super.render() never gets called accidentally

        System.out.println("Warning! AnimatedSprite.render(GrapgicsContext gc) is unused.");
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
}
