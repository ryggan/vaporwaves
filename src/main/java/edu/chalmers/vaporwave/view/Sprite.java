package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A basic class for unanimated images to move about the canvas (which is nice in a game).
 *
 * Created by bob on 2016-04-15.
 */
public class Sprite {

    private Image image;
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
        setImage(this.image);
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
        this.image = image;
        if (image == null) {
            this.width = -1;
            this.height = -1;
        } else {
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
     * Basic colission test between sprites.
     * @param s
     * @return
     */
    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    /**
     * Besides setting the scale, also updates the Image, via Utils, to the new resized scale.
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
        if (this.image != null)
            setImage(Utils.resize(this.image, this.scale));
    }

    public String toString() {
        return "Sprite - Position: [" + positionX + "," + positionY + "]" + "Width:[" + width +"," + height + "]";
    }

    // GETTERS AND SETTERS:

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
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

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
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

}
