package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class Movable {

    private double canvasPositionX;
    private double canvasPositionY;
    private Point gridPosition;
    private double velocityX;
    private double velocityY;

    protected Movable() { }

    public Movable(double canvasPositionX, double canvasPositionY, Point gridPosition) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.gridPosition = gridPosition;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void updatePosition() {
        this.canvasPositionX += this.velocityX;
        this.canvasPositionY += this.velocityY;
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

    public void setCanvasPosition(double canvasPositionX, double canvasPositionY) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
    }
    
//    public void setCanvasPositionX(double canvasPositionX) {
//        this.canvasPositionX = canvasPositionX;
//    }
//    public void setCanvasPositionY(double canvasPositionY) {
//        this.canvasPositionY = canvasPositionY;
//    }

    public double getCanvasPositionX() {
        return this.canvasPositionX;
    }
    public double getCanvasPositionY() {
        return this.canvasPositionY;
    }

//    public void setGridPosition(Point gridPosition) {
//        this.gridPosition = gridPosition;
//    }

//    public Point getGridPosition() {
//        return this.gridPosition;
//    }

//    public int getGridPositionX() {
//        return (int)gridPosition.getX();
//    }
//    public int getGridPositionY() {
//        return (int)gridPosition.getY();
//    }

//    public void setGeneralPosition(int gridPositionX, int gridPositionY) {
//        setGridPosition(new Point(gridPositionX, gridPositionY));
//        setCanvasPosition(gridPositionX * Constants.DEFAULT_TILE_WIDTH, gridPositionY * Constants.DEFAULT_TILE_HEIGHT);
//    }
}
