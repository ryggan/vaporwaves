package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Sprite {

    private Image image;
    private Image originalImage;

    private int sourceGridX;
    private int sourceGridY;
    private double sourceCanvasX;
    private double sourceCanvasY;
    private double width;
    private double height;

    private double positionX;
    private double positionY;
    private double offsetX;
    private double offsetY;
    private double offsetWidth;
    private double offsetHeight;

    private double scale;
    private boolean stayOnPixel;

    /**
     * Constructors, one simple which leaves the Sprite object without Image, and the other two with an Image
     * parameter respectively a file name String parameter.
     */
    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.scale = 1;
        this.stayOnPixel = true;
        setOffsetDimension(0, 0);
    }

    public Sprite(Image spriteSheet, Dimension dimension, int[] sourceGridPosition, double[] offset, double scale) {
        this();

        setImage(spriteSheet);
        setScale(scale);
        setSourceGridXY(sourceGridPosition[0], sourceGridPosition[1]);
        setSourceCanvasXY(0, 0);
        setDimension(dimension.getWidth(), dimension.getHeight());
        setOffsetXY(offset[0], offset[1]);
    }

    public Sprite(Image spriteSheet, Dimension dimension, double[] sourceCanvasPosition, double[] offset, double scale) {
        this();

        setImage(spriteSheet);
        setScale(scale);
        setSourceGridXY(0, 0);
        setSourceCanvasXY(sourceCanvasPosition[0], sourceCanvasPosition[1]);
        setDimension(dimension.getWidth(), dimension.getHeight());
        setOffsetXY(offset[0], offset[1]);
    }

    public Sprite(Image spriteSheet, Dimension spriteDimension, int[] startPosition, double[] offset) {
        this(spriteSheet, spriteDimension, startPosition, offset, Constants.GAME_SCALE);
    }

    public Sprite(Image image, double scale) {
        this(image, new Dimension((int)image.getWidth(), (int)image.getHeight()), new int[] {0, 0}, new double[] {0, 0}, scale);
    }

    public Sprite(Image image) {
        this(image, Constants.GAME_SCALE);
    }

//    public Sprite(Sprite sprite) {
//        this();
//        this.scale = sprite.getScale();
//        this.stayOnPixel = sprite.getStayOnPixel();
//        setImage(sprite.getImage());
//    }

    // Besides setting the scale, also updates the Image, via Utils, to the new resized scale (when necessary).
    public void setScale(double scale) {
        boolean differentScale = (scale != this.scale);
        this.scale = scale;
        if (differentScale) {
            setImage(this.originalImage);
        }
    }

    public double getScale() {
        return this.scale;
    }

    public void setImage(Image image) {
        this.originalImage = image;
        if (image != null) {
            if (this.scale == 1.0) {
                this.image = this.originalImage;
            } else {
                this.image = Utils.resize(this.originalImage, this.scale);
            }
        }
    }

    public void setImage(String fileName) {
        Image image = new Image(fileName);
        this.setImage(image);
    }

    public Image getImage() {
        return this.image;
    }
    // Draws the sprites image on canvas at the right position.
    public void render(GraphicsContext gc, double time) {

        double width = (this.width - this.offsetWidth) * this.scale;
        double height = (this.height - this.offsetHeight) * this.scale;
        double sourcex = this.sourceCanvasX + this.sourceGridX * width;
        double sourcey = this.sourceCanvasY + this.sourceGridY * height;

        double targetx = (this.positionX - this.offsetX) * this.scale;
        double targety = (this.positionY - this.offsetY) * this.scale;
        if (stayOnPixel) {
            targetx = Math.round(targetx * scale) / scale;
            targety = Math.round(targety * scale) / scale;
        }

        gc.drawImage(this.image, sourcex, sourcey, width, height, targetx, targety, width, height);
    }

    // GETTERS AND SETTERS:

    public void setStayOnPixel(boolean stayOnPixel) {
        this.stayOnPixel = stayOnPixel;
    }

    public boolean getStayOnPixel() {
        return stayOnPixel;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void setPosition(Point point) {
        this.setPosition(point.getX(), point.getY());
    }

    public void setDimension(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }

    public double getPositionX() {
        return this.positionX;
    }
    public double getPositionY() {
        return this.positionY;
    }

    public void setOffsetXY(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return this.offsetX;
    }
    public double getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetDimension(double offsetWidth, double offsetHeight) {
        this.offsetWidth = offsetWidth;
        this.offsetHeight = offsetHeight;
    }

    public double getOffsetWidth() {
        return this.offsetWidth;
    }
    public double getOffsetHeight() {
        return this.offsetHeight;
    }

    public void setSourceGridXY(int sourceGridX, int sourceGridY) {
        this.sourceGridX = sourceGridX;
        this.sourceGridY = sourceGridY;
    }

    public int getSourceGridX() {
        return this.sourceGridX;
    }
    public int getSourceGridY() {
        return this.sourceGridY;
    }

    public void setSourceCanvasXY(double sourceCanvasX, double sourceCanvasY) {
        this.sourceCanvasX = sourceCanvasX;
        this.sourceCanvasY = sourceCanvasY;
    }

    public double getSourceCanvasX() {
        return this.sourceCanvasX;
    }
    public double getSourceCanvasY() {
        return this.sourceCanvasY;
    }

    // Standard methods
    public String toString() {
        return "Sprite - Position: [" + positionX + "," + positionY + "]" + "Width:[" + width +"," + height + "]";
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Sprite){
            Sprite other = (Sprite) o;
            return (this.hashCode() == other.hashCode()
                    && this.originalImage == other.originalImage
                    && Math.round(this.width) == Math.round(other.width)
                    && Math.round(this.height) == Math.round(other.height)
                    && Math.round(this.offsetX) == Math.round(this.offsetX)
                    && Math.round(this.offsetY) == Math.round(this.offsetY)
                    && Math.round(this.positionX) == Math.round(this.positionX)
                    && Math.round(this.positionY) == Math.round(this.positionY)
                    && this.stayOnPixel == other.stayOnPixel);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 1 + (int) positionX*23;
        hash = hash + (int) positionY*17;
        hash = hash + (int) width*36;
        hash = hash + (int) height*42;
        hash = hash + (int) scale*13;
        hash = hash + (int) offsetX*34;
        hash = hash + (int) offsetY*35;
        hash = hash + image.hashCode();
        return hash;
    }

}
