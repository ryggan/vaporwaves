package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Explosive {

    private int delay;

    public Bomb(GameCharacter owner, int range, int delay, double damage) {
        super(owner, range, damage);
        this.delay = delay;
        startTimer();
    }

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                explode();
            }
        }, delay);
    }

}
