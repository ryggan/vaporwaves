package edu.chalmers.vaporwave.model.gameObjects;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.AI;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Utils;
import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;

public class Enemy extends Movable {

    private AI ai;

    public Enemy(String name, double canvasPositionX, double canvasPositionY, double speed, AI ai) {
        super(name, canvasPositionX, canvasPositionY, speed);
        this.ai = ai;
    }

    public void placeBomb() {
        if(ai.shouldPutBomb()) {
            GameEventBus.getInstance().post(new PlaceBombEvent(
                    Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()
                    ), 1, getDamage())
            );
        }
    }

    public AI getAI() {
        return this.ai;
    }

    @Override
    public int hashCode(){
        int hash = super.hashCode() + ai.hashCode();
        return hash;
    }

}
