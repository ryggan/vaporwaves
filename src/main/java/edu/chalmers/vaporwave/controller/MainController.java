package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.GoToMenuEvent;
import edu.chalmers.vaporwave.model.LoadingScreen;
import edu.chalmers.vaporwave.model.menu.NewGameEvent;
import edu.chalmers.vaporwave.util.ErrorMessage;
import edu.chalmers.vaporwave.util.LongValue;
import edu.chalmers.vaporwave.view.LoadingScreenView;
import javafx.animation.AnimationTimer;
import javafx.fxml.LoadException;
import javafx.scene.Group;

public class MainController {

    private MenuController menuController;
    private GameController gameController;
    private ContentController contentController;
    private Group root;
    private Group menuRoot;
    private Group gameRoot;

    private LoadingScreen loader;
    private LoadingScreenView loaderView;
    private boolean loadingDone;

    private static boolean loadingError;

    /**
     * Constructor, that sets up the ongoing main loop.
     *
     * @param root
     */
    public MainController(Group root) throws Exception {

        this.root = root;

        initLoader(root);

    }

    // Creates loader and it's view, and then set's up the loading loop
    public void initLoader(Group root) throws Exception {
        this.loader = new LoadingScreen();
        this.loaderView = new LoadingScreenView(root);
        this.loadingDone = false;
        loadingError = false;
        // Loading loop
        new AnimationTimer() {

            @Override
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

                if (loadingError && !ErrorMessage.isShown()) {
                    ErrorMessage.show(new LoadException());
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
        try {

            GameEventBus.getInstance().register(this);

            this.menuRoot = new Group();
            this.gameRoot = new Group();

            this.root.getChildren().add(menuRoot);

            this.menuController = new MenuController(menuRoot);
            this.gameController = new GameController(gameRoot);
            this.contentController = menuController;

            ListenerController.getInstance().clearPressed();
            ListenerController.getInstance().clearReleased();

        } catch (Exception e) {
            ErrorMessage.show(e);
        }
    }

    public void initTimer() {

        // Animation timer setup

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue(System.nanoTime());

        // Game loop
        new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                try {
                    // Time management
                    double timeSinceLastCall = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                    lastNanoTime.value = currentNanoTime;

                    double timeSinceStart = (currentNanoTime - startNanoTime) / 1000000000.0;

                    contentController.timerUpdate(timeSinceStart, timeSinceLastCall);

                    // Listener cleanup and updating

                    ListenerController.getInstance().updateGamePadInputs();
                    ListenerController.getInstance().clearPressed();
                    ListenerController.getInstance().clearReleased();

                } catch (Exception e) {
                    ErrorMessage.show(e);
                }
            }

        }.start();
    }

    @Subscribe
    public void newGame(NewGameEvent newGameEvent) {
        this.root.getChildren().clear();
        this.root.getChildren().add(gameRoot);
        this.gameController.initGame(gameRoot, newGameEvent);
        this.contentController = gameController;
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
        this.menuController.updateViews(null);
        this.contentController = menuController;
    }

    public static void showError() {
        loadingError = true;
    }
}