package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.LoadingScreen;
import edu.chalmers.vaporwave.assetcontainer.FileContainer;
import edu.chalmers.vaporwave.util.LongValue;
import edu.chalmers.vaporwave.assetcontainer.SoundContainer;
import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.view.LoadingScreenView;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;

public class MainController {

    private MenuController menuController;
    private GameController gameController;
    private Group root;
    private Group menuRoot;
    private Group gameRoot;

    private Thread loaderThread;
    private LoadingScreen loader;
    private LoadingScreenView loaderView;

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

    public void initLoader(Group root) {
        this.loader = new LoadingScreen(this);
        this.loaderView = new LoadingScreenView(root);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                loader.updateLoader();
                loaderView.updateView();

                if (loader.getPercentLoaded() == 2) {
                    initApplication();
                    initTimer();
                    this.stop();
                }
            }
        }.start();

        new Thread(new Runnable() {
            public void run() {
                SoundContainer.initialize();
                ImageContainer.initialize();
                FileContainer.initialize();
                return;
            }
        }).start();
    }

    public void initApplication() {

        System.out.println("initApplication()");
//
//        SoundContainer.initialize();
//        ImageContainer.initialize();
//        FileContainer.initialize();

        // Trying out mapreader
        GameEventBus.getInstance().register(this);

        //GameServer gameServer = new GameServer();

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
    }

    public void initTimer() {

        System.out.println("initTimer()");

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

                ListenerController.getInstance().clearPressed();
                ListenerController.getInstance().clearReleased();

            }

        }.start();
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
        this.root.getChildren().clear();
        this.root.getChildren().add(menuRoot);
        this.menuController.setActiveMenu(goToMenuEvent.getActiveMenu());
        this.menuController.updateViews();

        this.inGame = false;
    }

//    @Subscribe
//    public void loaderInitiated(LoaderInitiatedEvent loaderInitiatedEvent) {
//        System.out.println("loaderInitiated()");
//        initApplication();
//    }
}