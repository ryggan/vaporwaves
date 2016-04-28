package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.view.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Explosive {

    private int delay;
    private Timer timer;

    public Bomb(GameCharacter owner, int range, int delay) {
        super(owner, range);
        this.delay = delay;
        this.timer = new Timer();
        startTimer();
    }


    private void startTimer() {
        this.timer.schedule(new TimerTask(){
            public void run() {
                explode();
                // Stops the timer when the explode method has been run.
                stopTimer();
            }
        }, this.delay);
    }

    private void stopTimer() {
        this.timer.cancel();
    }

}
