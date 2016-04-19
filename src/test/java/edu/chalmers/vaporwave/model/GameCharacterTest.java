package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class GameCharacterTest {

    @Test
    public void testMoveUp() throws Exception {
//        Point p = new Point(0,0);
        GameCharacter ch = new GameCharacter("Alyssa");
        ch.moveUp();
        assertTrue(ch.getXPosition() == 0);
        assertTrue(ch.getYPosition() == 1);
    }

    @Test
    public void testMoveDown() throws Exception {
//        Point p = new Point(0,0);
        GameCharacter ch = new GameCharacter("Alyssa");
        ch.moveDown();
        assertTrue(ch.getXPosition() == 0);
        assertTrue(ch.getYPosition() == -1);

    }

    @Test
    public void testMoveLeft() throws Exception {
//        Point p = new Point(0,0);
        GameCharacter ch = new GameCharacter("Alyssa");
        ch.moveLeft();
        assertTrue(ch.getXPosition() == -1);
        assertTrue(ch.getYPosition() == 0);

    }

    @Test
    public void testMoveRight() throws Exception {
//        Point p = new Point(0,0);
        GameCharacter ch = new GameCharacter("Alyssa");
        ch.moveRight();
        assertTrue(ch.getXPosition() == 1);
        assertTrue(ch.getYPosition() == 0);

    }
}