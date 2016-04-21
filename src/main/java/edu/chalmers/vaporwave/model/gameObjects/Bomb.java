package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.Sprite;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Bomb extends Explosive {

    private Player owner;
    private int delay;
    //timer

    /**
     * Constructor for Bomb, takes a range and a delay
     *
     * @param range
     * @param delay
     */
    public Bomb(int range, int delay) {
        this.delay = delay;
    }

    /**
     * Default constructor
     */
    public Bomb() {
        this(3, 2000);
    }

    public void startTimer(){
        Timer t= new Timer();
        t.schedule(new TimerTask(){
            public void run(){
                explode();
            }
        }, delay);
    }

    public Player getOwner() {
        return this.owner;
    }

}
