package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.Utils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by bob on 2016-04-15.
 */
public class Sprite {

    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private double scale;

    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        scale = 1.0;
        setImage(this.image);
    }
    public Sprite(Image image) {
        this();
        setImage(image);
    }
    public Sprite(String fname) {
        this();
        setImage(fname);
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
    public void setImage(String fname) {
        Image i = new Image(fname);
        this.setImage(i);
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void addVelocity(double addX, double addY) {
        this.velocityX += addX;
        this.velocityY += addY;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public void setScale(double scale) {
        this.scale = scale;
        if (this.image != null) {
            setImage(Utils.resize(this.image, this.scale));
        }
    }

    public double getPositionX() {
        return this.positionX;
    }
    public double getPositionY() {
        return this.positionY;
    }

    public double getVelocityX() {
        return this.velocityX;
    }
    public double getVelocityY() {
        return this.velocityY;
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

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]" + " Velocity: ["
                + velocityX + "," + velocityY + "]";
    }

}
