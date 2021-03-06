package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A purely static class with an assortment of different utility methods that is used throughout
 * the application.
 */
public class Utils {

    // This method takes one image and returns the exact copy of it, scaled to scaleFactor in both width and height.
    // The reason for this lengthy way of doing it, is that it is very important that the image does not smoothen
    // or get any blurry aliasing. This method will get around that problem.
    // OBS! Other values than EVEN NUMBERS may still give a wee bit wonky graphic. Not a problem though.
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

    // Different position conversions to and from canvas and grid
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

    // Returns a point that has moved relative to the given position, in the given direction,
    // at a given distance.
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

    public static Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            default:
                return null;
        }
    }

    // Since there will be a lot of different inputs, this method simplifies it a bit.
    public static Direction getDirectionFromString(String string) {
        switch(string) {
            case "LEFT":
            case "A":
            case "F":
            case "J":
            case "LS_LEFT":
            case "DPAD_LEFT":
                return Direction.LEFT;
            case "UP":
            case "W":
            case "T":
            case "I":
            case "LS_UP":
            case "DPAD_UP":
                return Direction.UP;
            case "RIGHT":
            case "D":
            case "H":
            case "L":
            case "LS_RIGHT":
            case "DPAD_RIGHT":
                return Direction.RIGHT;
            case "DOWN":
            case "S":
            case "G":
            case "K":
            case "LS_DOWN":
            case "DPAD_DOWN":
                return Direction.DOWN;
            default:
                return null;
        }
    }

    // At some places a direct conversion between direction and integers is needed
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
                return null;
        }
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
                return -1;
        }
    }

    public static Direction[] getDirectionsAsArray() {
        Direction[] directions = new Direction[4];
        directions[0] = Direction.LEFT;
        directions[1] = Direction.UP;
        directions[2] = Direction.RIGHT;
        directions[3] = Direction.DOWN;
        return directions;
    }

    // Checker methods to see how two directions is aligned
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

    // Caps a selection to the given modulus without losing the position
    public static int calculateRemoteSelected(int[] remoteSelected, int playerID, int modulus) {
        int selected = remoteSelected[playerID];
        while(selected < 0) {
            selected += modulus;
        }
        return selected % modulus;
    }

    // When spawning players a conversion from MapObject to simple integer is needed
    public static MapObject getMapObjectPlayerFromID(int id) {
        switch (id) {
            case 0:
                return MapObject.PLAYER1;
            case 1:
                return MapObject.PLAYER2;
            case 2:
                return MapObject.PLAYER3;
            case 3:
                return MapObject.PLAYER4;

        }
        return null;
    }

    // The different keyboard controles for every player
    public static List<String[]> getPlayerControls() {
        List playerControls = new ArrayList<>();
        playerControls.add(new String[]{"LEFT", "UP", "RIGHT", "DOWN", "SPACE"});
        playerControls.add(new String[]{"A", "W", "D", "S", "SHIFT"});
        playerControls.add(new String[]{"F", "T", "H", "G", "R"});
        playerControls.add(new String[]{"J", "I", "L", "K", "U"});
        return playerControls;
    }

    public static List<String> getCharacterNames() {
        List<String> characterNames = new ArrayList<>();
        characterNames.add("ALYSSA");
        characterNames.add("MEI");
        characterNames.add("ZYPHER");
        characterNames.add("CHARLOTTE");
        return characterNames;
    }
}
