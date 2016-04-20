package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public interface Tile {
    public void setGridPosition(Point p);
    public Point getGridPosition();
    public int getGridPositionX();
    public int getGridPositionY();
    public void setCanvasPosition(double canvasPositionX, double canvasPositionY);
    public void setCanvasPositionX(double canvasPositionX);
    public void setCanvasPositionY(double canvasPositionY);
    public double getCanvasPositionX();
    public double getCanvasPositionY();
    public Sprite getSprite();
    public void setSprite(Sprite s);
    public void render(GraphicsContext gc, double time);
}
