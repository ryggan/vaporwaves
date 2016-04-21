package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class DestructibleWall extends Wall {

    private boolean isDestroyed;

    public DestructibleWall(){
        isDestroyed = false;
    }

    private void setDestroyed(){
        isDestroyed = true;
    }

    private boolean getDestroyed(){
        return isDestroyed;
    }


    /////needs spriteinfo, dynamic, cuz need to change when destroyed

}
