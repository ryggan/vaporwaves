package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class StaticTile implements Tile {

    private double canvasPositionX;
    private double canvasPositionY;
    private Point gridPosition;
    private Sprite sprite;

    public StaticTile(){
    }

    public StaticTile(Sprite s, double canvasPositionX, double canvasPositionY, Point gridPosition) {
        this.canvasPositionX = canvasPositionX;
        this.canvasPositionY = canvasPositionY;
        this.gridPosition = gridPosition;
        this.sprite = s;
    }

    public void render(GraphicsContext gc, double time) {
        getSprite().render(gc, time);
    }

    // GET N SETS

    public void setSprite(Sprite s) {
        this.sprite = s;
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

    public void setGridPosition(Point p) {
        this.gridPosition = p;
    }

    public Point getGridPosition() {
        return this.gridPosition;
    }

    public int getGridPositionX() {
        return (int)gridPosition.getX();
    }
    public int getGridPositionY() {
        return (int)gridPosition.getY();
    }
}
