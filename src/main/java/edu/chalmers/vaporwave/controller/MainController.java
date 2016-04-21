package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.util.LongValue;
import edu.chalmers.vaporwave.util.MapFileReader;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;

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
    public MainController(Group root) {


        GameEventBus.getInstance().register(this);
        // Trying out mapreader


        this.root = root;
        // Initiating variables and controllers

        this.inGame = false;


        this.menuController = new MenuController(root);

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

                if (inGame) {
                    gameController.timerUpdate(timeSinceStart, timeSinceLastCall);
                } else {
                    menuController.timerUpdate(timeSinceStart, timeSinceLastCall);
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


    @Subscribe
    public void newGame(NewGameEvent newGameEvent) {
        this.gameController = new GameController(this.root);


    }
}