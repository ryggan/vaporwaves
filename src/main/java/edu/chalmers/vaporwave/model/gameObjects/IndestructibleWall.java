package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class IndestructibleWall extends Wall {

    public IndestructibleWall(Sprite sprite, double canvasPositionX, double canvasPositionY, Point gridPosition){
        super(sprite, canvasPositionX, canvasPositionY, gridPosition);
    }


}
