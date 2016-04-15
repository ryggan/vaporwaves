package edu.chalmers.vaporwave.controller;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

/**
 * Created by bob on 2016-04-15.
 */
public class MainController {

    private Stage stage;
    private boolean inGame;


    public MainController(Stage stage) {
        this.stage = stage;
        this.inGame = false;

        // Animation timer setup

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue(System.nanoTime());

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                // Time management

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;


                // TEST

                if (t % 1.0 < 0.05) {
                    System.out.println("Elapsed time since beginning: "+t);
                    System.out.println("Elapsed time since last call: "+elapsedTime);
                }
            }

        }.start();
    }
}

/**
 * Utility class, to walk around the problem with inner classes and primitive variables.
 * Mainly used in AnimationTimer().
 */
class LongValue {
    public long value;

    public LongValue(long n) {
        value = n;
    }
}