package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Sprite {

    private Image image;
    private Image originalImage;
    private double positionX;
    private double positionY;
    private double width;
    private double height;
    private double scale;
    private boolean stayOnPixel;
    private double offsetX;
    private double offsetY;

    /**
     * Constructors, one simple which leaves the Sprite object without Image, and the other two with an Image
     * parameter respectively a file name String parameter.
     */
    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.scale = Constants.GAME_SCALE;
        this.stayOnPixel = true;
    }

    public Sprite(Image image) {
        this();
        setImage(image);
    }

    public Sprite(String fileName) {
        this();
        setImage(fileName);
    }

    public Sprite(Sprite sprite) {
        this();
        this.scale = sprite.getScale();
        this.stayOnPixel = sprite.getStayOnPixel();
        setImage(sprite.getImage());
        setImage(sprite.getImage());
    }

    public void setImage(Image image) {
        this.originalImage = image;
        if (image != null) {
            this.image = Utils.resize(this.originalImage, this.scale);
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
    }

    public void setImage(String fileName) {
        Image i = new Image(fileName);
        this.setImage(i);
    }

    /**
     * Draws the sprites image on canvas at the right position.
     * @param gc
     * @param time (unused, but necessary for overridden method)
     */
    public void render(GraphicsContext gc, double time) {
        double posx = (positionX - offsetX) * scale;
        double posy = (positionY - offsetY) * scale;
        if (stayOnPixel) {
            posx = Math.round(posx * scale) / scale;
            posy = Math.round(posy * scale) / scale;
        }
        gc.drawImage(this.image, posx, posy);
    }

    /**
     * Besides setting the scale, also updates the Image, via Utils, to the new resized scale.
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
        setImage(this.originalImage);
    }

    public String toString() {
        return "Sprite - Position: [" + positionX + "," + positionY + "]" + "Width:[" + width +"," + height + "]";
    }

    // GETTERS AND SETTERS:

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void setPosition(Point point) {
        this.setPosition(point.getX(), point.getY());
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public void setStayOnPixel(boolean stayOnPixel) {
        this.stayOnPixel = stayOnPixel;
    }

    public double getPositionX() {
        return this.positionX;
    }
    public double getPositionY() {
        return this.positionY;
    }

    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }

    public double getScale() {
        return this.scale;
    }

    public boolean getStayOnPixel() {
        return stayOnPixel;
    }

    public Image getImage() {
        return this.image;
    }

    public void setOffset(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return this.offsetX;
    }
    public double getOffsetY() {
        return this.offsetY;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Sprite){
           Sprite other =(Sprite) o;
            if(other.getStayOnPixel() == this.getStayOnPixel() /*&& other.getBoundary() == this.getBoundary()*/
                    && this.getHeight() == other.getHeight() && other.getWidth() == this.getWidth()
                    && other.getImage() == this.getImage() && this.getOffsetX() == other.getOffsetX()
                    && this.getOffsetY() == other.getOffsetY() && this.getPositionX() == other.getPositionX()
                    && this.getPositionY() == other.getPositionY()){
                return true;
            }
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
