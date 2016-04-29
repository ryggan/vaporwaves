package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.util.AI;
import edu.chalmers.vaporwave.util.Utils;

public class Enemy extends Movable {

    private AI ai;

    public Enemy(String name, double canvasPositionX, double canvasPositionY, double speed, AI ai) {
        super(name, canvasPositionX, canvasPositionY, speed);
        this.ai = ai;
    }

    // Todo: Implement this in a remote player instead
//    public void placeBomb() {
//        if(ai.shouldPutBomb()) {
//            GameEventBus.getInstance().post(new PlaceBombEvent(
//                    Utils.canvasToGridPosition(this.getCanvasPositionX(), this.getCanvasPositionY()
//                    ), 1, getDamage())
//            );
//        }
//    }

    public AI getAI() {
        return this.ai;
    }

    @Override
    public int hashCode(){
        int hash = super.hashCode() + ai.hashCode();
        return hash;
    }

}
