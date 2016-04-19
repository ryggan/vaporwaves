package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class Bomb extends Explosive{

    private int delay;
    //timer

    public Bomb(Sprite s, Point cPos, Point gPos, int range, int delay){
        super(s, cPos, gPos, range);
        this.delay=delay;
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


}
