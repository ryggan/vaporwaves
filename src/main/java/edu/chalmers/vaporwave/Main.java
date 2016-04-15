
package edu.chalmers.vaporwave;/**
 * Created by bob on 2016-04-15.
 */

import edu.chalmers.vaporwave.controller.ListenerController;
import edu.chalmers.vaporwave.controller.MainController;
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

//        setUpStage(primaryStage);

        primaryStage.show();

		MainController mc = new MainController(primaryStage);
		new ListenerController(scene, mc);

	}

//    private void setUpStage(Stage primaryStage) {
//
//        Group root = new Group();
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//    }
}
