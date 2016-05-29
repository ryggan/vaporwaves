package edu.chalmers.vaporwave;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.controller.InputController;
import edu.chalmers.vaporwave.controller.MainController;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.SetFullScreenEvent;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.ErrorMessage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * The main class, where the FX application is created.
 * When initiated, it immediately gives the control to MainController.œ
 */
public class Main extends Application {

    Stage primaryStage;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {}
		});

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

        // Setting up hierarchy
        this.primaryStage = primaryStage;
        final Group root = new Group();
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Design additions
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setMinWidth(Constants.WINDOW_WIDTH);
		primaryStage.setTitle("Sunset Ninjas");

		// This makes the application shut down properly when hitting cmd-q
		// Solution found here: http://mail.openjdk.java.net/pipermail/openjfx-dev/2013-July/008598.html , slightly modified
		primaryStage.setOnCloseRequest(new CloseWindowEventHandler());

		primaryStage.show();

		ErrorMessage.init(root);

        // Initiating controllers
		try {
			InputController.getInstance().initiateListeners(scene);
			new MainController(root);

		} catch (Exception e) {
			ErrorMessage.show(e);
		}

		primaryStage.sizeToScene();

        GameEventBus.getInstance().register(this);
	}

    @Subscribe
    public void setFullScreen(SetFullScreenEvent setFullScreenEvent) {
        this.primaryStage.setFullScreen(setFullScreenEvent.isFullscreen());
        this.primaryStage.sizeToScene();
    }

    private static class CloseWindowEventHandler implements EventHandler<WindowEvent> {
		@Override
		public void handle(final @Nonnull WindowEvent event) {
            Runtime.getRuntime().exit(0);
		}
	}
}
