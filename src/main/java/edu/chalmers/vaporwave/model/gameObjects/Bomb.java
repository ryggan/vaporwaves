package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.Sprite;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Bomb extends Explosive {

    private int delay;
    //timer

    /**
     * Constructor for Bomb, takes GameCharacter, a range and a delay
     *
     * @param owner
     * @param range
     * @param delay
     */
    public Bomb(GameCharacter owner, int range, int delay) {
        super(owner, range);
        this.delay = delay;
        startTimer();
    }


    public void startTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                explode();
            }
        }, delay);
    }

}
