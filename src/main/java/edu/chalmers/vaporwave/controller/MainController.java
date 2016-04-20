package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.gameObjects.RangePowerUp;
import edu.chalmers.vaporwave.util.LongValue;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.ArrayList;

/**
 * Created by bob on 2016-04-15.
 */
public class MainController {

//    private Stage stage;

    private MenuController menuController;
    private GameController gameController;
    private ListenerController listenerController;

    private boolean inGame;

    /**
     * Constructor, that sets up the ongoing main loop.
     * @param root
     */
    public MainController(Group root, ListenerController listenerController) {

        // Initiating variables and controllers

        this.inGame = false;

        this.menuController = new MenuController(root);
        this.gameController = new GameController(root);
        this.listenerController = listenerController;

        // Animation timer setup

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue(System.nanoTime());

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                // Time management

                double timeSinceLastCall = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double timeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;

                // Controller calls

                ArrayList<String> input = listenerController.getInput();

                if (inGame)
                    gameController.timerUpdate(timeSinceStart, timeSinceLastCall, input);
                else
                    menuController.timerUpdate(timeSinceStart, timeSinceLastCall, input);

                // TEST OUTPUT

//                if (timeSinceStart % 1.0 < 0.05) {
//                    System.out.println("Elapsed time since beginning: "+timeSinceStart);
//                    System.out.println("Elapsed time since last call: "+timeSinceLastCall);
//                    System.out.println("---");
//                }
            }

        }.start();
    }
}