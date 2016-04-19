package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public abstract class Wall extends StaticTile {

    ///is this class needed? Do indestructible- and destructible wall have anything in common?

    public Wall(Sprite s, Point gPos, Point cPos){
        super(s, gPos, cPos);
    }


}
