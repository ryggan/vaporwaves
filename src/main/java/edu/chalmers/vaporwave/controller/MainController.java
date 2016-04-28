package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.GoToMenuEvent;
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
    private Group menuRoot;
    private Group gameRoot;

    private boolean inGame;

    /**
     * Constructor, that sets up the ongoing main loop.
     *
     * @param root
     */
    public MainController(Group root) {


        GameEventBus.getInstance().register(this);
        // Trying out mapreader


        this.root = root;
        // Initiating variables and controllers

        this.inGame = false;

        if (inGame) {
            newGame(new NewGameEvent());
        }

        this.menuRoot = new Group();
        this.gameRoot = new Group();

        this.root.getChildren().add(menuRoot);

        this.menuController = new MenuController(menuRoot);
        this.gameController = new GameController(gameRoot);

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

                for (String code : ListenerController.getInstance().getPressed()) {
                    ListenerController.getInstance().updatePressed(code);
                }
            }

        }.start();

    }


    @Subscribe
    public void newGame(NewGameEvent newGameEvent) {
        this.root.getChildren().clear();
        this.root.getChildren().add(gameRoot);
        this.gameController.initGame(gameRoot);
        this.inGame = true;
    }

    @Subscribe
    public void exitGame(ExitGameEvent exitGameEvent) {
        System.exit(0);
    }

    @Subscribe
    public void goToMenu(GoToMenuEvent goToMenuEvent) {
        this.root.getChildren().clear();
        this.root.getChildren().add(menuRoot);
        this.menuController.updateViews();
        this.inGame = false;
    }
}