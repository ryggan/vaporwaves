package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.BlastState;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BlastTest {
    GameCharacter alyssa = new GameCharacter("ALYSSA", 0);
    Point position = alyssa.getGridPosition();

    @Test
    public void testGetPosition() throws Exception {
        Explosive bombish = new Bomb(alyssa, 1, 1, 1, 30);
        Blast bMan = new Blast(bombish, BlastState.CENTER, null, 0);
//        assertTrue(bMan.getPosition().equals(pirre.getGridPosition()));
    }

    @Test
    public void testGetRange() throws Exception {
        Explosive bombish = new Bomb(alyssa, 1, 1, 1, 30);
        Blast bMan = new Blast(bombish, BlastState.CENTER, null, 0);
//        assertTrue(bMan.getRange() == 1);
    }
}