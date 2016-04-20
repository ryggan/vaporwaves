package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public interface Tile {
    public Point getCanvasPosition();
    public Point getGridPosition();
    public void setCanvasPosition(Point p);
    public void setGridPosition(Point p);
    public double getGridYPosition();
    public double getGridXPosition();
    public double getCanvasYPosition();
    public double getCanvasXPosition();
    public Sprite getSprite();
    public void setSprite(Sprite s);
    public void render(GraphicsContext gc, double time);
}
