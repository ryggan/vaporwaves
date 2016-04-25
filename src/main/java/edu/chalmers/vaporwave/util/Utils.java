package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;

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

    public static Point canvasToGridPosition(double x, double y) {
        return new Point(canvasToGridPosition(x), canvasToGridPosition(y));
    }

    public static double gridToCanvasPosition(int gridPosition) {
        return (double)(gridPosition * Constants.DEFAULT_TILE_WIDTH);
    }

    /**
     * Returns a Point with an offset in the specified direction
     *
     * @param initialPosition The position in the center
     * @param distance The relative distance from the center point
     * @param direction Which direction to calculate (left, up, right, down)
     * @return newPosition The new position
     */
    public static Point getRelativePoint(Point initialPosition, int distance, Direction direction) {
        Point newPosition = new Point(0,0);
        switch (direction) {
            case LEFT:
                newPosition.setLocation(initialPosition.x - distance, initialPosition.y);
                break;
            case UP:
                newPosition.setLocation(initialPosition.x, initialPosition.y - distance);
                break;
            case RIGHT:
                newPosition.setLocation(initialPosition.x + distance, initialPosition.y);
                break;
            case DOWN:
                newPosition.setLocation(initialPosition.x, initialPosition.y + distance);
                break;
        }
        return newPosition;
    }

    public static Direction getDirectionFromString(String direction) {
        switch(direction) {
            case "LEFT":
                return Direction.LEFT;
            case "UP":
                return Direction.UP;
            case "RIGHT":
                return Direction.RIGHT;
            case "DOWN":
                return Direction.DOWN;
        }
        return Direction.RIGHT;
    }

    public static Direction[] getDirectionsAsArray() {
        Direction[] directions = new Direction[4];
        directions[0] = Direction.LEFT;
        directions[1] = Direction.UP;
        directions[2] = Direction.RIGHT;
        directions[3] = Direction.DOWN;
        return directions;
    }
}
