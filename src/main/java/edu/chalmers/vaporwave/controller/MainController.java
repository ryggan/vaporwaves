package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.LongValue;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Created by bob on 2016-04-15.
 */
public class MainController {

//    private Stage stage;

    private MenuController mc;
    private ArenaController ac;

    private boolean inGame;

    /**
     * Constructor, that sets up the ongoing main loop.
     * @param root
     */
    public MainController(Group root) {

        // Class setup

//        this.stage = stage;
        this.inGame = false;

        mc = new MenuController(root);
        ac = new ArenaController(root);

        // Animation timer setup

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue(System.nanoTime());

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                // Time management

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;


                // TEST OUTPUT

                if (t % 1.0 < 0.05) {
                    System.out.println("Elapsed time since beginning: "+t);
                    System.out.println("Elapsed time since last call: "+elapsedTime);
                }
            }

        }.start();
    }
}