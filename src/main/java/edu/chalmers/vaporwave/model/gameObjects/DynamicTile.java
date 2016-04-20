package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class DynamicTile implements Tile {

    private double canvasPositionX;
    private double canvasPositionY;
    private Point gridPosition;
    private Sprite sprite;
    private double velocityX;
    private double velocityY;

    protected DynamicTile() { }

    public DynamicTile(Sprite sprite, double canvasPositionX, double canvasPositionY, Point gridPosition) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.gridPosition = gridPosition;
        this.sprite = sprite;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void updatePosition() {
        this.canvasPositionX += this.velocityX;
        this.canvasPositionY += this.velocityY;
        sprite.setPosition(this.canvasPositionX, this.canvasPositionY);
    }

    public void render(GraphicsContext gc, double time) {
        getSprite().render(gc, time);
    }

    // GET N SETS

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public double getVelocityX() {
        return this.velocityX;
    }
    public double getVelocityY() {
        return this.velocityY;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public Sprite getSprite() {
        return this.sprite;
    }

    public void setCanvasPosition(double canvasPositionX, double canvasPositionY) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
    }
    public void setCanvasPositionX(double canvasPositionX) {
        this.canvasPositionX = canvasPositionX;
    }
    public void setCanvasPositionY(double canvasPositionY) {
        this.canvasPositionY = canvasPositionY;
    }

    public double getCanvasPositionX() {
        return this.canvasPositionX;
    }
    public double getCanvasPositionY() {
        return this.canvasPositionY;
    }

    public void setGridPosition(Point gridPosition) {
        this.gridPosition = gridPosition;
    }

    public Point getGridPosition() {
        return this.gridPosition;
    }

    public double getGridPositionX() {
        return gridPosition.getX();
    }
    public double getGridPositionY() {
        return gridPosition.getY();
    }
}
