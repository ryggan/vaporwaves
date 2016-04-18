package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class DestructibleWall extends Wall {

    private boolean isDestroyed;
    private Sprite destroyedSprite;

    public DestructibleWall(Sprite firstSprite, Point gPos, Point cPos, Sprite destroyedSprite){
        super(firstSprite, gPos, cPos);
        this.destroyedSprite=destroyedSprite;
        isDestroyed=false;
    }

    private void setDestroyed(boolean set){
        isDestroyed=set;
        if(isDestroyed){
            this.setSprite(destroyedSprite);
        }
    }

    private boolean getDestroyed(){
        return isDestroyed;
    }

    /////needs spriteinfo, dynamic, cuz need to change when destroyed

}
