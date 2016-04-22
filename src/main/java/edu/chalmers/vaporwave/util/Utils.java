package edu.chalmers.vaporwave.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Created by bob on 2016-04-17.
 */
public class Utils {

    /**
     * This method takes one image and returns the exact copy of it, scaled to scaleFactor in both width and height.
     * The reason for this lengthy way of doing it, is that it is very important that the image does not smoothen
     * or get any blurry aliasing. This method will get around that problem.
     * OBS! Other values than EVEN NUMBERS may still give a wee bit wonky graphic. Not a problem though.
     * @param input
     * @param scaleFactor
     * @return A scaled image
     */
    public static Image resize(Image input, double scaleFactor) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final double S = scaleFactor;

        WritableImage output = new WritableImage((int)(W * S), (int)(H * S));

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb((int)(x * S) + dx, (int)(y * S) + dy, argb);
                    }
                }
            }
        }

        return output;
    }

    public static int canvasToGridPosition(double canvasPosition) {
        return (int)Math.round(canvasPosition / Constants.DEFAULT_TILE_WIDTH);
    }

    public static double gridToCanvasPosition(int gridPosition) {
        return (double)(gridPosition * Constants.DEFAULT_TILE_WIDTH);
    }
}
