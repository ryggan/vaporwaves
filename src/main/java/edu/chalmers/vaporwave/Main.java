
package edu.chalmers.vaporwave;
/**
 * Created by bob on 2016-04-15.
 */

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.controller.MainController;
import edu.chalmers.vaporwave.util.Constants;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.setMinHeight(Constants.WINDOW_HEIGHT);
        primaryStage.setMinWidth(Constants.WINDOW_WIDTH);

        primaryStage.show();

		MainController mc = new MainController(root);
		new ListenerController(scene, mc);

	}
}
