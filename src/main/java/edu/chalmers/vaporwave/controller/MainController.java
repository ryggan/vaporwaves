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
        new Thread(new Runnable() {
            public void run() {
                double time = System.currentTimeMillis();
                ImageContainer.initialize();
                System.out.println("Image loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
                time = System.currentTimeMillis();
                FileContainer.initialize();
                System.out.println("File loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
                time = System.currentTimeMillis();
                SoundContainer.initialize();
                System.out.println("Sound loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
                time = System.currentTimeMillis();
                CharacterSpriteContainer.initialize();
                System.out.println("Character sprites done, timed: "+(System.currentTimeMillis() - time)+" millis");
                time = System.currentTimeMillis();
                SpriteContainer.initialize();
                System.out.println("Sprites done, timed: "+(System.currentTimeMillis() - time)+" millis");
                return;
            }
        }).start();
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
        this.inGame = false;
        this.root.getChildren().clear();
        this.root.getChildren().add(menuRoot);
        this.menuController.setActiveMenu(goToMenuEvent.getActiveMenu());
        this.menuController.updateViews();
    }

//    @Subscribe
//    public void loaderInitiated(LoaderInitiatedEvent loaderInitiatedEvent) {
//        System.out.println("loaderInitiated()");
//        initApplication();
//    }
}