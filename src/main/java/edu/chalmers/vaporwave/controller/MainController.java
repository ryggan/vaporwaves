package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.event.IEvent;
import edu.chalmers.vaporwave.event.IEventListener;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.gameObjects.RangePowerUp;
import edu.chalmers.vaporwave.util.LongValue;
import edu.chalmers.vaporwave.util.MapFileReader;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.ArrayList;

public class MainController {

    private MenuController menuController;
    private GameController gameController;
    private ListenerController listenerController;
    private Group root;

    private boolean inGame;

    /**
     * Constructor, that sets up the ongoing main loop.
     * @param root
     */
    public MainController(Group root, ListenerController listenerController) {

        MapFileReader mfr = new MapFileReader("src/main/resources/maps/default.vapormap");

        this.root = root;

        // Initiating variables and controllers

        this.inGame = true;


        this.menuController = new MenuController(root);
        newGame(new NewGameEvent());

        this.listenerController = listenerController;

        // Animation timer setup

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue(System.nanoTime());


        // Game loop
        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                // Time management

                double timeSinceLastCall = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double timeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;

                // Controller calls

                ArrayList<String> input = listenerController.getInput();

                if (inGame) {
                    gameController.timerUpdate(timeSinceStart, timeSinceLastCall, input);
                } else {
                    menuController.timerUpdate(timeSinceStart, timeSinceLastCall, input);
                }

                // TEST OUTPUT

//                if (timeSinceStart % 1.0 < 0.05) {
//                    System.out.println("Elapsed time since beginning: "+timeSinceStart);
//                    System.out.println("Elapsed time since last call: "+timeSinceLastCall);
//                    System.out.println("---");
//                }
            }

        }.start();
    }

    public void newGame(NewGameEvent event) {
        this.gameController = new GameController(this.root);



    }
}