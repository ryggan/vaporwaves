package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class StaticTile implements Tile {
    private Point canvasPosition;
    private Point gridPosition;
    private Sprite sprite;

    public StaticTile(){
    }

    public StaticTile(Sprite s, Point gPos, Point cPos) {
        this.canvasPosition = cPos;
        this.gridPosition = gPos;
        this.sprite = s;
    }

    public void setCanvasPosition(Point p) {
        this.canvasPosition = p;
    }

    public Point getCanvasPosition() {
        return this.canvasPosition;
    }

    public void setGridPosition(Point p) {
        this.gridPosition = p;
    }

    public Point getGridPosition() {
        return this.gridPosition;
    }

    public void setSprite(Sprite s) {
        this.sprite = s;
    }
    public Sprite getSprite() {
        return this.sprite;
    }

    public double getCanvasXPosition() {
        return canvasPosition.getX();
    }
    public double getCanvasYPosition() {
        return canvasPosition.getY();
    }

    public double getGridXPosition() {
        return gridPosition.getX();
    }
    public double getGridYPosition() {
        return gridPosition.getY();
    }

    public void render(GraphicsContext gc, double time) {
        getSprite().render(gc, time);
    }
}
