package edu.chalmers.vaporwave.util;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class ErrorMessage {
    private static Group root;
    private static ImageView imageView;

    private static long timeInSeconds;
    private static Timer timer;

    private static boolean shown;

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

    public static void show(Exception e) {
        if (!shown) {
            e.printStackTrace();

            root.getChildren().add(imageView);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Runtime.getRuntime().exit(1);
                }
            }, timeInSeconds * 1000);
            shown = true;
        }
    }

    public static boolean isShown() {
        return shown;
    }
}
