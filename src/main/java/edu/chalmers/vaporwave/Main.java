
package edu.chalmers.vaporwave;

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.controller.MainController;
import edu.chalmers.vaporwave.util.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.annotation.Nonnull;
import java.io.File;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

        // Setting up hierarchy

        final Group root = new Group();
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Design additions

        primaryStage.setResizable(false);
        primaryStage.setMinHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setMinWidth(Constants.WINDOW_WIDTH);
		primaryStage.setTitle("VaporWave");

		// This makes the application shut down properly when hitting cmd-q
		// Solution found here: http://mail.openjdk.java.net/pipermail/openjfx-dev/2013-July/008598.html
		// Slightly modified
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(final @Nonnull WindowEvent event) {
				System.exit(0);
			}
		});

//		System.out.println(System.getProperty("user.dir") + "/src/main/resources/fonts/BauhausStd-Bold.otf");

//		Font.loadFont(Main.class.getResource(System.getProperty("user.dir") + "/src/main/resources/fonts/BauhausStd-Bold.otf").toExternalForm(), 10);





        primaryStage.show();

        // Initiating controllers

		ListenerController.getInstance().initiateListener(scene);
		new MainController(root);
//		new ListenerController(scene, mc);
	}
}
