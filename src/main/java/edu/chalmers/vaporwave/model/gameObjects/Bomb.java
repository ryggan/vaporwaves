package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Bomb extends Explosive {

    private int delay;
    //timer


    //Constructor for JUnit tester
    public Bomb(int range, int delay) {
        this.delay = delay;
    }

    public void startTimer(){
        Timer t= new Timer();
        t.schedule(new TimerTask(){
            public void run(){
                getOuter().explode();
            }
        }, delay);
    }

    public Bomb getOuter() {
        return Bomb.this;
    }

    //Made fireEvent return boolean for testing purposes
    public void fireEvent() {
        startTimer();
    }


}
