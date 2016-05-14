package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    public static int canvasToGridPositionX(double x) {
        return (int)Math.round(x / Constants.DEFAULT_TILE_WIDTH);
    }
    public static int canvasToGridPositionY(double y) {
        return (int)Math.round(y / Constants.DEFAULT_TILE_HEIGHT);
    }

    public static Point canvasToGridPosition(double x, double y) {
        return new Point(canvasToGridPositionX(x), canvasToGridPositionY(y));
    }

    public static double gridToCanvasPositionX(int gridPositionX) {
        return (double)(gridPositionX * Constants.DEFAULT_TILE_WIDTH);
    }
    public static double gridToCanvasPositionY(int gridPositionY) {
        return (double)(gridPositionY * Constants.DEFAULT_TILE_HEIGHT);
    }

    public static Point gridToCanvasPosition(Point gridPosition) {
        return new Point();
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
            default:
        }
        return newPosition;
    }

    public static Direction getDirectionFromString(String string) {
        switch(string) {
            case "LEFT":
            case "A":
            case "LS_LEFT":
            case "DPAD_LEFT":
                return Direction.LEFT;
            case "UP":
            case "W":
            case "LS_UP":
            case "DPAD_UP":
                return Direction.UP;
            case "RIGHT":
            case "D":
            case "LS_RIGHT":
            case "DPAD_RIGHT":
                return Direction.RIGHT;
            case "DOWN":
            case "S":
            case "LS_DOWN":
            case "DPAD_DOWN":
                return Direction.DOWN;
            default:
        }
        return null;
    }

    public static Direction getDirectionFromInteger(int integer) {
        switch (integer) {
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.UP;
            case 2:
                return Direction.RIGHT;
            case 3:
                return Direction.DOWN;
            default:
        }
        return null;
    }

    public static int getIntegerFromDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                return 0;
            case UP:
                return 1;
            case RIGHT:
                return 2;
            case DOWN:
                return 3;
            default:
        }
        return -1;
    }

    public static Direction[] getDirectionsAsArray() {
        Direction[] directions = new Direction[4];
        directions[0] = Direction.LEFT;
        directions[1] = Direction.UP;
        directions[2] = Direction.RIGHT;
        directions[3] = Direction.DOWN;
        return directions;
    }

    public static boolean isOrtogonalDirections(Direction direction1, Direction direction2) {
        return ((direction1 == Direction.DOWN || direction1 == Direction.UP)
                && (direction2 == Direction.RIGHT || direction2 == Direction.LEFT))
                || ((direction1 == Direction.RIGHT || direction1 == Direction.LEFT)
                && (direction2 == Direction.DOWN || direction2 == Direction.UP));
    }

    public static boolean isParallellDirections(Direction direction1, Direction direction2) {
        return (direction1 == direction2)
                || (direction1 == Direction.DOWN && direction2 == Direction.UP)
                || (direction1 == Direction.UP && direction2 == Direction.DOWN)
                || (direction1 == Direction.RIGHT && direction2 == Direction.LEFT)
                || (direction1 == Direction.LEFT && direction2 == Direction.RIGHT);
    }

    public static int calculateRemoteSelected(int[] remoteSelected, int playerID, int modulus) {
        int selected = remoteSelected[playerID];
        while(selected < 0) {
            selected += modulus;
        }
        return selected % modulus;

    }
}
