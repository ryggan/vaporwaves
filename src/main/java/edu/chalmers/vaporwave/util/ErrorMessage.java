package edu.chalmers.vaporwave.util;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class shows a simple error window if any unchecked exceptions occur.
 */
public class ErrorMessage {
    private static Group root;
    private static ImageView imageView;

    private static int timeInSeconds;
    private static Timer timer;

    private static boolean shown;

    // First initiated in Main class
    public static void init(Group rootIn) {
        root = rootIn;

        Image image = new Image("images/error_message.png");
        imageView = new ImageView(image);
        imageView.setX(Constants.WINDOW_WIDTH / 2 - image.getWidth() / 2);
        imageView.setY(Constants.WINDOW_HEIGHT / 2 - image.getHeight() / 2);

        timer = new Timer();
        timeInSeconds = 10;
        shown = false;
    }

    // Except showing the message, this also prints the exceptions stacktrace and then
    // terminates the program after a set amount of seconds
    public static void show(Exception e) {
        if (!shown) {
            e.printStackTrace();

            root.getChildren().add(imageView);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Runtime.getRuntime().exit(1);
                }
            }, timeInSeconds * 1000L);
            shown = true;
        }
    }

    public static boolean isShown() {
        return shown;
    }
}
