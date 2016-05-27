package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import org.junit.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void testResize() throws Exception {
//        Image testImage = new Image()

    }

    @Test
    public void testCanvasToGridPositionX() throws Exception {

    }

    @Test
    public void testCanvasToGridPositionY() throws Exception {

    }

    @Test
    public void testCanvasToGridPosition() throws Exception {

    }

    @Test
    public void testGridToCanvasPositionX() throws Exception {

    }

    @Test
    public void testGridToCanvasPositionY() throws Exception {

    }

    @Test
    public void testGetRelativePoint() throws Exception {
        Point initialTestPosition = new Point(5,5);
        assertTrue(Utils.getRelativePoint(initialTestPosition, 3, Direction.LEFT).equals(new Point(2,5)));
        assertTrue(Utils.getRelativePoint(initialTestPosition, 2, Direction.UP).equals(new Point(5,3)));
        assertTrue(Utils.getRelativePoint(initialTestPosition, 5, Direction.DOWN).equals(new Point(5,10)));
        assertTrue(Utils.getRelativePoint(initialTestPosition, 1, Direction.RIGHT).equals(new Point(6,5)));
        assertTrue(Utils.getRelativePoint(initialTestPosition, -10, Direction.RIGHT).equals(new Point(-5,5)));
        assertTrue(Utils.getRelativePoint(initialTestPosition, -1, Direction.RIGHT).equals(new Point(4,5)));
        assertFalse(Utils.getRelativePoint(initialTestPosition, 2, Direction.RIGHT).equals(new Point(0,5)));
    }

    @Test
    public void testGetDirectionFromString() throws Exception {
        assertTrue(Utils.getDirectionFromString("LEFT").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("A").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("F").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("J").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("LS_LEFT").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("DPAD_LEFT").equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromString("UP").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("W").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("T").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("I").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("LS_UP").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("DPAD_UP").equals(Direction.UP));
        assertTrue(Utils.getDirectionFromString("RIGHT").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("D").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("H").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("L").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("LS_RIGHT").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("DPAD_RIGHT").equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromString("DOWN").equals(Direction.DOWN));
        assertTrue(Utils.getDirectionFromString("S").equals(Direction.DOWN));
        assertTrue(Utils.getDirectionFromString("G").equals(Direction.DOWN));
        assertTrue(Utils.getDirectionFromString("K").equals(Direction.DOWN));
        assertTrue(Utils.getDirectionFromString("LS_DOWN").equals(Direction.DOWN));
        assertTrue(Utils.getDirectionFromString("DPAD_DOWN").equals(Direction.DOWN));
    }

    @Test
    public void testGetDirectionFromInteger() throws Exception {
        assertTrue(Utils.getDirectionFromInteger(0).equals(Direction.LEFT));
        assertTrue(Utils.getDirectionFromInteger(1).equals(Direction.UP));
        assertTrue(Utils.getDirectionFromInteger(2).equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionFromInteger(3).equals(Direction.DOWN));
    }

    @Test
    public void testGetIntegerFromDirection() throws Exception {
        assertTrue(Utils.getIntegerFromDirection(Direction.LEFT) == 0);
        assertTrue(Utils.getIntegerFromDirection(Direction.UP) == 1);
        assertTrue(Utils.getIntegerFromDirection(Direction.RIGHT) == 2);
        assertTrue(Utils.getIntegerFromDirection(Direction.DOWN) == 3);
    }

    @Test
    public void testGetDirectionsAsArray() throws Exception {
        assertTrue(Utils.getDirectionsAsArray()[0].equals(Direction.LEFT));
        assertTrue(Utils.getDirectionsAsArray()[1].equals(Direction.UP));
        assertTrue(Utils.getDirectionsAsArray()[2].equals(Direction.RIGHT));
        assertTrue(Utils.getDirectionsAsArray()[3].equals(Direction.DOWN));
    }

    @Test
    public void testIsOrtogonalDirections() throws Exception {
        assertTrue(Utils.isOrtogonalDirections(Direction.RIGHT, Direction.UP));
        assertTrue(Utils.isOrtogonalDirections(Direction.DOWN, Direction.RIGHT));
        assertTrue(Utils.isOrtogonalDirections(Direction.LEFT, Direction.UP));
        assertTrue(Utils.isOrtogonalDirections(Direction.UP, Direction.RIGHT));
        assertFalse(Utils.isOrtogonalDirections(Direction.DOWN, Direction.UP));
        assertFalse(Utils.isOrtogonalDirections(Direction.UP, Direction.DOWN));
        assertFalse(Utils.isOrtogonalDirections(Direction.RIGHT, Direction.LEFT));
        assertFalse(Utils.isOrtogonalDirections(Direction.LEFT, Direction.RIGHT));
    }

    @Test
    public void testIsParallellDirections() throws Exception {
        assertTrue(Utils.isParallellDirections(Direction.DOWN, Direction.UP));
        assertTrue(Utils.isParallellDirections(Direction.UP, Direction.DOWN));
        assertTrue(Utils.isParallellDirections(Direction.RIGHT, Direction.LEFT));
        assertTrue(Utils.isParallellDirections(Direction.LEFT, Direction.RIGHT));
        assertFalse(Utils.isParallellDirections(Direction.RIGHT, Direction.UP));
        assertFalse(Utils.isParallellDirections(Direction.DOWN, Direction.RIGHT));
    }

    @Test
    public void testCalculateRemoteSelected() throws Exception {
        int testSelect = 5;
        testSelect += 1;
        assertTrue(Utils.calculateRemoteSelected(new int[]{ testSelect }, 0, 6) == 0);
        testSelect += 1;
        assertTrue(Utils.calculateRemoteSelected(new int[]{ testSelect }, 0, 6) == 1);
        testSelect += 1;
        assertTrue(Utils.calculateRemoteSelected(new int[]{ testSelect }, 0, 6) == 2);
        testSelect += 1;
        assertTrue(Utils.calculateRemoteSelected(new int[]{ testSelect }, 0, 3) == 0);
    }

    @Test
    public void testGetMapObjectPlayerFromID() throws Exception {
        assertTrue(Utils.getMapObjectPlayerFromID(0).equals(MapObject.PLAYER1));
        assertTrue(Utils.getMapObjectPlayerFromID(1).equals(MapObject.PLAYER2));
        assertTrue(Utils.getMapObjectPlayerFromID(2).equals(MapObject.PLAYER3));
        assertTrue(Utils.getMapObjectPlayerFromID(3).equals(MapObject.PLAYER4));
    }

    @Test
    public void testGetPlayerControls() throws Exception {
        assertTrue(Utils.getPlayerControls().get(0)[0].equals("LEFT"));
        assertTrue(Utils.getPlayerControls().get(1)[1].equals("W"));
        assertTrue(Utils.getPlayerControls().get(3)[2].equals("L"));
        assertFalse(Utils.getPlayerControls().get(1)[3].equals("DOWN"));
        assertFalse(Utils.getPlayerControls().get(2)[3].equals("SPACE"));
    }
}