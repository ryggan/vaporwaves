package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.GoToMenuEvent;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.LoadingScreen;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.LongValue;
import edu.chalmers.vaporwave.view.LoadingScreenView;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.List;

public class MainController {

    private MenuController menuController;
    private GameController gameController;
    private Group root;
    private Group menuRoot;
    private Group gameRoot;

    private LoadingScreen loader;
    private LoadingScreenView loaderView;
    private boolean loadingDone;

    private boolean inGame;

    /**
     * Constructor, that sets up the ongoing main loop.
     *
     * @param root
     */
    public MainController(Group root) {

        this.root = root;

        initLoader(root);

//        initApplication();
//        initTimer();

    }

    // Creates loader and it's view, and then set's up the loading loop
    public void initLoader(Group root) {
        this.loader = new LoadingScreen();
        this.loaderView = new LoadingScreenView(root);
        this.loadingDone = false;

        // Loading loop
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Updates loading percentage and then updates view
                loader.updateLoader();
                loaderView.updateView(loader.getPercentLoaded());

                // Only to delay loading done by one frame
                if (loader.getPercentLoaded() == 1 && !loadingDone) {
                    loadingDone = true;

                // Initiates the rest of the game and starts game-timer, and finally ends the loading loop
                } else if (loadingDone) {
                    initApplication();
                    initTimer();
                    this.stop();
                }
            }
        }.start();

        // A separate thread that initializes all file loading, so that the loading loop can read it's progress
        // without hinderance. Instantly terminates thread when done.
        new Thread(new InitializationRunnable()).start();
    }

    private static class InitializationRunnable implements Runnable {
        public void run() {
            Container.initialize();
            return;
        }
    }

    public void initApplication() {

        GameEventBus.getInstance().register(this);

        //GameServer gameServer = new GameServer();

        this.inGame = false;

        if (inGame) {
            newGame(new NewGameEvent());
        }

        this.menuRoot = new Group();
        this.gameRoot = new Group();

        this.root.getChildren().add(menuRoot);

        this.menuController = new MenuController(menuRoot);
        this.gameController = new GameController(gameRoot);

//        jinputTest();

        ListenerController.getInstance().clearPressed();
        ListenerController.getInstance().clearReleased();
    }

    public void initTimer() {

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

//                jinputTest();

                ListenerController.getInstance().updateGamePadInputs();
                if (ListenerController.getInstance().getGamePads().size() > 0) {
                    Controller gamePad1 = ListenerController.getInstance().getGamePads().get(0);
                    List<String> gamePad1Input = ListenerController.getInstance().getGamePadInput(gamePad1);
                    if (gamePad1Input.size() > 0) {
                        System.out.println("P1 gamepad input: " + gamePad1Input);
                    }
                }

                ListenerController.getInstance().clearPressed();
                ListenerController.getInstance().clearReleased();

            }

        }.start();
    }

    private void jinputTest() {
        Controller[] controllerArray = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for(int i =0;i < controllerArray.length;i++) {

            if (controllerArray[i].getType() == Controller.Type.GAMEPAD) {

                /* Get the name of the controller */
                System.out.println(controllerArray[i].getName());

                System.out.println("Type: " + controllerArray[i].getType().toString());

                /* Get this controllers components (buttons and axis) */
                Component[] components = controllerArray[i].getComponents();
                System.out.println("Component Count: " + components.length);

                for (int j = 0; j < components.length; j++) {

                    /* Get the components name */
                    System.out.println("Component " + j + ": " + components[j].getName());

                    System.out.println("    Identifier: " + components[j].getIdentifier().getName());

                    System.out.print("    ComponentType: ");
                    if (components[j].isRelative()) {
                        System.out.print("Relative");
                    } else {
                        System.out.print("Absolute");
                    }
                    if (components[j].isAnalog()) {
                        System.out.print(" Analog");
                    } else {
                        System.out.print(" Digital");
                    }
                    System.out.println();
                }
            }
        }
    }

    @Subscribe
    public void newGame(NewGameEvent newGameEvent) {

        this.root.getChildren().clear();
        this.root.getChildren().add(gameRoot);
        this.gameController.initGame(gameRoot, newGameEvent);
        this.inGame = true;
    }

    @Subscribe
    public void exitGame(ExitGameEvent exitGameEvent) {
        System.exit(0);
    }

    @Subscribe
    public void goToMenu(GoToMenuEvent goToMenuEvent) {
        this.inGame = false;
        this.root.getChildren().clear();
        this.root.getChildren().add(menuRoot);
        this.menuController.setActiveMenu(goToMenuEvent.getActiveMenu());
        this.menuController.updateViews(-1);
    }
}